# 파일 업로드와 상품 이미지 관리
## 1. 파일 업로드 개요

상품 등록 기능에서는 상품 정보와 함께 여러 개의 이미지 파일을 업로드할 수 있다.

파일 업로드 과정은 다음과 같이 진행된다.

```text
Client
   │
   │ multipart/form-data 요청
   ▼
Controller
   │
   ▼
업로드 파일 검증
   │
   ▼
UUID를 포함한 파일명 생성
   │
   ▼
서버 디렉터리에 파일 저장
   │
   ▼
파일 정보를 상품 이미지 엔티티에 저장
   │
   ▼
Database
```

실제 이미지 파일은 서버의 파일 시스템에 저장하고, 데이터베이스에는 파일명과 순서 등의 정보만 저장한다.

---

## 2. 파일 업로드 설정

`application.properties`

```properties
# Multipart 파일 업로드 기능 활성화
spring.servlet.multipart.enabled=true

# 업로드 가능한 개별 파일의 최대 크기
spring.servlet.multipart.max-file-size=3MB

# 하나의 요청에서 업로드 가능한 전체 파일 크기
spring.servlet.multipart.max-request-size=30MB

# WebFlux 환경에서 메모리에 보관할 수 있는 Multipart 데이터 크기
# Spring MVC만 사용하는 경우에는 일반적으로 필요하지 않다.
spring.webflux.multipart.max-in-memory-size=256KB

# 파일이 실제로 저장될 서버 디렉터리
com.ex3.khg.upload.path=upload

# 정적 리소스를 조회할 수 있는 위치
# 기본 static 디렉터리와 외부 upload 디렉터리를 함께 사용한다.
spring.web.resources.static-locations=classpath:/static/,file:${com.ex3.khg.upload.path}/
```

---

## 3. Multipart 설정

### `spring.servlet.multipart.enabled`

```properties
spring.servlet.multipart.enabled=true
```

Spring Boot에서 `multipart/form-data` 형식의 파일 업로드를 허용한다.

활성화하면 Controller에서 `MultipartFile`을 이용하여 업로드된 파일을 받을 수 있다.

```java
@PostMapping("/upload")
public void upload(MultipartFile file) {
}
```

---

### `spring.servlet.multipart.max-file-size`

```properties
spring.servlet.multipart.max-file-size=3MB
```

파일 한 개의 최대 크기를 `3MB`로 제한한다.

예를 들어 이미지 파일 5개를 업로드하더라도 각각의 파일은 `3MB`를 초과할 수 없다.

---

### `spring.servlet.multipart.max-request-size`

```properties
spring.servlet.multipart.max-request-size=30MB
```

한 번의 HTTP 요청으로 업로드할 수 있는 전체 파일 크기를 `30MB`로 제한한다.

```text
파일 1: 2MB
파일 2: 2MB
파일 3: 2MB

전체 요청 크기: 약 6MB
```

개별 파일 크기와 요청 전체 크기는 서로 다른 제한이다.

---

## 4. 파일 저장 경로

```properties
com.ex3.khg.upload.path=upload
```

업로드된 파일을 저장할 디렉터리를 사용자 정의 설정으로 지정한다.

```text
프로젝트 실행 위치
└── upload
    ├── UUID_product1.jpg
    ├── UUID_product2.jpg
    └── UUID_product3.png
```

Java 코드에서는 `@Value`를 이용해 설정값을 가져올 수 있다.

```java
@Value("${com.ex3.khg.upload.path}")
private String uploadPath;
```

운영 환경에서는 절대 경로나 외부 저장소를 사용하는 것이 안전하다.

```properties
com.ex3.khg.upload.path=C:/upload
```

또는 Linux 환경에서는 다음과 같이 설정할 수 있다.

```properties
com.ex3.khg.upload.path=/home/app/upload
```

---

## 5. 업로드 파일을 정적 리소스로 제공하기

```properties
spring.web.resources.static-locations=classpath:/static/,file:${com.ex3.khg.upload.path}/
```

Spring Boot가 다음 두 위치의 파일을 정적 리소스로 제공하도록 설정한다.

```text
classpath:/static/
file:upload/
```

따라서 `upload` 디렉터리에 저장된 이미지를 브라우저에서 직접 조회할 수 있다.

예시:

```text
http://localhost:8080/UUID_product.jpg
```

단, 실제 URL 구조는 ResourceHandler 설정이나 Controller 구현 방식에 따라 달라질 수 있다.

---

## 6. 업로드 파일 제한

파일 업로드 시 다음 조건을 검사해야 한다.

* 이미지 파일만 업로드할 수 있다.
* 파일이 비어 있으면 저장하지 않는다.
* 파일 크기 제한을 초과하면 업로드할 수 없다.
* 권한이 있는 사용자만 업로드할 수 있다.
* 파일명 중복을 방지해야 한다.
* 위험한 파일명과 경로 조작을 방지해야 한다.

---

## 7. 이미지 파일 검증

클라이언트가 보내는 파일 확장자만 신뢰해서는 안 된다.

다음 파일은 확장자가 이미지처럼 보여도 실제 내용은 이미지가 아닐 수 있다.

```text
malicious.exe → malicious.jpg
```

따라서 다음 정보를 함께 검사하는 것이 좋다.

```java
String contentType = file.getContentType();

if (contentType == null || !contentType.startsWith("image/")) {
    throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
}
```

가능하면 이미지 라이브러리를 이용해 실제 이미지 파일인지 추가로 확인한다.

---

## 8. UUID를 이용한 파일명 생성

사용자가 같은 이름의 파일을 여러 번 업로드할 수 있으므로 원본 파일명을 그대로 저장하면 충돌이 발생할 수 있다.

예시:

```text
product.jpg
product.jpg
product.jpg
```

UUID를 파일명 앞에 추가하면 중복 가능성을 줄일 수 있다.

```java
String uuid = UUID.randomUUID().toString();
String saveFileName = uuid + "_" + originalFileName;
```

저장 결과:

```text
3fa85f64-5717-4562-b3fc-2c963f66afa6_product.jpg
```

UUID는 파일명을 고유하게 만드는 용도로 사용한다.

---

## 9. 원본 파일명과 저장 파일명

파일 업로드 시 원본 파일명과 실제 저장 파일명을 구분하는 것이 좋다.

| 항목               | 설명                |
| ---------------- | ----------------- |
| originalFileName | 사용자가 업로드한 원래 파일명  |
| uuid             | 파일명 중복 방지를 위한 고유값 |
| saveFileName     | 서버에 실제로 저장되는 파일명  |

예시:

```text
원본 파일명: product.jpg
UUID: 3fa85f64-5717-4562-b3fc-2c963f66afa6
저장 파일명: 3fa85f64-5717-4562-b3fc-2c963f66afa6_product.jpg
```

데이터베이스에는 UUID와 원본 파일명을 각각 저장하거나, 저장 파일명을 하나의 값으로 보관할 수 있다.

---

## 10. 업로드 권한

파일 업로드는 상품을 등록하거나 수정할 권한이 있는 사용자만 가능해야 한다.

예를 들어 Spring Security에서는 다음과 같이 제한할 수 있다.

```java
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/products")
public void registerProduct() {
}
```

또는 Security 설정에서 특정 URL에 대한 권한을 지정할 수 있다.

```java
.requestMatchers("/api/products/**").hasRole("ADMIN")
```

이미지 조회는 상품 정보를 보여주기 위한 기능이므로 일반적으로 모든 사용자가 접근할 수 있도록 설정한다.

```text
업로드: 관리자 또는 권한이 있는 사용자만 가능
조회: 모든 사용자 가능
```

---

## 11. 상품 엔티티와 이미지 엔티티

하나의 상품에는 여러 개의 이미지가 등록될 수 있다.

```text
ProductEntity 1
      │
      │ 1:N
      ▼
ProductImage 여러 개
```

관계형 데이터베이스에서는 보통 다음과 같이 구성한다.

```text
ProductEntity
- pno
- pname
- price
- description

ProductImage
- uuid
- fileName
- idx
- product_pno
```

`ProductImage`는 어떤 상품에 속한 이미지인지 알기 위해 상품의 기본키를 외래키로 가진다.

---

## 12. 상품 엔티티 예시

```java
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    private String description;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();
}
```

상품 엔티티는 상품의 기본 정보와 이미지 목록을 함께 관리한다.

---

## 13. 상품 이미지 클래스 예시

```java
@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String uuid;

    private String fileName;

    private int idx;
}
```

각 필드의 역할은 다음과 같다.

| 필드       | 설명                |
| -------- | ----------------- |
| uuid     | 파일명 중복 방지를 위한 고유값 |
| fileName | 사용자가 업로드한 원본 파일명  |
| idx      | 상품 이미지의 출력 순서     |

실제 저장 파일명은 다음과 같이 만들 수 있다.

```java
public String getLink() {
    return uuid + "_" + fileName;
}
```

---

## 14. 이미지 순서 관리

상품에 여러 이미지가 있는 경우 `idx`를 이용하여 출력 순서를 관리한다.

```text
idx = 0 → 대표 이미지
idx = 1 → 두 번째 이미지
idx = 2 → 세 번째 이미지
```

상품 목록 화면에서는 보통 `idx = 0`인 이미지만 대표 이미지로 조회한다.

```java
query.where(productImage.idx.eq(0));
```

상품 상세 화면에서는 해당 상품의 모든 이미지를 순서대로 조회한다.

---

## 15. 상품 이미지 추가

상품 엔티티 내부에 이미지 추가 메서드를 작성하면 이미지 목록을 안전하게 관리할 수 있다.

```java
public void addImage(String uuid, String fileName) {

    ProductImage productImage = ProductImage.builder()
            .uuid(uuid)
            .fileName(fileName)
            .idx(images.size())
            .build();

    images.add(productImage);
}
```

이미지가 추가될 때 현재 이미지 개수를 `idx`로 사용하면 순서를 자동으로 지정할 수 있다.

---

## 16. 상품 이미지 초기화

상품 수정 시 기존 이미지 목록을 비우고 새로운 이미지 목록으로 교체해야 할 수 있다.

```java
public void clearImages() {
    images.clear();
}
```

단순히 데이터베이스의 이미지 정보만 삭제하면 실제 서버 디렉터리에 저장된 파일은 남아 있을 수 있다.

따라서 상품 이미지 삭제 시에는 다음 두 작업을 모두 고려해야 한다.

```text
1. 데이터베이스의 이미지 정보 삭제
2. 서버 디렉터리의 실제 파일 삭제
```

---

## 17. 파일 업로드 Controller 예시

```java
@PostMapping("/upload")
public List<String> upload(List<MultipartFile> files) {

    List<String> result = new ArrayList<>();

    for (MultipartFile file : files) {

        if (file.isEmpty()) {
            continue;
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }

        String originalFileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + "_" + originalFileName;

        result.add(saveFileName);
    }

    return result;
}
```

실제 구현에서는 생성한 파일명을 이용하여 지정된 디렉터리에 파일을 저장해야 한다.

---

## 18. 파일 업로드 시 보안 주의사항

파일 업로드 기능은 보안상 주의가 필요한 기능이다.

* 원본 파일명을 그대로 저장하지 않는다.
* `../`와 같은 경로 이동 문자열을 제거한다.
* 실행 가능한 파일 업로드를 차단한다.
* MIME 타입과 실제 파일 형식을 검사한다.
* 파일 크기와 개수를 제한한다.
* 업로드 디렉터리에 실행 권한을 부여하지 않는다.
* 사용자에게 서버의 실제 파일 경로를 노출하지 않는다.
* 인증과 권한 검사를 반드시 수행한다.

원본 파일명에서 경로 정보를 제거하려면 다음과 같이 처리할 수 있다.

```java
String originalFileName =
        Paths.get(file.getOriginalFilename())
             .getFileName()
             .toString();
```

---

## 19. 파일 업로드와 데이터베이스의 역할

일반적인 파일 업로드 구조에서는 파일 자체와 파일 정보를 분리하여 관리한다.

```text
파일 시스템
- 실제 이미지 파일 저장

Database
- UUID
- 원본 파일명
- 이미지 순서
- 상품 번호
```

이미지 파일 자체를 데이터베이스의 BLOB 형식으로 저장할 수도 있지만, 일반적인 웹 서비스에서는 파일 시스템이나 외부 스토리지를 사용하는 경우가 많다.

---

## 20. 핵심 정리

* Spring Boot는 `MultipartFile`을 이용하여 파일 업로드를 처리한다.
* 개별 파일 크기와 전체 요청 크기를 각각 제한할 수 있다.
* 업로드된 실제 파일은 서버 디렉터리에 저장한다.
* 데이터베이스에는 UUID, 파일명, 이미지 순서 등의 정보만 저장한다.
* 파일명 중복 방지를 위해 UUID를 사용한다.
* 이미지 파일 여부와 파일 크기를 반드시 검증해야 한다.
* 업로드는 권한이 있는 사용자만 가능하도록 제한한다.
* 상품과 상품 이미지는 일반적으로 1:N 관계를 가진다.
* `idx = 0`인 이미지를 대표 이미지로 사용할 수 있다.
* 데이터베이스 정보 삭제와 실제 파일 삭제는 별도로 처리해야 한다.
* 운영 환경에서는 로컬 디렉터리보다 외부 스토리지 사용을 고려할 수 있다.

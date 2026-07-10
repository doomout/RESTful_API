# Service 계층과 Controller 계층

Spring Boot는 일반적으로 **계층형(Layered Architecture)** 구조를 사용한다.

```text
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
```

각 계층은 자신의 역할만 담당하며, 다른 계층의 역할을 대신하지 않는다.

---

# Controller 계층

## Controller란?

Controller는 **클라이언트의 HTTP 요청(Request)을 가장 먼저 받는 계층**이다.

주요 역할

* URL 요청 처리
* 요청 데이터(Request) 수신
* Service 호출
* 결과(Response) 반환

Controller는 **비즈니스 로직을 직접 처리하지 않는다.**

---

## Controller의 특징

* HTTP 요청을 처리한다.
* Service를 호출한다.
* JSON 또는 View를 반환한다.
* 비즈니스 로직은 작성하지 않는다.

예제

```java
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable Long pno) {
        return productService.read(pno);
    }
}
```

Controller는 요청을 받아 Service에게 전달하는 역할만 수행한다.

---

# Service 계층

## Service란?

Service는 **애플리케이션의 핵심 비즈니스 로직을 처리하는 계층**이다.

Controller와 Repository 사이에서 필요한 작업을 수행한다.

주요 역할

* 비즈니스 로직 처리
* 데이터 검증
* 여러 Repository 조합
* DTO ↔ Entity 변환
* 트랜잭션 관리

---

## Service의 특징

* 핵심 기능을 구현한다.
* Repository를 호출한다.
* Controller와 Database를 분리한다.
* 여러 Repository를 함께 사용할 수 있다.

예제

```java
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public ProductDTO read(Long pno) {

        ProductEntity entity =
                repository.findById(pno)
                          .orElseThrow();

        return entityToDTO(entity);
    }
}
```

Service는 필요한 데이터를 조회하고 가공한 뒤 Controller에 전달한다.

---

# Repository 계층

Repository는 **데이터베이스와 직접 통신하는 계층**이다.

주요 역할

* 데이터 조회
* 데이터 저장
* 데이터 수정
* 데이터 삭제

예제

```java
public interface ProductRepository
        extends JpaRepository<ProductEntity, Long> {
}
```

Repository는 SQL 대신 JPA를 이용하여 데이터를 관리한다.

---

# 계층별 역할

| 계층         | 역할             |
| ---------- | -------------- |
| Controller | HTTP 요청과 응답 처리 |
| Service    | 비즈니스 로직 처리     |
| Repository | 데이터베이스 접근      |
| Database   | 데이터 저장         |

---

# 요청 처리 과정

상품 조회 요청

```text
Client
   │
GET /products/10
   │
   ▼
Controller
   │
read(10)
   │
   ▼
Service
   │
비즈니스 로직 수행
   │
   ▼
Repository
   │
findById(10)
   │
   ▼
Database
```

조회 결과는 다시 역순으로 반환된다.

```text
Database
   │
   ▼
Repository
   │
   ▼
Service
   │
DTO 생성
   │
   ▼
Controller
   │
JSON 반환
   │
   ▼
Client
```

---

# 왜 계층을 나누는가?

계층을 분리하면 각각의 역할이 명확해지고 유지보수가 쉬워진다.

예를 들어

Controller에서 모든 기능을 처리하면

* 코드가 길어진다.
* 테스트가 어려워진다.
* 재사용이 어렵다.

반대로 Service로 분리하면

* 여러 Controller에서 같은 기능을 사용할 수 있다.
* 테스트가 쉬워진다.
* 코드의 책임이 명확해진다.

---

# 계층별 책임

### Controller

✔ 요청 받기

✔ 요청 데이터 추출

✔ Service 호출

✔ 응답 반환

❌ DB 접근

❌ 비즈니스 로직 처리

---

### Service

✔ 핵심 비즈니스 로직

✔ DTO ↔ Entity 변환

✔ 여러 Repository 호출

✔ 데이터 검증

✔ 트랜잭션 관리

❌ HTTP 요청 처리

---

### Repository

✔ 데이터 조회

✔ 데이터 저장

✔ 데이터 수정

✔ 데이터 삭제

❌ 비즈니스 로직 처리

---

# 계층 간 호출 관계

```text
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Database
```

호출은 항상 위에서 아래로 진행되며, 일반적으로 역방향 호출은 하지 않는다.

---

# 계층 분리의 장점

* 역할과 책임이 명확하다.
* 코드의 재사용성이 높다.
* 유지보수가 쉽다.
* 테스트가 용이하다.
* 코드의 가독성이 향상된다.
* 비즈니스 로직을 한곳에서 관리할 수 있다.

---

# 핵심 정리

* **Controller**는 클라이언트의 요청을 받아 Service에 전달하고 결과를 반환하는 역할을 담당한다.
* **Service**는 애플리케이션의 핵심 비즈니스 로직을 처리하며, Repository를 이용해 데이터를 관리한다.
* **Repository**는 데이터베이스와 직접 통신하는 계층이다.
* 계층을 분리하면 코드의 책임이 명확해지고 유지보수와 테스트가 쉬워진다.
* 일반적인 Spring Boot의 호출 흐름은 **Client → Controller → Service → Repository → Database** 순으로 이루어진다.

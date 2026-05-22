# Spring 의존성 주입과 주요 어노테이션 정리

## 1. 의존성 주입(DI)이란?

DI는 Dependency Injection의 약자이다.

객체가 필요한 의존 객체를 직접 생성하지 않고, 외부에서 주입받는 방식이다.

```java
// 직접 생성
MemberService service = new MemberService();

// DI 방식
private final MemberService memberService;
```

Spring에서는 객체 생성과 의존성 연결을 Spring Container가 관리한다.

---

## 2. 의존성 자동 주입

Spring은 Bean으로 등록된 객체를 필요한 곳에 자동으로 주입할 수 있다.

대표 방식은 다음과 같다.

---

## 2.1 생성자 주입

가장 권장되는 방식이다.

```java
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
```

### 장점

- 의존성이 반드시 주입됨
- final 사용 가능
- 테스트 코드 작성이 쉬움
- 순환 참조 문제를 빨리 발견 가능

실무에서는 생성자 주입을 가장 많이 사용한다.

---

## 2.2 Setter 주입

Setter 메서드를 통해 의존성을 주입한다.

```java
@RestController
public class MemberController {

    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
}
```

### 특징

- 선택적 의존성에 사용할 수 있음
- 객체 생성 후 의존성이 변경될 수 있음

일반적인 필수 의존성에는 잘 사용하지 않는다.

---

## 2.3 필드 주입

필드에 바로 주입하는 방식이다.

```java
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;
}
```

### 특징

- 코드가 짧음
- 테스트가 불편함
- final 사용 불가
- 의존성이 숨겨짐

학습 예제에서는 자주 보이지만 실무에서는 권장되지 않는다.

---

## 3. 클래스 선언부에서 사용하는 주요 어노테이션

## 3.1 Bean 등록 관련 어노테이션

### @Component

Spring Bean으로 등록한다.

```java
@Component
public class CommonUtil {
}
```

가장 일반적인 Bean 등록 어노테이션이다.

---

### @Service

비즈니스 로직 계층에 사용한다.

```java
@Service
public class MemberService {
}
```

내부적으로는 `@Component`와 같은 역할을 하지만, 의미상 Service 계층임을 나타낸다.

---

### @Repository

데이터 접근 계층에 사용한다.

```java
@Repository
public class MemberRepository {
}
```

DB 접근 클래스에 사용한다.

Spring이 예외를 DataAccessException 계열로 변환하는 기능도 제공한다.

---

### @Controller

MVC Controller에 사용한다.

```java
@Controller
public class PageController {
}
```

주로 View를 반환하는 웹 컨트롤러에 사용한다.

---

### @RestController

REST API Controller에 사용한다.

```java
@RestController
public class MemberApiController {
}
```

`@Controller`와 `@ResponseBody`를 합친 어노테이션이다.

메서드 반환값이 View 이름이 아니라 JSON 응답으로 처리된다.

---

## 3.2 설정 관련 어노테이션

### @Configuration

설정 클래스를 의미한다.

```java
@Configuration
public class AppConfig {
}
```

---

### @Bean

메서드의 반환 객체를 Spring Bean으로 등록한다.

```java
@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

직접 만든 클래스가 아니거나, 생성 과정을 직접 제어해야 할 때 사용한다.

---

## 4. MVC / REST API 관련 클래스 어노테이션

### @RequestMapping

Controller 클래스나 메서드에 공통 URL을 지정한다.

```java
@RestController
@RequestMapping("/api/members")
public class MemberController {
}
```

---

## 5. 메서드에서 사용하는 주요 어노테이션

## 5.1 HTTP Method 매핑

### @GetMapping

조회 요청에 사용한다.

```java
@GetMapping
public List<MemberDto> getMembers() {
    return memberService.getMembers();
}
```

---

### @PostMapping

생성 요청에 사용한다.

```java
@PostMapping
public MemberDto createMember(@RequestBody MemberCreateRequest request) {
    return memberService.createMember(request);
}
```

---

### @PutMapping

전체 수정 요청에 사용한다.

```java
@PutMapping("/{id}")
public MemberDto updateMember(
        @PathVariable Long id,
        @RequestBody MemberUpdateRequest request
) {
    return memberService.updateMember(id, request);
}
```

---

### @PatchMapping

부분 수정 요청에 사용한다.

```java
@PatchMapping("/{id}")
public MemberDto updateMemberName(
        @PathVariable Long id,
        @RequestBody MemberNameUpdateRequest request
) {
    return memberService.updateMemberName(id, request);
}
```

---

### @DeleteMapping

삭제 요청에 사용한다.

```java
@DeleteMapping("/{id}")
public void deleteMember(@PathVariable Long id) {
    memberService.deleteMember(id);
}
```

---

## 5.2 요청 데이터 처리 어노테이션

### @PathVariable

URL 경로에 포함된 값을 가져온다.

```java
@GetMapping("/{id}")
public MemberDto getMember(@PathVariable Long id) {
    return memberService.getMember(id);
}
```

요청 예시:

```http
GET /api/members/1
```

---

### @RequestParam

Query String 값을 가져온다.

```java
@GetMapping
public List<MemberDto> searchMembers(@RequestParam String keyword) {
    return memberService.searchMembers(keyword);
}
```

요청 예시:

```http
GET /api/members?keyword=kim
```

---

### @RequestBody

HTTP 요청 Body의 JSON 데이터를 객체로 변환한다.

```java
@PostMapping
public MemberDto createMember(@RequestBody MemberCreateRequest request) {
    return memberService.createMember(request);
}
```

요청 예시:

```json
{
  "name": "kim",
  "email": "kim@test.com"
}
```

---

### @ModelAttribute

Form 데이터나 Query Parameter를 객체로 바인딩한다.

```java
@GetMapping("/search")
public List<MemberDto> search(@ModelAttribute MemberSearchCondition condition) {
    return memberService.search(condition);
}
```

주로 검색 조건, 폼 데이터 처리에 사용한다.

---

## 5.3 응답 처리 관련 어노테이션

### @ResponseBody

메서드 반환값을 HTTP 응답 Body로 직접 반환한다.

```java
@ResponseBody
@GetMapping("/hello")
public String hello() {
    return "hello";
}
```

`@RestController`를 사용하면 보통 직접 붙이지 않아도 된다.

---

### ResponseEntity

어노테이션은 아니지만 REST API에서 자주 사용한다.

HTTP 상태 코드와 응답 Body를 함께 제어할 수 있다.

```java
@GetMapping("/{id}")
public ResponseEntity<MemberDto> getMember(@PathVariable Long id) {
    MemberDto member = memberService.getMember(id);
    return ResponseEntity.ok(member);
}
```

생성 성공 응답:

```java
return ResponseEntity.status(HttpStatus.CREATED).body(result);
```

---

## 6. 검증 관련 어노테이션

### @Valid

요청 DTO 검증을 실행한다.

```java
@PostMapping
public MemberDto createMember(@Valid @RequestBody MemberCreateRequest request) {
    return memberService.createMember(request);
}
```

DTO 예시:

```java
public class MemberCreateRequest {

    @NotBlank
    private String name;

    @Email
    private String email;
}
```

---

## 7. 자주 사용하는 검증 어노테이션

### @NotNull

null을 허용하지 않는다.

```java
@NotNull
private Long id;
```

---

### @NotBlank

null, 빈 문자열, 공백 문자열을 허용하지 않는다.

```java
@NotBlank
private String name;
```

---

### @NotEmpty

null과 빈 값을 허용하지 않는다.

```java
@NotEmpty
private List<String> tags;
```

---

### @Email

이메일 형식인지 검증한다.

```java
@Email
private String email;
```

---

### @Size

문자열, 배열, 컬렉션 크기를 검증한다.

```java
@Size(min = 2, max = 20)
private String username;
```

---

## 8. 정리

### Bean 등록

| 어노테이션 | 역할 |
|---|---|
| @Component | 일반 Bean |
| @Service | 비즈니스 로직 계층 |
| @Repository | 데이터 접근 계층 |
| @Controller | MVC Controller |
| @RestController | REST API Controller |
| @Configuration | 설정 클래스 |
| @Bean | 메서드 반환 객체를 Bean 등록 |

---

### 요청 매핑

| 어노테이션 | HTTP Method |
|---|---|
| @GetMapping | GET |
| @PostMapping | POST |
| @PutMapping | PUT |
| @PatchMapping | PATCH |
| @DeleteMapping | DELETE |
| @RequestMapping | 공통 매핑 또는 범용 매핑 |

---

### 요청 데이터 처리

| 어노테이션 | 역할 |
|---|---|
| @PathVariable | URL 경로 값 |
| @RequestParam | Query String |
| @RequestBody | JSON Body |
| @ModelAttribute | Form 또는 Query 객체 바인딩 |
| @Valid | DTO 검증 실행 |

---

## 9. 실무 추천

의존성 주입은 생성자 주입을 기본으로 사용한다.

```java
private final MemberService memberService;

public MemberController(MemberService memberService) {
    this.memberService = memberService;
}
```

REST API Controller에서는 보통 다음 조합을 많이 사용한다.

```java
@RestController
@RequestMapping("/api/members")
public class MemberController {

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(
            @Valid @RequestBody MemberCreateRequest request
    ) {
        MemberDto result = memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
```
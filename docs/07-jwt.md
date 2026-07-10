# JWT (JSON Web Token)

## JWT란?

JWT(JSON Web Token)는 **사용자의 인증(Authentication) 정보를 안전하게 전달하기 위한 토큰(Token) 기반 인증 방식**이다.

기존의 세션(Session) 방식과 달리 서버가 로그인 정보를 저장하지 않고, 클라이언트가 토큰을 보관하여 요청할 때마다 함께 전달한다.

---

# JWT 인증 과정

```text
Client
   │
   │ 로그인 요청 (ID / Password)
   ▼
Server
   │
   │ 사용자 인증
   ▼
JWT 생성
   │
   ▼
Client
   │
   │ JWT 저장
   │
   │ 이후 모든 요청에 JWT 포함
   ▼
Server
   │
JWT 검증
   │
   ▼
API 처리
```

---

# JWT의 구조

JWT는 세 부분으로 구성된다.

```text
Header.Payload.Signature
```

예시

```text
xxxxx.yyyyy.zzzzz
```

각 부분은 `.`으로 구분된다.

---

# Header

Header에는 토큰의 정보가 저장된다.

예시

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

| 항목  | 설명         |
| --- | ---------- |
| alg | 암호화 알고리즘   |
| typ | 토큰 타입(JWT) |

---

# Payload

Payload에는 사용자 정보(Claim)가 저장된다.

예시

```json
{
  "username": "admin",
  "role": "USER",
  "exp": 1750000000
}
```

대표적인 Claim

| Claim | 설명     |
| ----- | ------ |
| sub   | 사용자 ID |
| role  | 권한     |
| exp   | 만료 시간  |
| iat   | 발급 시간  |
| iss   | 발급자    |

Payload는 암호화되는 것이 아니라 **Base64로 인코딩**되므로 민감한 정보(비밀번호 등)는 저장하면 안 된다.

---

# Signature

Signature는 토큰의 위변조 여부를 확인하기 위한 값이다.

생성 방식

```text
HMACSHA256(
    Header + "." + Payload,
    SecretKey
)
```

Secret Key를 모르면 Signature를 생성할 수 없으므로 토큰 위조를 방지할 수 있다.

---

# JWT 인증 흐름

### 1. 로그인

```text
POST /login
```

아이디와 비밀번호를 서버로 전송한다.

---

### 2. 로그인 성공

```text
Server
    │
    ▼
JWT 생성
    │
    ▼
Client
```

서버는 JWT를 생성하여 클라이언트에게 전달한다.

---

### 3. 토큰 저장

클라이언트는 JWT를 저장한다.

예시

* Local Storage
* Session Storage
* HttpOnly Cookie

---

### 4. API 요청

```http
GET /products
Authorization: Bearer JWT_TOKEN
```

Authorization 헤더에 JWT를 포함하여 요청한다.

---

### 5. 서버 검증

서버는

* 토큰 존재 여부
* Signature 검증
* 만료 시간(exp)
* 사용자 정보

를 확인한 후 요청을 처리한다.

---

# JWT의 장점

### Stateless

서버가 로그인 상태를 저장하지 않는다.

즉,

```text
사용자 1
사용자 2
사용자 3
```

모든 로그인 정보를 서버가 저장하지 않아도 된다.

---

### 확장성이 좋다.

여러 서버가 있어도 로그인 정보를 공유할 필요가 없다.

```text
Client
   │
   ▼
Load Balancer
   │
 ┌─┴─────┐
 ▼       ▼
Server1 Server2
```

어느 서버가 요청을 받아도 JWT만 검증하면 된다.

---

### 모바일에 적합

앱(Android, iOS)에서도 쉽게 사용할 수 있다.

---

# JWT의 단점

### 토큰 탈취 위험

토큰을 탈취당하면 만료 전까지 사용할 수 있다.

따라서

* HTTPS 사용
* 짧은 만료 시간
* Refresh Token 사용

등이 필요하다.

---

### 로그아웃이 어렵다.

JWT는 서버가 저장하지 않기 때문에

```text
로그아웃
```

해도 이미 발급된 토큰은 만료 전까지 유효하다.

이를 해결하기 위해

* Refresh Token
* Black List

등을 사용한다.

---

# Session과 JWT 비교

| 구분      | Session  | JWT       |
| ------- | -------- | --------- |
| 로그인 정보  | 서버 저장    | 클라이언트 저장  |
| 서버 메모리  | 사용       | 거의 사용 안 함 |
| 확장성     | 낮음       | 높음        |
| 모바일     | 불편       | 편리        |
| 서버 여러 대 | 세션 공유 필요 | 공유 불필요    |

---

# Spring Boot에서 JWT 사용 순서

```text
1. 로그인 요청

↓

2. 사용자 인증

↓

3. JWT 생성

↓

4. Client에게 JWT 전달

↓

5. Authorization Header에 JWT 포함

↓

6. JWT Filter에서 토큰 검증

↓

7. SecurityContext에 인증 정보 저장

↓

8. Controller 실행
```

---

# JWT와 Spring Security

JWT는 **인증 정보를 담는 토큰**이고,

Spring Security는 **인증(Authentication)과 인가(Authorization)를 처리하는 보안 프레임워크**이다.

일반적인 구조는 다음과 같다.

```text
Client
    │
    ▼
JWT Filter
    │
    ▼
Spring Security
    │
    ▼
Controller
```

JWT Filter가 토큰을 검증한 후, 인증된 사용자 정보를 Spring Security에 전달한다.

---

# JWT에서 자주 사용하는 클래스

| 클래스                   | 역할            |
| --------------------- | ------------- |
| JwtUtil               | JWT 생성 및 검증   |
| JwtFilter             | 요청마다 JWT 검사   |
| UserDetailsService    | 사용자 정보 조회     |
| Authentication        | 인증 정보 저장      |
| SecurityContextHolder | 현재 로그인 사용자 관리 |

---

# 핵심 정리

* JWT는 **토큰 기반 인증 방식**이다.
* 서버는 로그인 상태를 저장하지 않는 **Stateless** 구조를 사용한다.
* JWT는 **Header, Payload, Signature** 세 부분으로 구성된다.
* Payload에는 사용자 정보를 저장하지만 **비밀번호와 같은 민감한 정보는 저장하지 않는다.**
* 클라이언트는 JWT를 `Authorization: Bearer {Token}` 헤더에 담아 요청한다.
* 서버는 토큰의 **Signature**와 **만료 시간(exp)** 을 검증한 후 요청을 처리한다.
* JWT는 서버 확장성이 뛰어나며 REST API와 모바일 환경에서 많이 사용된다.
* Spring Boot에서는 **Spring Security + JWT Filter**를 함께 사용하여 인증과 인가를 처리한다.

# Axios와 API 서버 호출

## Axios란?

Axios는 **브라우저와 Node.js에서 HTTP 요청을 쉽게 보낼 수 있도록 도와주는 JavaScript 라이브러리**이다.

REST API 서버와 데이터를 주고받을 때 가장 많이 사용된다.

Axios는 내부적으로 HTTP 통신을 수행하며, Promise 기반으로 동작한다.

---

# Axios 설치

```bash
npm install axios
```

또는

```bash
yarn add axios
```

---

# Axios 기본 사용법

```javascript
import axios from "axios";
```

기본 요청

```javascript
axios.get("http://localhost:8080/api/products");
```

Promise 방식

```javascript
axios.get("/api/products")
    .then(response => {
        console.log(response.data);
    })
    .catch(error => {
        console.log(error);
    });
```

Async / Await 방식

```javascript
const response = await axios.get("/api/products");

console.log(response.data);
```

---

# HTTP Method와 Axios

## GET (조회)

```javascript
const response = await axios.get("/api/products");
```

데이터 조회에 사용한다.

---

## POST (생성)

```javascript
await axios.post("/api/products", {
    pname: "노트북",
    price: 1200000
});
```

새로운 데이터를 생성한다.

---

## PUT (전체 수정)

```javascript
await axios.put("/api/products/10", {
    pname: "모니터",
    price: 300000
});
```

기존 데이터를 전체 수정한다.

---

## PATCH (일부 수정)

```javascript
await axios.patch("/api/products/10", {
    price: 280000
});
```

일부 데이터만 수정한다.

---

## DELETE (삭제)

```javascript
await axios.delete("/api/products/10");
```

데이터를 삭제한다.

---

# Axios 응답(Response)

```javascript
const response = await axios.get("/api/products");
```

응답 객체

| 속성         | 설명         |
| ---------- | ---------- |
| data       | 실제 응답 데이터  |
| status     | HTTP 상태 코드 |
| statusText | 상태 메시지     |
| headers    | 응답 헤더      |
| config     | 요청 설정 정보   |

예제

```javascript
console.log(response.data);
console.log(response.status);
```

---

# Axios 요청(Request)

POST 요청 예시

```javascript
await axios.post("/api/products", {
    pname: "키보드",
    price: 50000
});
```

Body에 JSON 데이터를 전달한다.

Spring Boot에서는

```java
@PostMapping
public ProductDTO register(
        @RequestBody ProductDTO dto) {
}
```

와 같이 `@RequestBody`를 이용하여 JSON을 받을 수 있다.

---

# Query Parameter 전달

예제

```javascript
await axios.get("/api/products", {
    params: {
        page: 1,
        size: 10
    }
});
```

실제 요청

```text
GET /api/products?page=1&size=10
```

Spring Boot

```java
@GetMapping
public PageDTO list(
        int page,
        int size) {
}
```

---

# Path Variable 전달

Axios

```javascript
await axios.get("/api/products/100");
```

Spring Boot

```java
@GetMapping("/{pno}")
public ProductDTO read(
        @PathVariable Long pno) {
}
```

---

# JWT와 함께 사용하기

JWT 인증이 필요한 경우 Authorization 헤더를 추가한다.

```javascript
await axios.get("/api/products", {
    headers: {
        Authorization: `Bearer ${token}`
    }
});
```

Spring Security는 Authorization 헤더의 JWT를 검증한 후 요청을 처리한다.

---

# Axios Instance

API 주소가 반복되는 경우 Instance를 생성하여 사용할 수 있다.

```javascript
import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/api"
});
```

사용 예시

```javascript
const response = await api.get("/products");
```

매번 서버 주소를 작성할 필요가 없다.

---

# Interceptor

Interceptor는 요청(Request)과 응답(Response)을 가로채 공통 작업을 수행하는 기능이다.

### Request Interceptor

JWT를 자동으로 추가할 수 있다.

```javascript
api.interceptors.request.use(config => {

    const token = localStorage.getItem("token");

    if(token){
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});
```

모든 요청에 JWT가 자동으로 포함된다.

---

### Response Interceptor

로그인 만료 처리

```javascript
api.interceptors.response.use(
    response => response,

    error => {

        if(error.response.status === 401){
            alert("로그인이 필요합니다.");
        }

        return Promise.reject(error);
    }
);
```

401 Unauthorized 발생 시 공통 처리를 할 수 있다.

---

# Axios와 Spring Boot 호출 흐름

```text
React / Vue

        │

axios.get()

        │

        ▼

HTTP Request

        │

        ▼

Spring Controller

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

응답은 다시 역순으로 반환된다.

```text
Database

      │

Repository

      │

Service

      │

Controller

      │

HTTP Response(JSON)

      │

axios

      │

React / Vue
```

---

# Axios 사용 시 주의사항

* GET 요청은 조회에만 사용한다.
* POST는 데이터 생성에 사용한다.
* PUT은 전체 수정, PATCH는 일부 수정에 사용한다.
* 예외 처리를 위해 `try-catch` 또는 `.catch()`를 사용한다.
* JWT 인증 시 Authorization 헤더를 함께 전송한다.
* API 주소는 Axios Instance로 관리하면 유지보수가 쉽다.

---

# 핵심 정리

* Axios는 HTTP 통신을 위한 JavaScript 라이브러리이다.
* REST API 서버와 JSON 데이터를 주고받을 때 가장 많이 사용된다.
* Promise 기반이며 `async/await`와 함께 사용하면 코드가 간결해진다.
* GET, POST, PUT, PATCH, DELETE 등 모든 HTTP Method를 지원한다.
* `params`를 이용해 Query Parameter를 전달하고, URL을 통해 Path Variable을 전달할 수 있다.
* JWT 인증 시 `Authorization: Bearer {token}` 헤더를 추가한다.
* Axios Instance와 Interceptor를 사용하면 공통 설정과 인증 처리를 효율적으로 관리할 수 있다.

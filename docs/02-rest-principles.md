# REST 원칙 정리
## 1. REST란?

Representational State Transfer

자원을 URI로 표현하고 HTTP 메서드로 조작하는 방식

---

## 2. REST 핵심 구성

### Resource

```plaintext
/users
/products
/orders
```

### Method
- GET
- POST
- PUT
- PATCH
- DELETE

### Representation
- JSON
- XML

현재는 JSON이 사실상 표준

## 3. REST 제약조건
- Client-Server 
    - 역할 분리

- Stateless
    - 서버가 클라이언트 상태 저장 X
    - JWT 구조가 대표적

- Cacheable
    - 응답 캐싱 가능

- Uniform Interface
    - 일관된 API 규칙
```text
GET /users
POST /users
DELETE /users/1
```

- Layered System

    - 중간 계층 가능
```text
Gateway
Load Balancer
Security Layer
```

## 4. 좋은 REST URI 설계
- 잘못된 예 : 동사를 URI에 넣음
```text
/getUsers
/deleteProduct
/createOrder
```

- 좋은 예: 행위는 HTTP Method로 처리
```text
/users
/products
/orders
```

## 5. XML vs JSON

과거: XML 많이 사용
```xml
<user>
  <name>kim</name>
</user>
```

현재: JSON 사용
```json
{
  "name": "kim"
}
```
- JSON 장점
    - 가벼움
    - 가독성 좋음
    - JavaScript 친화적
# REST 원칙 정리
## 1. REST란?
```text
REST(Representational State Transfer)는
웹의 자원(Resource)을 URI로 표현하고,
HTTP Method(GET, POST, PUT, DELETE 등)를 이용하여
자원을 조회(Create, Read, Update, Delete)하는
아키텍처 스타일이다.
```

## 2. REST 핵심 구성

### Resource

```plaintext
/users
/products
/orders
```

### Method
| Method | 역할 |
|--------|------|
| GET | 조회 |
| POST | 생성 |
| PUT | 전체 수정 |
| PATCH | 일부 수정 |
| DELETE | 삭제 |

### Representation
- JSON
- XML

## 3. REST 제약조건
- Client-Server 
    - 역할 분리

- Stateless
    - 서버가 클라이언트 상태 저장 X
    - JWT는 Stateless 구조를 구현하는 대표적인 인증 방식이다.

- Cacheable
    - 응답 캐싱 가능

- Uniform Interface
    - URI는 Resource만 표현한다.
    - HTTP Method로 행위를 표현한다.
    - 응답 형식(JSON 등)을 일관되게 유지한다.
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

- Code on Demand (선택사항)
    - 필요 시 서버가 코드를 클라이언트에 전달
    - JavaScript 다운로드 등이 대표적인 예
    - 실제 REST API에서는 거의 사용되지 않는다.

## 4. 좋은 REST URI 설계
- 행위는 HTTP Method로 처리
```text
/users
/products
/orders
```
- 소문자를 사용한다.

- 복수형 명사를 사용한다.
```text
/users
/products
```
- 동사를 URI에 넣지 않는다.

- 계층 구조를 표현한다.
```text
/users/1/orders
```
- 확장자를 넣지 않는다.
```text
/users.json (X)
/users (O)
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
    - 가볍다.
    - 사람이 읽기 쉽다.
    - JavaScript와 호환성이 좋다.
    - 대부분의 REST API에서 표준처럼 사용된다.

## RESTful API란?

REST 원칙을 최대한 준수하여 설계한 API를
RESTful API라고 한다.

- 대표적인 예
```text
GET /products
POST /products
GET /products/1
PUT /products/1
DELETE /products/1
```
REST 원칙을 모두 지키지 못하더라도  
REST의 철학을 최대한 따르는 API를  
RESTful API라고 부른다.
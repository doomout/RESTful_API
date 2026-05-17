# API 설계 기초
## 1. CRUD 매핑

### Create
```js
POST /users
```

### Read
```js
GET /users

GET /users/1
```

### Update
```js
PUT /users/1

PATCH /users/1
```

### Delete
```js
DELETE /users/1
```

## 2. PUT vs PATCH

### PUT : 전체 수정

```json
{
  "name": "kim",
  "age": 30
}
```

### PATCH : 부분 수정

```json
{
  "age": 31
}
```

## 3. Query String
검색
```js
GET /products?keyword=phone
```
정렬
```js
GET /products?sort=price
```
페이지
```js
GET /products?page=1&size=10
```
필터
```js
GET /products?brand=apple
```

## 4. Path Variable
```js
GET /users/1
GET /orders/100
```
특정 리소스 조회

## 5. 상태 코드
```js
200 OK

201 Created

204 No Content

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

500 Internal Server Error
```
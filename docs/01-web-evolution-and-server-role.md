# 웹의 발전과 서버 역할 변화

---

## 1. 서버의 역할

초기 웹 서버의 역할은 매우 단순했다.

클라이언트(브라우저)가 요청하면:

- HTML 파일 제공
- 이미지 제공
- CSS 제공

즉 정적인 데이터를 전달하는 역할만 수행했다.

```plaintext
Browser → Server → HTML 반환
```

## 2. Model 1 방식

초기 Java 웹 개발에서 많이 사용

- 대표 기술:
    - JSP
    - Servlet

- JSP 내부에서
    - HTML 생성
    - DB 조회
    - 비즈니스 로직 처리

- 문제점
    - 유지보수 어려움
    - 역할 분리 안됨
    - 코드 복잡도 증가

## 3. Model 2 방식

역할 분리 필요
```text
Client
 ↓
Controller(Servlet)
 ↓
Business Logic
 ↓
JSP(View)
```
Controller가 중간에서 요청을 처리

- 장점
    - 유지 보수 향상
    - 역할 분리
    - 테스트 용이

## 4. MVC 패턴
Model : 데이터 처리

View : 화면 출력

Controller : 요청 처리

```text
Client → Controller → Model → View
```

## 5. 과거 웹 프로그래밍 한계

서버가 HTML을 직접 생성  

문제:  
- 모바일 대응 어려움
- 다양한 클라이언트 지원 어려움
- 프론트 개발 생산성 낮음

## 6. Web 2.0 시대

AJAX 등장

페이지 전체 새로고침 없이 일부 데이터 요청 가능
```js
fetch("/users")
```

## 7. API 서버 등장

서버가 HTML 대신 데이터만 제공

```text
Client → API Server → JSON
```

클라이언트:

- Web
- Mobile
- Desktop
- 외부 서비스

## 8. 실무 경험 연결

현재 React + Spring 구조도 동일하다.

- React: View 담당

- Spring: API 담당

- PostgreSQL: 데이터 저장
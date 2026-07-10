## MariaDB 12 정보
- Todo에 사용할 DB 
- port: 3000
- Spring Data JPA, MariaDB Driver 항목 추가
- DB : bootdb2 
- user : bootdb2user
- pw : bootdb2user

# JPA (Java Persistence API)

## JPA란?

JPA(Java Persistence API)는 **자바 객체(Entity)와 데이터베이스 테이블을 연결(Mapping)하여 데이터를 관리하는 표준 인터페이스**이다.

기존에는 SQL을 직접 작성하여 데이터를 조회하고 수정했지만, JPA는 객체 중심으로 개발할 수 있도록 도와준다.

> **JPA는 구현체가 아닌 '표준(인터페이스)'이다.**
> 대표적인 구현체로 **Hibernate**가 가장 많이 사용된다.

---

# JPA의 동작 구조

```
Application
      │
      ▼
     JPA (표준)
      │
      ▼
 Hibernate (구현체)
      │
      ▼
 Database
```

개발자는 JPA를 사용하지만, 실제 SQL 생성과 실행은 Hibernate가 담당한다.

---

# JPA의 장점

### 1. SQL 작성 감소

기존 JDBC

```sql
SELECT * FROM product WHERE pno = 1;
```

JPA

```java
repository.findById(1L);
```

SQL을 직접 작성하지 않아도 된다.

---

### 2. 객체 중심 개발

테이블이 아니라 객체를 다루므로 Java 개발에 더욱 자연스럽다.

```java
Product product = repository.findById(1L).get();
```

---

### 3. 생산성 향상

기본적인 CRUD를 Repository만으로 처리할 수 있다.

```java
save()
findById()
findAll()
delete()
```

---

### 4. 데이터베이스 독립성

JPA는 SQL을 자동 생성하므로 MySQL, MariaDB, PostgreSQL 등 데이터베이스가 변경되어도 코드 수정이 최소화된다.

---

### 5. 페이징 및 정렬 지원

Spring Data JPA는 Pageable을 이용하여 간단하게 페이징을 처리할 수 있다.

```java
Page<ProductEntity> list = repository.findAll(pageable);
```

---

# JPA의 단점

### 복잡한 조회는 어렵다.

여러 테이블을 조인하거나 복잡한 조건이 필요한 경우에는 JPA만으로는 한계가 있다.

이럴 때는 다음과 같은 방법을 사용한다.

* JPQL
* QueryDSL
* Native Query(SQL)

---

### 성능을 이해해야 한다.

JPA는 SQL을 자동 생성하기 때문에 내부 동작을 이해하지 못하면 예상하지 못한 SQL이 실행될 수 있다.

대표적인 예

* N+1 문제
* Lazy Loading
* Fetch Join

---

# Entity란?

Entity는 데이터베이스의 테이블과 매핑되는 객체이다.

```java
@Entity
public class ProductEntity {
}
```

보통 하나의 Entity는 하나의 테이블과 연결된다.

---

# Repository란?

Repository는 Entity를 데이터베이스와 연결해 주는 인터페이스이다.

```java
public interface ProductRepository
        extends JpaRepository<ProductEntity, Long> {
}
```

JpaRepository를 상속하면 기본 CRUD 기능이 자동으로 제공된다.

---

# 영속성(Persistence)

JPA는 객체를 **영속성 컨텍스트(Persistence Context)** 에서 관리한다.

영속 상태의 객체는 변경 사항이 자동으로 감지되어 데이터베이스에 반영된다.

이를 **Dirty Checking(변경 감지)** 이라고 한다.

```java
ProductEntity product = repository.findById(1L).get();

product.changeName("새 상품명");

// save()를 호출하지 않아도 변경 사항이 반영될 수 있다.
```

---

# JPA의 주요 어노테이션

| 어노테이션           | 설명             |
| --------------- | -------------- |
| @Entity         | Entity 클래스 지정  |
| @Table          | 테이블명 지정        |
| @Id             | 기본키(PK) 지정     |
| @GeneratedValue | PK 자동 생성       |
| @Column         | 컬럼 설정          |
| @Transient      | DB에 저장하지 않는 필드 |
| @Enumerated     | Enum 저장 방식 지정  |
| @Embedded       | 내장 객체 매핑       |
| @OneToOne       | 1:1 관계         |
| @OneToMany      | 1:N 관계         |
| @ManyToOne      | N:1 관계         |
| @ManyToMany     | N:N 관계         |
| @JoinColumn     | FK 컬럼 지정       |

---

# JPA와 Hibernate의 관계

```
JPA
 ├── Hibernate (가장 많이 사용)
 ├── EclipseLink
 └── OpenJPA
```

JPA는 표준이고, Hibernate는 JPA를 구현한 라이브러리이다.

---

# Spring Data JPA

Spring Data JPA는 JPA를 더욱 쉽게 사용할 수 있도록 만든 Spring의 라이브러리이다.

예를 들어

```java
findAll()

findById()

save()

delete()
```

같은 메서드를 직접 구현하지 않아도 사용할 수 있다.

또한 메서드 이름만으로 조회 쿼리를 생성할 수도 있다.

```java
findByName()

findByPriceGreaterThan()

findByTitleContaining()
```

---

# JPA와 JPQL의 차이

### JPA

객체(Entity)를 저장, 수정, 삭제하는 기술이다.

```java
repository.save(product);
```

---

### JPQL

Entity를 대상으로 조회(Query)하는 객체 지향 쿼리 언어이다.

```java
select p
from ProductEntity p
where p.price > 10000
```

JPQL은 SQL과 비슷하지만 **테이블명이 아닌 Entity 이름과 필드명을 사용**한다.

---

# QueryDSL과의 관계

복잡한 검색 기능에서는 JPQL 대신 QueryDSL을 많이 사용한다.

```
JPA
   │
   ├── CRUD
   │
   ├── JPQL
   │
   └── QueryDSL
```

QueryDSL은 컴파일 시점에 문법 오류를 확인할 수 있고, 동적 검색 조건을 작성하기 쉽다는 장점이 있다.

---

# 핵심 정리

* JPA는 자바 객체와 데이터베이스를 연결하는 표준 인터페이스이다.
* Hibernate는 JPA의 대표적인 구현체이다.
* Spring Data JPA는 JPA를 더욱 쉽게 사용할 수 있도록 지원한다.
* CRUD는 대부분 SQL 없이 처리할 수 있다.
* 복잡한 조회는 JPQL 또는 QueryDSL을 사용한다.
* 영속성 컨텍스트를 통해 객체를 관리하며 변경 감지(Dirty Checking)를 지원한다.
* JPA는 객체 중심 개발을 가능하게 하여 생산성과 유지보수성을 높여준다.

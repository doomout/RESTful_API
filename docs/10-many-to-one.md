# JPA 다대일(Many-To-One) 관계

## 다대일(Many-To-One)이란?

다대일(Many-To-One)은 **여러 개의 데이터가 하나의 데이터를 참조하는 관계**를 의미한다.

즉,

> **여러(Many) → 하나(One)**

의 관계이다.

예를 들어 상품과 리뷰를 생각해보자.

```text
상품 1개
   │
   ├── 리뷰1
   ├── 리뷰2
   ├── 리뷰3
   └── 리뷰4
```

리뷰는 여러 개가 존재할 수 있지만,

각 리뷰는 하나의 상품에만 속한다.

---

# 관계를 그림으로 표현하면

```text
Product (1)

     ▲

     │

Review (N)
```

또는

```text
ProductEntity

pno = 10

상품명 = 노트북

        ▲

        │

 ┌──────┼────────┐

 │      │        │

Review1 Review2 Review3
```

---

# 데이터베이스 구조

## Product 테이블

| pno | pname |
| --: | ----- |
|   1 | 노트북   |
|   2 | 마우스   |

---

## Review 테이블

| rno | review | product_pno |
| --: | ------ | ----------: |
|   1 | 좋아요    |           1 |
|   2 | 최고예요   |           1 |
|   3 | 괜찮아요   |           2 |

여기서

```text
product_pno
```

가 **외래키(Foreign Key)** 이다.

즉,

Review는 Product를 참조한다.

---

# JPA에서는 어떻게 표현할까?

ReviewEntity

```java
@ManyToOne
@JoinColumn(name = "product_pno")
private ProductEntity product;
```

### @ManyToOne

```java
@ManyToOne
```

현재 Entity가

**여러 개(N)** 존재하고

상대 Entity는

**하나(1)** 라는 의미이다.

즉,

```text
Review 여러 개

↓

Product 하나
```

를 의미한다.

---

### @JoinColumn

```java
@JoinColumn(name = "product_pno")
```

외래키(FK) 컬럼명을 지정한다.

데이터베이스에는

```text
product_pno
```

컬럼이 생성된다.

---

# 왜 Product에 Review를 저장하지 않을까?

많은 사람들이 처음에 이렇게 생각한다.

```java
Product

List<Review>
```

만 있으면 되는 것 아닌가?

하지만 실제 데이터베이스에서는

```text
Review가 Product를 참조
```

하는 구조가 훨씬 효율적이다.

왜냐하면

리뷰는 반드시

어떤 상품에 속하는지 알아야 하기 때문이다.

그래서

```text
Review

↓

Product
```

참조가 기본이 된다.

---

# 실제 객체 구조

```text
ReviewEntity

------------------

rno

review

rating

product
        │
        ▼
ProductEntity
```

Review 객체 안에는

ProductEntity 객체가 들어 있다.

즉

```java
review.getProduct()
```

가 가능하다.

---

# 저장 과정

```text
상품 등록

↓

ProductEntity 저장

↓

상품 번호 생성

↓

리뷰 등록

↓

ReviewEntity 안에 ProductEntity 저장

↓

Review 저장
```

예제

```java
ProductEntity product =
        productRepository.findById(1L).get();

ReviewEntity review =
        ReviewEntity.builder()
                .review("좋아요")
                .rating(5)
                .product(product)
                .build();

reviewRepository.save(review);
```

---

# 조회 과정

리뷰를 조회하면

상품 정보도 함께 사용할 수 있다.

```java
ReviewEntity review =
        reviewRepository.findById(1L).get();

System.out.println(
        review.getProduct().getPname()
);
```

출력

```text
노트북
```

---

# SQL로 보면

Review 저장

```sql
INSERT INTO review

(review, rating, product_pno)

VALUES

('좋아요', 5, 1);
```

Review 조회

```sql
SELECT *

FROM review

WHERE product_pno = 1;
```

JPA는 이러한 SQL을 자동으로 생성한다.

---

# 실무에서 많이 사용하는 예

## 상품 - 리뷰

```text
Product

1

↓

Review

N
```

---

## 회원 - 주문

```text
Member

1

↓

Order

N
```

---

## 게시글 - 댓글

```text
Board

1

↓

Reply

N
```

---

## 카테고리 - 상품

```text
Category

1

↓

Product

N
```

---

# @ManyToOne의 특징

* 가장 많이 사용하는 연관관계이다.
* 외래키(FK)는 N쪽 테이블이 가진다.
* 여러 개의 객체가 하나의 객체를 참조한다.
* 대부분의 비즈니스에서 사용된다.

---

# @ManyToOne과 @OneToMany의 관계

두 어노테이션은 서로 반대 관계이다.

```text
Review
      │
@ManyToOne
      │
      ▼
Product
      ▲
@OneToMany
      │
Review List
```

즉,

Review 입장에서는

```java
@ManyToOne
private ProductEntity product;
```

Product 입장에서는

```java
@OneToMany(mappedBy = "product")
private List<ReviewEntity> reviews;
```

가 된다.

하지만 실무에서는 **양방향 연관관계가 꼭 필요한 경우가 아니라면 단방향(@ManyToOne)만 사용하는 경우가 많다.**

양방향은 코드가 복잡해지고 순환 참조 등의 문제가 발생할 수 있기 때문이다.

---

# 핵심 정리

* **다대일(Many-To-One)** 은 여러 개의 객체가 하나의 객체를 참조하는 관계이다.
* 쇼핑몰에서는 **상품 1개에 여러 개의 리뷰가 등록**될 수 있다.
* 데이터베이스에서는 **N쪽(Review)이 외래키(FK)를 가진다.**
* JPA에서는 `@ManyToOne`과 `@JoinColumn`을 이용하여 관계를 설정한다.
* 리뷰 객체는 `review.getProduct()`를 통해 자신이 속한 상품 정보를 조회할 수 있다.
* `@ManyToOne`은 게시글-댓글, 회원-주문, 상품-리뷰 등 대부분의 실무 프로젝트에서 가장 많이 사용되는 연관관계이다.
* 양방향 관계(`@OneToMany` + `@ManyToOne`)도 가능하지만, 특별한 이유가 없다면 **단방향 `@ManyToOne`부터 사용하는 것이 유지보수에 유리하다.**

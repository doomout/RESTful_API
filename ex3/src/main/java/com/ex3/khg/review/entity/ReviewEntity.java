package com.ex3.khg.review.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ex3.khg.products.entity.ProductEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity      // 이 클래스를 JPA Entity(테이블)로 사용
@Table(
    name = "tbl_review",                     // 테이블 이름 지정
    indexes = @Index(columnList = "product_pno") // product_pno 컬럼에 인덱스 생성(조회 속도 향상)
)

@Getter      // Lombok : Getter 자동 생성

// toString() 생성 시 productEntity 제외
// 연관관계(Entity)를 출력하면 무한참조가 발생할 수 있기 때문
@ToString(exclude = "productEntity")

@AllArgsConstructor    // 모든 필드를 매개변수로 하는 생성자 생성
@NoArgsConstructor     // 기본 생성자 생성
@Builder               // Builder 패턴 지원

// 생성일, 수정일을 자동으로 관리하도록 Auditing 기능 활성화
@EntityListeners(value = { AuditingEntityListener.class })

public class ReviewEntity {

    @Id     // 기본키(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 PK를 자동 증가
    private Long rno;

    // 리뷰 내용
    private String reviewText;

    // 작성자
    private String reviewer;

    // 평점
    private int score;

    // 여러 개의 Review가 하나의 Product를 참조
    @ManyToOne(fetch = FetchType.LAZY)

    // 외래키(FK) 컬럼명 지정
    @JoinColumn(name="product_pno")
    private ProductEntity productEntity;

    // Entity가 처음 저장될 때 현재 시간이 자동 저장
    @CreatedDate
    private LocalDateTime reviewDate;

    // Entity가 수정될 때마다 현재 시간이 자동 저장
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}

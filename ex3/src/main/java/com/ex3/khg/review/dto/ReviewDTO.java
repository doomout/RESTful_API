package com.ex3.khg.review.dto;

import java.time.LocalDateTime;

import com.ex3.khg.review.entity.ReviewEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReviewDTO {
    private Long rno;
    private String reviewText;
    private String reviewer;
    private int score;
    private Long pno;
    private LocalDateTime reviewDate;
    private LocalDateTime modifiedDate;

    public ReviewDTO(ReviewEntity reviewEntity) {
        this.rno = reviewEntity.getRno();
        this.reviewText = reviewEntity.getReviewText();
        this.reviewer = reviewEntity.getReviewer();
        this.score = reviewEntity.getScore();
        this.pno = reviewEntity.getProductEntity().getPno();
        this.reviewDate = reviewEntity.getReviewDate();
        this.modifiedDate = reviewEntity.getModifiedDate();
    }
}

package com.todo.khg.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO { // 페이지 요청 정보를 담는 DTO 클래스
    @Builder.Default
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.") // 페이지 번호는 1 이상이어야 한다는 유효성 검사 추가
    private int page = 1; // 현재 페이지 번호, 기본값은 1

    @Builder.Default
    @Max(value = 100, message = "페이지 크기는 100 이하이어야 합니다.") // 페이지 크기는 100 이하이어야 한다는 유효성 검사 추가
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.") // 페이지 크기는 1 이상이어야 한다는 유효성 검사 추가
    private int size = 10; // 페이지당 데이터 수, 기본값은 10

    public Pageable getPageable(Sort sort) {
        int pageNum = page < 0 ? 1: page - 1;
        int sizeNum = size <= 10 ? 10 : size;

        return PageRequest.of(pageNum, sizeNum, sort);
    }
}

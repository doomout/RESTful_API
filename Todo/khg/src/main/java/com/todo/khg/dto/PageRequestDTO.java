package com.todo.khg.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    private int page = 1; // 현재 페이지 번호, 기본값은 1

    @Builder.Default
    private int size = 10; // 페이지당 데이터 수, 기본값은 10

    public Pageable getPageable(Sort sort) {
        int pageNum = page < 0 ? 1: page - 1;
        int sizeNum = size <= 10 ? 10 : size;

        return PageRequest.of(pageNum, sizeNum, sort);
    }
}

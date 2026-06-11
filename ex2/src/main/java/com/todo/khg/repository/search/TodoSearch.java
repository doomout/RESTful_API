package com.todo.khg.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.todo.khg.dto.TodoDTO;
import com.todo.khg.entity.TodoEntity;

public interface TodoSearch {
    Page<TodoEntity> search1(Pageable pageable);

    // DTO로 변환하여 반환하는 메서드 추가
    Page<TodoDTO> searchDTO(Pageable pageable);
}

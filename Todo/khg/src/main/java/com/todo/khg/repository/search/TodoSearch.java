package com.todo.khg.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.todo.khg.entity.TodoEntity;

public interface TodoSearch {
    Page<TodoEntity> search1(Pageable pageable);
}

package com.todo.khg.TodoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import com.todo.khg.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    @Query("select t from TodoEntity t")
    Page<TodoEntity> findAll(Pageable pageable);
}

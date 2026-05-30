package com.todo.khg.TodoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.todo.khg.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    @Query("select t from TodoEntity t ")
    Page<TodoEntity> listAll(Pageable pageable);
}

package com.todo.khg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.todo.khg.entity.TodoEntity;
import com.todo.khg.repository.search.TodoSearch;

public interface TodoRepository extends JpaRepository<TodoEntity, Long>, TodoSearch {
    @Query("select t from TodoEntity t ")
    Page<TodoEntity> listAll(Pageable pageable);
}

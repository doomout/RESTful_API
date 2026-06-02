package com.todo.khg.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todo.khg.dto.TodoDTO;
import com.todo.khg.entity.TodoEntity;
import com.todo.khg.repository.search.TodoSearch;

public interface TodoRepository extends JpaRepository<TodoEntity, Long>, TodoSearch {
    @Query("select t from TodoEntity t ")
    Page<TodoEntity> listAll(Pageable pageable);

    @Query("select t from TodoEntity t where t.mno = :mno") // DTO로 변환하여 반환
    Optional<TodoDTO> getDTO(@Param("mno") Long mno); // DTO로 변환하여 반환
}

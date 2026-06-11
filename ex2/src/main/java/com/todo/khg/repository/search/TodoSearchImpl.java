package com.todo.khg.repository.search;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.todo.khg.dto.TodoDTO;
import com.todo.khg.entity.TodoEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {
    public TodoSearchImpl() {
        super(TodoEntity.class);
    }

    @Override
    public Page<TodoEntity> search1(Pageable pageable) {
        log.info("search1............");

        PathBuilder<TodoEntity> todoEntity = new PathBuilder<>(TodoEntity.class, "todoEntity");
        JPQLQuery<TodoEntity> query = from(todoEntity);

        query.where(todoEntity.getNumber("mno", Long.class).gt(0L));
        getQuerydsl().applyPagination(pageable, query);

        List<TodoEntity> entityList = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(entityList, pageable, count);
    }

    @Override
    public Page<TodoDTO> searchDTO(Pageable pageable) {
        PathBuilder<TodoEntity> todoEntity = new PathBuilder<>(TodoEntity.class, "todoEntity");
        JPQLQuery<TodoEntity> query = from(todoEntity);

        query.where(todoEntity.getNumber("mno", Long.class).gt(0L));
        getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<TodoDTO> dtoQuery = query.select(Projections.bean(
                TodoDTO.class,
                todoEntity.getNumber("mno", Long.class),
                todoEntity.getString("title"),
                todoEntity.getString("writer"),
                todoEntity.getDate("dueDate", LocalDate.class)));

        List<TodoDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }
}

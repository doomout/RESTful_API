package com.todo.khg.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.jpa.JPQLQuery;
import com.todo.khg.entity.QTodoEntity;
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
        QTodoEntity todoEntity = QTodoEntity.todoEntity;
        JPQLQuery<TodoEntity> query = from(todoEntity);
        query.where(todoEntity.mno.gt(0L));
        
        // 결과 반환
        return null;
    }
}

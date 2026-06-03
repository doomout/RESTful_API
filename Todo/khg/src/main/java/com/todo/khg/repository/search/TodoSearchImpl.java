package com.todo.khg.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.todo.khg.dto.TodoDTO;
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
        // Querydsl을 사용하여 검색 조건을 구성
        QTodoEntity todoEntity = QTodoEntity.todoEntity;
        // 검색 조건 예시: 모든 TodoEntity를 조회하는 조건
        JPQLQuery<TodoEntity> query = from(todoEntity);
        // 검색 조건을 추가할 수 있습니다. 예를 들어, 특정 제목을 포함하는 TodoEntity를 검색하려면 다음과 같이 조건을 추가할 수 있습니다.
        query.where(todoEntity.mno.gt(0L));

        // 페이징 처리
        getQuerydsl().applyPagination(pageable, query);
        // 검색 결과를 리스트로 가져오고, 전체 개수를 계산
        java.util.List<TodoEntity> entityList = query.fetch();
        // 전체 개수 계산
        long count = query.fetchCount();
        
        // 결과 반환
        return new PageImpl<>(entityList, pageable, count);
    }

    // Projections.bean()을 사용하여 DTO로 변환하여 반환하는 메서드 추가
    @Override
    public Page<TodoDTO> searchDTO(Pageable pageable) {
        // Querydsl을 사용하여 검색 조건을 구성
        QTodoEntity todoEntity = QTodoEntity.todoEntity;

        // 검색 조건 예시: 모든 TodoEntity를 조회하는 조건
        JPQLQuery<TodoEntity> query = from(todoEntity);

        // 검색 조건 예시: 모든 TodoEntity를 조회하는 조건
        query.where(todoEntity.mno.gt(0L));

        // 페이징 처리
        getQuerydsl().applyPagination(pageable, query);

        // bean 프로젝션을 사용하여 검색 결과를 DTO로 변환하여 리스트로 가져오고, 전체 개수를 계산
        JPQLQuery<TodoDTO> dtoQuery = query.select(Projections.bean(
            TodoDTO.class,
            todoEntity.mno,
            todoEntity.title,   
            todoEntity.writer,
            todoEntity.dueDate));
        
        // DTO 리스트와 전체 개수 계산
        java.util.List<TodoDTO> dtoList = dtoQuery.fetch();

        // 전체 개수 계산
        long count = dtoQuery.fetchCount();
        
        // 결과 반환
        return new PageImpl<>(dtoList, pageable, count);
    }
/* Querydsl을 사용한 검색 조건과 DTO 변환 예시
    @Override
    public Page<TodoDTO> searchDTO(Pageable pageable) {
        // Querydsl을 사용하여 검색 조건을 구성
        QTodoEntity todoEntity = QTodoEntity.todoEntity;

        // 검색 조건 예시: 모든 TodoEntity를 조회하는 조건
        JPQLQuery<TodoEntity> query = from(todoEntity);

        // 검색 조건 예시: 모든 TodoEntity를 조회하는 조건
        query.where(todoEntity.mno.gt(0L));

        // 페이징 처리
        getQuerydsl().applyPagination(pageable, query);

        // 검색 결과를 DTO로 변환하여 리스트로 가져오고, 전체 개수를 계산
        JPQLQuery<TodoDTO> dtoQuery = query.select(Projections.constructor(TodoDTO.class, todoEntity));
        
        // DTO 리스트와 전체 개수 계산
        java.util.List<TodoDTO> dtoList = dtoQuery.fetch();

        // 전체 개수 계산
        long count = dtoQuery.fetchCount();
        
        // 결과 반환
        return new PageImpl<>(dtoList, pageable, count);
    }
*/
}

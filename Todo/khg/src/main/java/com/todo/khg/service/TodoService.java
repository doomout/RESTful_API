package com.todo.khg.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.khg.dto.TodoDTO;
import com.todo.khg.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

     public TodoDTO register(TodoDTO todoDTO) {
        // DTO 를 엔티티 객체로 변환
        // TodoRepository 를 이용하여 저장
        // DTO에 저장된 번호를 지정해서 반환
        
        return null;
     }
}

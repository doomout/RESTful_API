package com.todo.khg.service;


import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.khg.dto.TodoDTO;
import com.todo.khg.entity.TodoEntity;
import com.todo.khg.exception.EntityNotFoundException;
import com.todo.khg.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    // TodoDTO 객체를 이용하여 TodoEntity 객체로 변환한 후 저장하는 메서드
     public TodoDTO register(TodoDTO todoDTO) {
        // DTO 를 엔티티 객체로 변환
        TodoEntity todoEntity = todoDTO.toEntity();
        
        // TodoRepository 를 이용하여 저장
        todoRepository.save(todoEntity);

        // DTO에 저장된 번호를 지정해서 반환
        return new TodoDTO(todoEntity);
     }

     // 번호를 이용하여 TodoDTO 객체를 반환하는 메서드
     public TodoDTO read(Long mno) {
         Optional<TodoDTO> result = todoRepository.getDTO(mno);
         TodoDTO todoDTO = result.orElseThrow(
            () -> new EntityNotFoundException(mno + "번 번호의 Todo가 존재하지 않습니다.") // 예외  처리 추가
         );

         return todoDTO;
     }
}

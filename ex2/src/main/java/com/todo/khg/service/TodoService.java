package com.todo.khg.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.khg.dto.PageRequestDTO;
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

   // 번호를 이용하여 TodoEntity 객체를 삭제하는 메서드
   public void remove(Long mno) {
      Optional<TodoEntity> result = todoRepository.findById(mno);
      
      TodoEntity todoEntity = result.orElseThrow(
         () -> new EntityNotFoundException(mno + "번 번호의 Todo가 존재하지 않습니다.") // 예외  처리 추가
      );

      todoRepository.delete(todoEntity);
   }

   // 번호를 이용하여 TodoEntity 객체를 수정하는 메서드
   public TodoDTO modify(TodoDTO todoDTO) {
      Optional<TodoEntity> result = todoRepository.findById(todoDTO.getMno());
      
      TodoEntity todoEntity = result.orElseThrow(
         () -> new EntityNotFoundException(todoDTO.getMno() + "번 번호의 Todo가 존재하지 않습니다.") // 예외  처리 추가
      );

      // 수정할 내용 반영
      todoEntity.changeTitle(todoDTO.getTitle());
      todoEntity.changeWriter(todoDTO.getWriter());
      todoEntity.changeDueDate(todoDTO.getDueDate());

      // 변경된 엔티티 객체 저장
      return new TodoDTO(todoEntity);
   }

   // PageRequestDTO 객체를 이용하여 TodoDTO 객체의 페이지 목록을 반환하는 메서드
   public Page<TodoDTO> getList(PageRequestDTO pageRequestDTO) {
      Sort sort = Sort.by("mno").descending(); // 번호(mno)를 기준으로 내림차순 정렬
      Pageable pageable = pageRequestDTO.getPageable(sort); // PageRequestDTO 객체에서 Pageable 객체를 생성
      
      return todoRepository.searchDTO(pageable); // TodoRepository의 searchDTO 메서드를 호출하여 페이지 목록을 반환
   }
}

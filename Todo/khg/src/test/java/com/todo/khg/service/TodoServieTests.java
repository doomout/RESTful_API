package com.todo.khg.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.todo.khg.dto.PageRequestDTO;
import com.todo.khg.dto.TodoDTO;

@SpringBootTest
public class TodoServieTests {
    @Autowired
    private TodoService todoService;

    @Test
    public void testRegister() { // todoService의 register 메서드 테스트
        // TodoDTO 객체 생성
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("Test Title");
        todoDTO.setWriter("Test Writer");
        todoDTO.setDueDate(LocalDate.of(2026, 6, 05));

        // TodoService의 register 메서드 호출
        TodoDTO resultDTO = todoService.register(todoDTO);

        // 결과 출력
        System.out.println("Registered Todo: " + resultDTO);
    }

    @Test
    public void testRead() { // todoService의 read 메서드 테스트
        Long mno = 20L; 

        TodoDTO todoDTO = todoService.read(mno);
        System.out.println("Read Todo: " + todoDTO);
    }

    @Test
    public void testRemove() { // todoService의 remove 메서드 테스트
        Long mno = 50L; 

        todoService.remove(mno);
        System.out.println("Removed Todo with mno: " + mno);
    }

    @Test
    public void testModify() { // todoService의 modify 메서드 테스트
        // TodoDTO 객체 생성
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setMno(20L); 
        todoDTO.setTitle("수정된 제목");
        todoDTO.setWriter("수정된 작성자");
        todoDTO.setDueDate(LocalDate.now());

        // TodoService의 modify 메서드 호출
        todoService.modify(todoDTO);

        // 결과 출력
        System.out.println("수정된 Todo: " + todoDTO);
    }

    @Test
    public void testlist() {
        // page 1, size 10
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        
        // TodoService의 getList 메서드 호출
        Page<TodoDTO> result = todoService.getList(pageRequestDTO);

        System.out.println("이전 페이지: " + result.previousPageable());
        System.out.println("다음 페이지: " + result.nextPageable());
        System.out.println("총 페이지 수: " + result.getTotalElements());

        result.getContent().forEach(todoDTO -> System.out.println(todoDTO));
    }
}

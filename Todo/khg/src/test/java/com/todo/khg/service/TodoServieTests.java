package com.todo.khg.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    
}

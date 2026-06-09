package com.todo.khg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.khg.dto.TodoDTO;
import com.todo.khg.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/todos")
@Log4j2
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("")
    public ResponseEntity<TodoDTO> register(@RequestBody @Validated TodoDTO todoDTO) { //  @Validated 어노테이션을 사용하여 유효성 검사를 수행
        log.info("register...............");
        log.info(todoDTO);

        todoDTO.setMno(null); // 번호는 자동으로 생성되도록 null로 설정

        //return ResponseEntity.ok(todoService.register(todoDTO)); // 등록된 TodoDTO 객체를 반환
        return null;
    }
}

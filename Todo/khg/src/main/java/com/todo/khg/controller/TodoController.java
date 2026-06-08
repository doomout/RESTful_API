package com.todo.khg.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.khg.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/todos")
@Log4j2
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
}

package com.todo.khg.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.khg.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
}

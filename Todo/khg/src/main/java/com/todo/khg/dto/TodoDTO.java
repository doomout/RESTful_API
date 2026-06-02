package com.todo.khg.dto;

import java.time.LocalDate;

import com.todo.khg.entity.TodoEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoDTO {
    private Long mno;
    private String title;
    private String writer;
    private LocalDate dueDate;

    public TodoDTO(TodoEntity entity) {
        this.mno = entity.getMno();
        this.title = entity.getTitle();
        this.writer = entity.getWriter();
        this.dueDate = entity.getDueDate();
    }
}

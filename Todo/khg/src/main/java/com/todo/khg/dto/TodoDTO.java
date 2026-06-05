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

    // 엔티티 객체 -> DTO 객체로 변환하는 생성자
    public TodoDTO(TodoEntity todoEntity) {
        this.mno = todoEntity.getMno();
        this.title = todoEntity.getTitle();
        this.writer = todoEntity.getWriter();
        this.dueDate = todoEntity.getDueDate();
    }

    // DTO 객체 -> 엔티티 객체로 변환하는 메서드
    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .mno(mno)
                .title(title)
                .writer(writer)
                .dueDate(dueDate)
                .build();
    }
}

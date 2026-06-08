package com.todo.khg.dto;

import java.time.LocalDate;

import com.todo.khg.entity.TodoEntity;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoDTO {
    private Long mno;

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;

    @NotEmpty(message = "작성자는 필수입니다.")
    private String writer;

    @FutureOrPresent(message = "마감일은 오늘 또는 미래여야 합니다.")
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

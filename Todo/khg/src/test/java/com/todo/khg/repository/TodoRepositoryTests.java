package com.todo.khg.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.todo.khg.TodoRepository.TodoRepository;
import com.todo.khg.entity.TodoEntity;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TodoRepositoryTests {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testInsert() { // 1개 데이터 삽입 테스트
        TodoEntity todoEntity = TodoEntity.builder()
                .title("부트 끝내기")
                .writer("user00")
                .dueDate(LocalDate.of(2026, 5, 26))
                .build();
        
        todoRepository.save(todoEntity);
        System.out.println("TodoEntity saved: " + todoEntity.getMno());
    }

    @Test
    public void testInsertDummies() { // 100개 데이터 삽입 테스트
        for (int i = 0; i < 100; i++) {
            TodoEntity todoEntity = TodoEntity.builder()
                    .title("Test Todo..." + i)
                    .writer("tester" + i)
                    .dueDate(LocalDate.of(2026, 5, 26))
                    .build();
            todoRepository.save(todoEntity);
            System.out.println("New TodoEntity MNO: " + todoEntity.getMno());
        }
    }

    @Test
    public void testRead() {
        Long mno = 58L; // 읽어올 데이터의 MNO
        
        Optional<TodoEntity> result = todoRepository.findById(mno);
        
        result.ifPresent(todoEntity -> {
            System.out.println(todoEntity);
        });
    }
}

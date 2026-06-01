package com.todo.khg.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.todo.khg.entity.TodoEntity;
import com.todo.khg.repository.TodoRepository;

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

    @Test
    @Transactional // 트랜잭션을 적용하여 영속성 컨텍스트 유지
    @Commit // 테스트 완료 후 트랜잭션 커밋하여 변경 사항 데이터베이스에 반영
    public void testUpdateDirtyCheck() {
        Long mno = 58L; // 수정할 데이터의 MNO
        
        // 동일 트랜젝션 내에서 처리되고 있는 영속 상태의 엔티티 객체
        Optional<TodoEntity> result = todoRepository.findById(mno);

        TodoEntity todoEntity = result.get();

        System.out.println("OLD" + todoEntity);
        
        // 엔티티 객체의 상태 변경
        todoEntity.changeTitle("수정된 제목..." + Math.random());
        todoEntity.changeWriter("수정된 작성자..." + Math.random());

        System.out.println("수정된 날짜: " + todoEntity);
    }

    @Test
    @Transactional // 트랜잭션을 적용하여 영속성 컨텍스트 유지
    @Commit // 테스트 완료 후 트랜잭션 커밋하여 변경 사항 데이터베이스에 반영
    public void testDelete() {
        Long mno = 44L; // 삭제할 데이터의 MNO

        Optional<TodoEntity> result = todoRepository.findById(mno);
        
        result.ifPresent(todoEntity -> {
            todoRepository.delete(todoEntity);
            System.out.println("삭제한 번호: " + mno);
        });
    }

    @Test
    @Transactional // 트랜잭션을 적용하여 영속성 컨텍스트 유지
    @Commit // 테스트 완료 후 트랜잭션 커밋하여 변경 사항 데이터베이스에 반영
    public void testDeleteById() {
        Long mno = 30L; // 삭제할 데이터의 MNO
        
        todoRepository.deleteById(mno);
        System.out.println("삭제한 번호: " + mno);
    }

    @Test
    public void testPaging() { // 페이징 처리 테스트
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<TodoEntity> result = todoRepository.findAll(pageable); // 페이징 처리된 결과를 Page 객체로 반환

        System.out.println(result.getTotalPages()); // 전체 페이지 수
        System.out.println(result.getTotalElements()); // 전체 데이터 수

        java.util.List<TodoEntity> todoList = result.getContent(); // 현재 페이지에 해당하는 데이터 리스트

        todoList.forEach(todoEntity -> { // 람다식으로 각 엔티티 객체를 출력
            System.out.println(todoEntity);
        });
    }

    @Test
    public void testListAll() { // listAll 메서드 테스트
        
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<TodoEntity> result = todoRepository.listAll(pageable); // listAll 메서드를 호출하여 페이징 처리된 결과를 Page 객체로 반환

        System.out.println(result.getContent());  // 현재 페이지에 해당하는 데이터 리스트를 출력
    }

    @Test
    public void testSearch1() { // search1 메서드 테스트
        
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<TodoEntity> result = todoRepository.search1(pageable); // search1 메서드를 호출하여 페이징 처리된 결과를 Page 객체로 반환

        System.out.println(result.getContent());  // 현재 페이지에 해당하는 데이터 리스트를 출력
    }
}

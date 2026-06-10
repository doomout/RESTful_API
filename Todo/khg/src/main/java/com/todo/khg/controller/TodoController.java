package com.todo.khg.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.khg.dto.PageRequestDTO;
import com.todo.khg.dto.TodoDTO;
import com.todo.khg.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("/{mno}")
    public ResponseEntity<TodoDTO> read(@PathVariable("mno") Long mno) { // @PathVariable 어노테이션을 사용하여 URL 경로에서 mno 값을 추출
        log.info("read...............");
        log.info(mno);


        return ResponseEntity.ok(todoService.read(mno)); // 해당 번호의 TodoDTO 객체를 반환
    }
    
    @PutMapping("/{mno}") 
    public ResponseEntity<TodoDTO> modify(@PathVariable("mno") Long mno, @RequestBody TodoDTO todoDTO) { 
        log.info("modify...............");
        log.info(mno);
        log.info(todoDTO);

        // DTO 에 번호를 저장한다.
        todoDTO.setMno(mno);

        TodoDTO modifiedTodoDTO = todoService.modify(todoDTO);

        return ResponseEntity.ok(modifiedTodoDTO); // 수정된 TodoDTO 객체를 반환
    }

    @DeleteMapping("/{mno}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable("mno") Long mno) { 
        log.info("remove...............");
        log.info(mno);

        todoService.remove(mno);

        // void 타입이기 때문에 다른 결과들처럼 JSON 결과를 만들어서 반환한다.
        Map<String, String> result = Map.of("result", "success");

        return ResponseEntity.ok(result); // 삭제 성공 시 204 No Content 응답 반환
    }

    @GetMapping("/list")
    public ResponseEntity<Page<TodoDTO>> list(@Validated PageRequestDTO pageRequestDTO) { // @Validated 어노테이션을 사용하여 유효성 검사를 수행
        log.info("list...............");
        log.info(pageRequestDTO);
        // 페이지 요청 정보는 PageRequestDTO 객체로 받는다.
        // 페이지 요청 정보는 URL 쿼리 파라미터로 전달된다. 예: /api/v1/todos/list?page=1&size=10
        // @RequestParam 어노테이션을 사용하여 URL 쿼리 파라미터에서 page와 size 값을 추출한다.
        // @Validated 어노테이션을 사용하여 유효성 검사를 수행한다.
        // PageRequestDTO 객체는 page와 size 필드를 가지고 있으며, 기본값은 각각 1과 10이다.
        // 페이지 요청 정보는 Pageable 객체로 변환되어 서비스 계층에 전달된다.

        return ResponseEntity.ok(todoService.getList(pageRequestDTO)); // 페이지 목록과 페이지 정보를 포함하는 JSON 결과를 반환
    }
}

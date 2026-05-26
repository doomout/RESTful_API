package com.todo.khg.TodoRepository;

import org.springframework.data.jpa.repository.*;
import com.todo.khg.entity.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    
}

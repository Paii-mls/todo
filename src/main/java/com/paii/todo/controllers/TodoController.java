package com.paii.todo.controllers;

import com.paii.todo.entities.Todo;
import com.paii.todo.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    protected static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;


    @GetMapping()
    public List<Todo> getTodoList() {
        logger.info("Find all Todos");
        return todoService.getTodoList();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable Long id) {
        logger.info("Find Todo by id: {}", id);
        return todoService.getTodo(id);
    }

    @PostMapping()
    public ResponseEntity<?> createTodo(@Valid @RequestBody Todo body) {
        logger.info("Create Todo");
        Todo todo = todoService.createTodo(body);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Todo updateToDo(@PathVariable Long id, @Valid @RequestBody Todo body) {
        logger.info("Update Todo id: {}", id);
        return todoService.updateTodo(id, body);
    }

    @DeleteMapping("/{id}")
    public Todo deleteTodo(@PathVariable Long id) {
        logger.info("Delete Todo id: {}", id);
        return todoService.deleteTodo(id);
    }
}

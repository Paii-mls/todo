package com.paii.todo.controllers;

import com.paii.todo.entities.TodoItem;
import com.paii.todo.services.TodoItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodoItemController {

    protected static final Logger logger = LoggerFactory.getLogger(TodoItemController.class);

    @Autowired
    TodoItemService todoItemService;

    @PostMapping("/{todoId}/items")
    public ResponseEntity<?> createTodoItem(@PathVariable Long todoId, @Valid @RequestBody TodoItem body) {
        logger.info("Create Todo item of Todo id: {}", todoId);
        TodoItem todoItem = todoItemService.createTodoItem(todoId, body);

        return new ResponseEntity<>(todoItem, HttpStatus.CREATED);
    }

    @PutMapping("/items/{id}")
    public TodoItem updateTodoItem(@PathVariable Long id, @Valid @RequestBody TodoItem body) {
        logger.info("Update Todo item id: {}", id);
        return todoItemService.updateTodoItem(id, body);
    }

    @DeleteMapping("/items/{id}")
    public TodoItem deleteTodoItem(@PathVariable Long id) {
        logger.info("Delete Todo item id: {}", id);
        return todoItemService.deleteTodoItem(id);
    }
}

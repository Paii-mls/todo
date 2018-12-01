package com.paii.todo.services;

import com.paii.todo.entities.Todo;
import com.paii.todo.exceptions.NotFoundException;
import com.paii.todo.respositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo getTodo(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Todo id " + id));
    }

    public List<Todo> getTodoList() {
        return (List<Todo>)todoRepository.findAll();
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo todo) {
        Todo result = todoRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found Todo id " + id));

        result.setTitle(todo.getTitle());

        return todoRepository.save(result);
    }

    public Todo deleteTodo(Long id) {
        Todo result = todoRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found Todo id " + id));

        todoRepository.delete(result);

        return result;
    }
}

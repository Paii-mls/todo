package com.paii.todo.services;

import com.paii.todo.entities.Todo;
import com.paii.todo.entities.TodoItem;
import com.paii.todo.exceptions.NotFoundException;
import com.paii.todo.respositories.TodoItemRepository;
import com.paii.todo.respositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoItemService {

    private TodoRepository todoRepository;
    private TodoItemRepository todoItemRepository;

    @Autowired
    public TodoItemService(TodoRepository todoRepository, TodoItemRepository todoItemRepository) {
        this.todoRepository = todoRepository;
        this.todoItemRepository = todoItemRepository;
    }

    public TodoItem createTodoItem(Long todoId, TodoItem todoItem) {
      Todo todo = todoRepository.findById(todoId)
                    .orElseThrow(() -> new NotFoundException("Not found Todo id " + todoId));

      todoItem.setTodo(todo);

      return todoItemRepository.save(todoItem);
    }

    public TodoItem updateTodoItem(Long id, TodoItem todoItem) {
       TodoItem result =  todoItemRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Not found TodoItem id" + id));

       result.setContent(todoItem.getContent());

       return todoItemRepository.save(result);
    }

    public TodoItem deleteTodoItem(Long id) {
        TodoItem result = todoItemRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Not found TodoItem id" + id));

        todoItemRepository.delete(result);
        return result;
    }

}

package com.paii.todo.respositories;

import com.paii.todo.entities.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> { }

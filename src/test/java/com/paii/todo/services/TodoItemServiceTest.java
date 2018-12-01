package com.paii.todo.services;

import com.paii.todo.entities.Todo;
import com.paii.todo.entities.TodoItem;
import com.paii.todo.exceptions.NotFoundException;
import com.paii.todo.respositories.TodoItemRepository;
import com.paii.todo.respositories.TodoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoItemServiceTest {

    private TodoItemService todoItemService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoItemRepository todoItemRepository;

    private Todo todo;
    private TodoItem todoItem;

    @Before
    public void setUp() {
        todoItemService = new TodoItemService(todoRepository, todoItemRepository);
        todo = new Todo(1, "Todo Sample");
        todoItem = new TodoItem(1, "Task 1", todo);
    }

    @Test
    public void testCreateTodoItem() {
        // Arrange
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo));
        when(todoItemRepository.save(any(TodoItem.class))).thenReturn(todoItem);

        // Act
        TodoItem result =  todoItemService.createTodoItem(1L, todoItem);

        // Assert
        assertEquals("Task 1", result.getContent());
        verify(todoItemRepository).save(any(TodoItem.class));
    }

    @Test
    public void testUpdateTodoItem() {
        // Arrange
        when(todoItemRepository.findById(anyLong())).thenReturn(Optional.of(todoItem));
        when(todoItemRepository.save(any(TodoItem.class))).thenReturn(todoItem);

        // Act
        todoItem.setContent("Task 1 edited");
        TodoItem result = todoItemService.updateTodoItem(1L, todoItem);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Task 1 edited", result.getContent());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateTodoItemThrowsExceptions() {
        // Arrange
        TodoItem todoItem2 = new TodoItem(2, "Task edited", null);

        // Act
        todoItemService.updateTodoItem(2L, todoItem2);
    }

    @Test
    public void testDeleteTodoItem() {
        // Arrange
        when(todoItemRepository.findById(anyLong())).thenReturn(Optional.of(todoItem));
        doNothing().when(todoItemRepository).delete(any(TodoItem.class));

        // Act
        todoItemService.deleteTodoItem(1L);

        // Assert
        verify(todoItemRepository).delete(any(TodoItem.class));
    }

}

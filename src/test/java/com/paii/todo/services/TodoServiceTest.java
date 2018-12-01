package com.paii.todo.services;

import com.paii.todo.entities.Todo;
import com.paii.todo.exceptions.NotFoundException;
import com.paii.todo.respositories.TodoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Before
    public void setUp() {
        todoService = new TodoService(todoRepository);
    }

    @Test
    public void testCreateTodo() {
        // Arrange
        Todo todo = new Todo(1, "First Todo");
        when(todoRepository.save(any(Todo.class))).thenAnswer(returnsFirstArg());

        // Act
        Todo result = todoService.createTodo(todo);

        // Assert
        assertEquals("First Todo", result.getTitle());
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    public void testGetTodo() {
        // Arrange
        Todo todo = new Todo(1, "Todo Sample 1");
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo));

        // Act
        Todo result = todoService.getTodo(1L);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("Todo Sample 1", result.getTitle());
    }

    @Test(expected = NotFoundException.class)
    public void testGetTodoAndThrowsException() {
        // Act
        todoService.getTodo(2L);
    }

    @Test
    public void testGetTodoList() {
        // Arrange
        List<Todo> todoList = new ArrayList<Todo>();
        todoList.add(new Todo(1,"Todo Sample 1"));
        todoList.add(new Todo(2,"Todo Sample 2"));
        todoList.add(new Todo(3,"Todo Sample 3"));
        when(todoRepository.findAll()).thenReturn(todoList);

        // Act
        List<Todo> result = todoService.getTodoList();

        // Assert
        assertEquals(3, result.size());
    }

    @Test
    public void testUpdateTodo() {
        // Arrange
        Todo todo = new Todo(1, "First todo ldssd");
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Act
        todo.setTitle("First todo list");
        Todo result = todoService.updateTodo(1L, todo);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("First todo list", result.getTitle());
        verify(todoRepository, times(1)).findById(anyLong());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    public void testDeleteTodo() {
        // Arrange
        Todo todo = new Todo(6, "Todo Sample 6");
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo));
        doNothing().when(todoRepository).delete(any(Todo.class));

        // Act
        todoService.deleteTodo(6L);
        List<Todo> result = todoService.getTodoList();

        // Assert
        assertEquals(0, result.size());
        verify(todoRepository, times(1)).delete(any(Todo.class));
    }
}

package com.paii.todo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paii.todo.entities.Todo;
import com.paii.todo.exceptions.NotFoundException;
import com.paii.todo.services.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Todo> todoList;
    private Todo todo1;
    private Todo todo2;

    @Before
    public void setUp() {
        todo1 = new Todo(1, "Todo Sample 1");
        todo2 = new Todo(2, "Todo Sample 2");
        todoList = new ArrayList<Todo>();
        todoList.add(todo1);
        todoList.add(todo2);
    }

    @Test
    public void testCreateTodo() throws Exception {
        // Arrange
        when(todoService.createTodo(any(Todo.class))).thenReturn(todo2);

        // Act
        ResultActions result = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todo1)));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Todo Sample 2")));
        verify(todoService).createTodo(any(Todo.class));
    }

    @Test
    public void testGetTodo() throws Exception {
        // Arrange
        when(todoService.getTodo(anyLong())).thenReturn(todo1);

        // Act
        ResultActions result = mockMvc.perform(get("/todos/1")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Todo Sample 1")));
    }

    @Test
    public void testGetTodoInvalid() throws Exception {
        // Arrange
        when(todoService.getTodo(anyLong())).thenThrow(NotFoundException.class);

        // Act
        ResultActions result = mockMvc.perform(get("/todos/5")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // Assert
        result.andExpect(status().isNotFound());
    }

    @Test
    public void testGetTodoList() throws Exception {
        // Arrange
        when(todoService.getTodoList()).thenReturn(todoList);

        // Act
        ResultActions result = mockMvc.perform(get("/todos")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        // Arrange
        Todo todo = new Todo(2, "Todo Sample 2 edited");
        when(todoService.updateTodo(anyLong(), any(Todo.class))).thenReturn(todo);

        // Act
        ResultActions result = mockMvc.perform(put("/todos/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todo))
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("Todo Sample 2 edited")));
        verify(todoService, times(1)).updateTodo(anyLong(), any(Todo.class));
    }

    @Test
    public void testUpdateTodoInvalid() throws Exception {
        // Arrange
        when(todoService.updateTodo(anyLong(), any(Todo.class))).thenThrow(NotFoundException.class);
        Todo todo = new Todo(4, "sample");

        // Act
        ResultActions result = mockMvc.perform(put("/todos/4")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todo))
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // Assert
        result.andExpect(status().isNotFound());
    }
    @Test
    public void testDeleteTodo() throws Exception {
        // Arrange
        when(todoService.deleteTodo(anyLong())).thenReturn(todo2);

        // Act
        ResultActions result = mockMvc.perform(delete("/todos/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todo2)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("Todo Sample 2")));
        verify(todoService, times(1)).deleteTodo(anyLong());
    }

    @Test
    public void testDeleteTodoInvalid() throws Exception {
        // Arrange
        when(todoService.deleteTodo(anyLong())).thenThrow(NotFoundException.class);
        Todo todo = new Todo(3, "Task Sample 3");

        // Act
        ResultActions result = mockMvc.perform(delete("/todos/10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todo))
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // Assert
        result.andExpect(status().isNotFound());
    }
}

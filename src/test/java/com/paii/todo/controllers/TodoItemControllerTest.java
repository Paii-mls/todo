package com.paii.todo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paii.todo.entities.Todo;
import com.paii.todo.entities.TodoItem;
import com.paii.todo.exceptions.NotFoundException;
import com.paii.todo.services.TodoItemService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoItemController.class)
public class TodoItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoItemService todoItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private Todo todo;
    private TodoItem todoItem;

    @Before
    public void setUp() {
        todo = new Todo(1, "Todo Sample");
        todoItem = new TodoItem(1, "Task 1", todo);
    }

    @Test
    public void createTodoItem() throws Exception {
        // Arrange
        when(todoItemService.createTodoItem(anyLong(), any(TodoItem.class))).thenReturn(todoItem);

        // Act
        ResultActions result = mockMvc.perform(post("/todos/1/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todoItem)));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("Task 1")));
    }

    @Test
    public void updateTodoItem() throws Exception {
        // Arrange
        TodoItem todoItem = new TodoItem(1, "Task 1 edited", todo);
        when(todoItemService.updateTodoItem(anyLong(), any(TodoItem.class))).thenReturn(todoItem);

        // Act
        ResultActions result = mockMvc.perform(put("/todos/items/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todoItem)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("Task 1 edited")));
    }

    @Test
    public void updateTodoItemInvalid() throws Exception{
        // Arrange
        TodoItem todoItem = new TodoItem(2, "Task 2 edited", todo);
        when(todoItemService.updateTodoItem(anyLong(), any(TodoItem.class))).thenThrow(NotFoundException.class);

        // Act
        ResultActions result = mockMvc.perform(put("/todos/items/2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todoItem)));

        // Assert
        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteTodoItem() throws Exception {
        // Arrange
        when(todoItemService.deleteTodoItem(anyLong())).thenReturn(todoItem);

        // Act
        ResultActions result = mockMvc.perform(delete("/todos/items/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todoItem)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("Task 1")));
    }

    @Test
    public void deleteTodoItemInvalid() throws Exception {
        // Arrange
        TodoItem todoItem = new TodoItem(10, "Task 10", todo);
        when(todoItemService.deleteTodoItem(anyLong())).thenThrow(NotFoundException.class);

        // Act
        ResultActions result = mockMvc.perform(delete("/todos/items/10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(todo)));

        // Assert
        result.andExpect(status().isNotFound());
    }
}

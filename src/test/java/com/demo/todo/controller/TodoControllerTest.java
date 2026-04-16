package com.demo.todo.controller;

import com.demo.todo.model.Todo;
import com.demo.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @Test
    void testGetAllTodos() throws Exception {
        Todo todo1 = new Todo(1L, "Todo 1", "Description 1");
        Todo todo2 = new Todo(2L, "Todo 2", "Description 2");
        List<Todo> todos = Arrays.asList(todo1, todo2);

        when(todoService.getAllTodos()).thenReturn(todos);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Todo 1"))
                .andExpect(jsonPath("$[1].title").value("Todo 2"));
    }

    @Test
    void testGetTodoById() throws Exception {
        Todo todo = new Todo(1L, "Test Todo", "Test Description");

        when(todoService.getTodoById(1L)).thenReturn(todo);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Todo"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void testCreateTodo() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("New Todo");
        todo.setDescription("New Description");

        Todo savedTodo = new Todo(1L, "New Todo", "New Description");

        when(todoService.createTodo(any(Todo.class))).thenReturn(savedTodo);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Todo"));
    }

    @Test
    void testUpdateTodo() throws Exception {
        Todo updateData = new Todo();
        updateData.setTitle("Updated Todo");
        updateData.setDescription("Updated Description");
        updateData.setCompleted(true);

        Todo updatedTodo = new Todo(1L, "Updated Todo", "Updated Description");
        updatedTodo.setCompleted(true);

        when(todoService.updateTodo(eq(1L), any(Todo.class))).thenReturn(updatedTodo);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Todo"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testDeleteTodo() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Todo deleted successfully"));
    }
}

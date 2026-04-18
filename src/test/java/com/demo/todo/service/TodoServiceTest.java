package com.demo.todo.service;

import com.demo.todo.exception.TodoNotFoundException;
import com.demo.todo.model.Todo;
import com.demo.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TodoServiceTest {

    private TodoRepository repository;
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        repository = new TodoRepository();
        todoService = new TodoService(repository);
    }

    @Test
    void testCreateTodo() {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");

        Todo created = todoService.createTodo(todo);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test Todo", created.getTitle());
        assertEquals("Test Description", created.getDescription());
        assertFalse(created.isCompleted());
    }

    @Test
    void testGetTodoById() {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        Todo saved = todoService.createTodo(todo);

        Todo found = todoService.getTodoById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals("Test Todo", found.getTitle());
    }

    @Test
    void testUpdateTodo() {
        Todo todo = new Todo();
        todo.setTitle("Original Title");
        Todo saved = todoService.createTodo(todo);

        Todo updateData = new Todo();
        updateData.setTitle("Updated Title");
        updateData.setDescription("Updated Description");
        updateData.setCompleted(true);

        Todo updated = todoService.updateTodo(saved.getId(), updateData);

        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Description", updated.getDescription());
        assertTrue(updated.isCompleted());
    }

    @Test
    void testGetTodoCount() {
        assertEquals(0, todoService.getTodoCount());

        todoService.createTodo(new Todo(null, "Todo 1", "Description 1"));
        todoService.createTodo(new Todo(null, "Todo 2", "Description 2"));
        todoService.createTodo(new Todo(null, "Todo 3", "Description 3"));

        assertEquals(3, todoService.getTodoCount());
    }
}

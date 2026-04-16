package com.demo.todo.repository;

import com.demo.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TodoRepository {

    private final Map<Long, Todo> todos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Todo> findAll() {
        return new ArrayList<>(todos.values());
    }

    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(todos.get(id));
    }

    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(idGenerator.getAndIncrement());
        }
        todos.put(todo.getId(), todo);
        return todo;
    }

    public void deleteById(Long id) {
        todos.remove(id);
    }

    public boolean existsById(Long id) {
        return todos.containsKey(id);
    }
}

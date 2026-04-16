package com.demo.todo.model;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Todo {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private boolean completed;

    private LocalDateTime createdAt;

    public Todo() {
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }

    public Todo(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

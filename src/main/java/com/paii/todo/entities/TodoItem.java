package com.paii.todo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="todo_item")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    @JsonIgnore
    private Todo todo;

    public TodoItem() { }

    public TodoItem(long id, String content, Todo todo) {
        this.id = id;
        this.content = content;
        this.todo = todo;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "Id=" + id +
                ", content='" + content + '\'' +
                ", todo=" + todo +
                '}';
    }
}

package taskmanagement.entity;

import jakarta.persistence.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
    private String text;
    private String author;

    public  Comment() {}

    public Comment(Task task, String text) {
        this.task = task;
        task.getComments().add(this);
        this.text = text;
        this.author = SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

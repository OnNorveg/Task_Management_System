package taskmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Long id;
    @JsonProperty("id")
    private Long taskID;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String author;
    private String assignee;
    @JsonIgnore
    @OneToMany(mappedBy ="task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final Set<Comment> comments;


    {
        status = Status.CREATED;
        assignee = "none";
        comments = new HashSet<>();
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTaskID() {
        return id.toString();
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Set<Comment> getComments() {
        return comments;
    }
}

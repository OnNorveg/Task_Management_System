package taskmanagement.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface TaskDto {
    String getId();
    String getTitle();
    String getDescription();
    String getStatus();
    String getAuthor();
    String getAssignee();
    @JsonProperty("total_comments")
    int getTotalComments();
}

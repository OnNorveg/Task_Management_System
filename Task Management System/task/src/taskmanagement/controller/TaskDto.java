package taskmanagement.controller;

public interface TaskDto {
    public String getId();
    public String getTitle();
    public String getDescription();
    public String getStatus();
    public String getAuthor();
    public String getAssignee();
    public int getTotalComments();
}

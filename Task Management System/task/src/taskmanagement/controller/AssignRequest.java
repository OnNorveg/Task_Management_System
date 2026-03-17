package taskmanagement.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AssignRequest {

    @NotNull
    @Pattern(
            // Регулярное выражение: формат email ИЛИ слово "none"
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$|^none$",
            message = "Assignee must be a valid email or 'none'"
    )
    private String assignee;

    public String getAssignee() {
        return assignee;
    }
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}

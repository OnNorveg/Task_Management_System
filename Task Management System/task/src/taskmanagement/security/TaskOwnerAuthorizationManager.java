package taskmanagement.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import taskmanagement.entity.Task;
import taskmanagement.repository.TaskRepository;

import java.util.function.Supplier;

@Component
public class TaskOwnerAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final TaskRepository taskRepository;

    public TaskOwnerAuthorizationManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        String userName = authentication.get().getName();
        String taskId = context.getVariables().get("taskId");
        return taskRepository.findById(Long.valueOf(taskId))
                .map(task -> new AuthorizationDecision(task.getAuthor().equals(userName)
                || task.getAssignee().equals(userName)))
                .orElse(new AuthorizationDecision(true));
    }
}

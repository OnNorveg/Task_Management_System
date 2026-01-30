package taskmanagement.controller;

import jakarta.validation.Valid;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskmanagement.entity.UserProfile;
import taskmanagement.service.TaskManagementService;

@RestController
public class TaskManagementController {

    private final TaskManagementService taskManagementService;

    public TaskManagementController(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
    }

    @PostMapping("/api/accounts")
    ResponseEntity<?> accounts(@Valid @RequestBody UserProfile user) {
        return taskManagementService.addAccount(user);
    }

    @PostMapping("/api/tasks")
    ResponseEntity<?> SetTasks(@Valid @RequestBody TaskRequest taskRequest) {
        return taskManagementService.createTask(taskRequest);
    }

    @GetMapping("/api/tasks")
    ResponseEntity<?> GetTasks(@RequestParam(name = "author", required = false)  String author) {
        return taskManagementService.getAllTasks(author);
    }
}

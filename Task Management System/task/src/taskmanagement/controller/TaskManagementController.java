package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/api/tasks")
    ResponseEntity<?> tasks() {
        return ResponseEntity.ok().build();
    }
}

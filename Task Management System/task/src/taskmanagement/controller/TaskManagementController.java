package taskmanagement.controller;

import jakarta.validation.Valid;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import taskmanagement.entity.UserProfile;
import taskmanagement.service.TaskManagementService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
public class TaskManagementController {

    private final TaskManagementService taskManagementService;
    private final JwtEncoder jwtEncoder;

    public TaskManagementController(TaskManagementService taskManagementService,  JwtEncoder jwtEncoder) {
        this.taskManagementService = taskManagementService;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/api/accounts")
    ResponseEntity<?> accounts(@Valid @RequestBody UserProfile user) {
        return taskManagementService.addAccount(user);
    }

    @PostMapping("/api/tasks")
    ResponseEntity<?> setTasks(@Valid @RequestBody TaskRequest taskRequest) {
        return taskManagementService.createTask(taskRequest);
    }

    @GetMapping("/api/tasks")
    ResponseEntity<?> getTasks(@RequestParam(name = "author", required = false)  String author,
                               @RequestParam(name = "assignee", required = false) String assignee) {
        return taskManagementService.getAllTasks(author, assignee);
    }

    @GetMapping("/api/tasks/{taskId}/comments")
    ResponseEntity<?> getTaskComments(@PathVariable("taskId") Long taskId){
        return taskManagementService.getAllTaskComments(taskId);
    }

    @PostMapping("/api/auth/token")
    ResponseEntity<?> token(Authentication authentication) {
        var authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(60, ChronoUnit.SECONDS))
                .claim("scope", authorities)
                .build();

        return new ResponseEntity<>(Map.of("token",
                jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue()), HttpStatus.OK);
    }

    @PostMapping("/api/tasks/{taskId}/comments")
    ResponseEntity<?> postComment(@Valid @RequestBody CommentRequest commentRequest, @PathVariable("taskId") Long taskId) {
        return taskManagementService.addComment(commentRequest, taskId);
    }

    @PutMapping("/api/tasks/{taskId}/assign")
    ResponseEntity<?> updateTask(@Valid @RequestBody AssignRequest assignRequest, @PathVariable("taskId") Long taskId) {
        return taskManagementService.assignTask(assignRequest, taskId);
    }

    @PutMapping("/api/tasks/{taskId}/status")
    ResponseEntity<?> updateStatus(@Valid @RequestBody StatusRequest statusRequest, @PathVariable("taskId") Long taskId) {
        return taskManagementService.assignStatus(statusRequest, taskId);
    }

}

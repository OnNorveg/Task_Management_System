package taskmanagement.controller;

import jakarta.validation.Valid;
import org.hibernate.annotations.Parameter;
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
    ResponseEntity<?> SetTasks(@Valid @RequestBody TaskRequest taskRequest) {
        return taskManagementService.createTask(taskRequest);
    }

    @GetMapping("/api/tasks")
    ResponseEntity<?> GetTasks(@RequestParam(name = "author", required = false)  String author) {
        return taskManagementService.getAllTasks(author);
    }

    @PostMapping("/api/auth/token")
    String token(Authentication authentication) {
        var authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(60, ChronoUnit.SECONDS))
                .claim("scope", authorities)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}

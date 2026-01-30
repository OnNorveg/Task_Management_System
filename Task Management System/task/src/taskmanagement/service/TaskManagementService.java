package taskmanagement.service;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taskmanagement.controller.TaskRequest;
import taskmanagement.entity.Task;
import taskmanagement.repository.TaskRepository;
import taskmanagement.security.SecurityUser;
import taskmanagement.entity.UserProfile;
import taskmanagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManagementService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public TaskManagementService(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.taskRepository = taskRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile user = userRepository
                .findUserProfileByUsername(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new SecurityUser(user);
    }

    public ResponseEntity<?> addAccount(UserProfile user){
        if(userRepository.findUserProfileByUsername(user.getUsername()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> createTask(TaskRequest taskRequest){
        var task = new Task();
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllTasks(String author){
        Sort idSort = Sort.by("id");
        if(author.isEmpty()){
            return new ResponseEntity<>(taskRepository.findAll(idSort), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(taskRepository.findByAuthor(author), HttpStatus.OK);
        }
    }

}

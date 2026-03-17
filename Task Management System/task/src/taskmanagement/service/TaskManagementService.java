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
import taskmanagement.controller.AssignRequest;
import taskmanagement.controller.StatusRequest;
import taskmanagement.controller.TaskRequest;
import taskmanagement.entity.Task;
import taskmanagement.repository.TaskRepository;
import taskmanagement.security.SecurityUser;
import taskmanagement.entity.UserProfile;
import taskmanagement.repository.UserRepository;

import java.util.*;

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

    public ResponseEntity<?> getAllTasks(String author, String assignee){
        Sort idSort = Sort.by("id").descending();
        if(author == null && assignee == null){
            return new ResponseEntity<>(taskRepository.findAll(idSort), HttpStatus.OK);
        } else {
            if(author != null && assignee != null){
                return new ResponseEntity<>(taskRepository.findByAuthorAndAssigneeOrderByIdDesc(author, assignee), HttpStatus.OK);
            } else if(assignee != null){
                return new ResponseEntity<>(taskRepository.findByAssigneeOrderByIdDesc(assignee), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(taskRepository.findByAuthorOrderByIdDesc(author), HttpStatus.OK);
            }
        }
    }

    public ResponseEntity<?> assignTask(AssignRequest assignRequest, Long taskId){

        if(taskRepository.findById(taskId).isPresent()
                &&(userRepository.findUserProfileByUsername(assignRequest.getAssignee()).isPresent()
                || assignRequest.getAssignee().equals("none"))){
            Task task = taskRepository.findById(taskId).get();
            task.setAssignee(assignRequest.getAssignee());
            return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> assignStatus(StatusRequest statusRequest, Long taskId){
        if(taskRepository.findById(taskId).isPresent()){
            Task task = taskRepository.findById(taskId).get();
            task.setStatus(statusRequest.getStatus());
            return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
        }  else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

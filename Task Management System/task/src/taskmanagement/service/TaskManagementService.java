package taskmanagement.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taskmanagement.security.SecurityUser;
import taskmanagement.entity.UserProfile;
import taskmanagement.repository.UserRepository;

@Service
public class TaskManagementService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TaskManagementService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

}

package taskmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue
    private int id;
    @NotEmpty
    private String email;
    private String username;
    @NotEmpty
    @Size(min = 6)
    private String password;
    @JsonIgnore
    private final List<String> roles;

    {
        this.roles = new ArrayList<>();
        roles.add("USER");
    }

    public UserProfile(String email, String password) {
        this.email = email.toLowerCase();
        this.username = this.email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

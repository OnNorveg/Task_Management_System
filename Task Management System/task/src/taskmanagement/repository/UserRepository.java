package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import taskmanagement.entity.UserProfile;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserProfile, Long> {
    Optional<UserProfile> findUserProfileByUsername(String username);
}

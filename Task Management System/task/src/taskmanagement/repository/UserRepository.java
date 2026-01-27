package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.entity.UserProfile;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserProfile, Integer> {
    Optional<UserProfile> findAppUserByUsername(String username);
}

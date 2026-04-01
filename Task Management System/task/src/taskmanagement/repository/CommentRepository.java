package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import taskmanagement.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}

package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import taskmanagement.entity.Comment;
import taskmanagement.entity.Task;

import java.util.List;


public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Task> findByTaskOrderByIdDesc(Task task);
}

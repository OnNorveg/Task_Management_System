package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import taskmanagement.entity.Task;

import java.util.Optional;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, CrudRepository<Task, Long> {
    Optional<Task> findByAuthor(String author);
}

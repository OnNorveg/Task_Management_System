package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import taskmanagement.entity.Task;

import java.util.List;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, CrudRepository<Task, Long> {
    List<Task> findByAuthorOrderByIdDesc(String author);
}

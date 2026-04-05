package taskmanagement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import taskmanagement.controller.TaskDto;
import taskmanagement.entity.Task;

import java.util.List;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, CrudRepository<Task, Long> {
    List<Task> findByAuthorOrderByIdDesc(String author);
    List<Task> findByAssigneeOrderByIdDesc(String assignee);
    List<Task> findByAuthorAndAssigneeOrderByIdDesc(String author, String assignee);

    @Query("SELECT t.taskId as id, t.title as title, t.description as description, " +
            "t.status as status, t.author as author, t.assignee as assignee, " +
            "COUNT(c) as totalComments " +
            "FROM Task t LEFT JOIN t.comments c " +
            "WHERE t.author = :author " +
            "GROUP BY t.taskId, t.title, t.description, t.status, t.author, t.assignee")
    List<TaskDto> findByAuthorOrderByIdDescDto(@Param("author") String author);

    @Query("SELECT t.taskId as id, t.title as title, t.description as description, " +
            "t.status as status, t.author as author, t.assignee as assignee, " +
            "COUNT(c) as totalComments " +
            "FROM Task t LEFT JOIN t.comments c " +
            "WHERE t.assignee = :assignee " +
            "GROUP BY t.taskId, t.title, t.description, t.status, t.author, t.assignee")
    List<TaskDto> findByAssigneeOrderByIdDescDto(@Param("assignee") String assignee);

    @Query("SELECT t.taskId as id, t.title as title, t.description as description, " +
            "t.status as status, t.author as author, t.assignee as assignee, " +
            "COUNT(c) as totalComments " +
            "FROM Task t LEFT JOIN t.comments c " +
            "WHERE t.author = :author AND t.assignee = :assignee " +
            "GROUP BY t.taskId, t.title, t.description, t.status, t.author, t.assignee")
    List<TaskDto> findByAuthorAndAssigneeOrderByIdDescDto(@Param("author") String author, @Param("assignee") String assignee);
}

package com.dgarciacasam.RocketAPI.Tasks;

import com.dgarciacasam.RocketAPI.Tasks.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value = "SELECT t.* FROM Tasks t JOIN TasksUsers tu WHERE t.id = tu.taskId AND tu.userId = :id",nativeQuery = true)
    public List<Task> getTasksByUser(@Param("id") Integer id);

    @Query(value = "SELECT t.* FROM Tasks t JOIN TasksUsers tu ON t.id = tu.taskId WHERE tu.userId = :userId AND t.projectId = :projectId", nativeQuery = true)
    public List<Task> getTasksByProject(@Param("userId") Integer userId, @Param("projectId") Integer projectId);

    @Query(value = "SELECT t.* FROM Tasks t JOIN TasksUsers tu ON t.id = tu.taskId WHERE DATE(t.finishDate) = CURDATE() + INTERVAL 1 DAY AND t.columnId != 3", nativeQuery = true)
    public List<Task> getFinalishingTasks();
}


package pl.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.taskmanager.model.Task;
import pl.taskmanager.model.enums.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Integer countAllByTaskStatus(TaskStatus taskStatus);
}

package pl.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.taskmanager.model.Comment;
import pl.taskmanager.model.Project;
import pl.taskmanager.model.Task;
import pl.taskmanager.model.User;
import pl.taskmanager.model.dto.ProjectDto;
import pl.taskmanager.model.dto.TaskDto;
import pl.taskmanager.model.enums.TaskStatus;
import pl.taskmanager.repository.CommentRepository;
import pl.taskmanager.repository.ProjectRepository;
import pl.taskmanager.repository.TaskRepository;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private CommentRepository commentRepository;
    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository, CommentRepository commentRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

    // utwórz projekt
    public Project createProject(ProjectDto projectDto){
        return projectRepository.save(
                new Project(
                        projectDto.getAcronim(),
                        projectDto.getDescription(),
                        projectDto.getDateStart(),
                        projectDto.getDateStop()));
    }
    // zmień datę końca projektu
    public Project updateProjectStopDate(Long project_id, LocalDate dateStop){
        // pytamy o obiekt projektu po id
        Optional<Project> projectToUpdate = projectRepository.findById(project_id);
        if(projectToUpdate.isPresent()) {
            Project project = projectToUpdate.get();
            // zmieniamy deadline
            project.setDateStop(dateStop);
            // zapisujemy na tym samym obiekcie / update
            return projectRepository.save(project);
        }
        return new Project();
    }
    // dodaj nowego taska do projektu
    public Task createTask(TaskDto taskDto, Long project_id){
        // obiekt taska z przypisaniem do projektu
        Task task = new Task(
                taskDto.getTitle(),
                taskDto.getMessage(),
                LocalDate.now(),
                taskDto.getInterval(),
                projectRepository.getOne(project_id));
        return taskRepository.save(task);
    }
    // usuń taska z projektu
    public Task removeTask(Long task_id){
        // wyszukaj task po id
        Task deletedTask = taskRepository.getOne(task_id);
        // usuwam obiekt
        taskRepository.delete(deletedTask);
        // zwracam usunięty obiekt
        return deletedTask;
    }
    // usunięcie projektu wraz z jego taskami
    public Project removeProjectRecursively(Long project_id){
        Project deletedProject = projectRepository.getOne(project_id);
        projectRepository.delete(deletedProject);
        return deletedProject;
    }
    // metoda zwracająca wszystkie projekty
    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }
    // metoda zwaracjąca liczbę wszystkich tasków
    public Long countTasks(){
        return taskRepository.count();
    }
    // metoda zwracająca projekt po id
    public Project getProjectById(Long project_id){
        return projectRepository.getOne(project_id);
    }
    public Task getTaskById(Long task_id){
        return taskRepository.getOne(task_id);
    }
    public void addUserToTask(User user, Long task_id){
        // wydobycie obiektu taska po id
        Task task = taskRepository.getOne(task_id);
        // dodanie obiektu User do listy users w Task
        List<User> users = task.getUsers();
        users.add(user);
        task.setUsers(users);
        // update taska
        taskRepository.save(task);
    }
    public void deleteUserFromTaskUsersList(User user, Task task){
        List<User> users = task.getUsers();
        users.remove(user);
        task.setUsers(users);
        taskRepository.save(task);
    }
    public void updateTaskStatusAndInterval(Long task_id, Integer interval, TaskStatus taskStatus){
        Task task = taskRepository.getOne(task_id);
        task.setInterval(interval);
        task.setTaskStatus(taskStatus);
        taskRepository.save(task);
    }
    public List<Comment> getAllCommentsByTaskId(Long task_id){
        return commentRepository.findAllByTask(taskRepository.getOne(task_id));
    }
}

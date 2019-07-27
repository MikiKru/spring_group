package pl.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.taskmanager.model.Project;
import pl.taskmanager.model.dto.ProjectDto;
import pl.taskmanager.repository.ProjectRepository;
import pl.taskmanager.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
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

}

package pl.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.taskmanager.model.Project;
import pl.taskmanager.model.dto.ProjectDto;
import pl.taskmanager.service.ProjectService;

import java.time.LocalDate;

@RestController
public class ProjectController {
    private ProjectService projectService;
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping("/project/{acronim}&{description}&{dateStart}&{dateStop}")
    public Project createNewProject(
            @PathVariable String acronim, @PathVariable String description,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateStart,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateStop
            ){
        return projectService.createProject(new ProjectDto(acronim, description, dateStart,dateStop));
    }
    @PutMapping("/project/update/{project_id}&{dateStop}")
    public Project changeProjectDeadline(
            @PathVariable Long project_id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateStop
    ){
        return projectService.updateProjectStopDate(project_id,dateStop);
    }
}

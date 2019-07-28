package pl.taskmanager.controller.frontEndControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.taskmanager.model.Project;
import pl.taskmanager.model.Task;
import pl.taskmanager.model.dto.TaskDto;
import pl.taskmanager.service.ProjectService;

import javax.validation.Valid;
import java.util.List;
@Controller
public class ProjectControllerFrontEnd {

    private ProjectService projectService;
    @Autowired
    public ProjectControllerFrontEnd(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public String projects(Model model){
        List<Project> projectsList = projectService.getAllProjects();
        Long taskNo = projectService.countTasks();
        model.addAttribute("projectsList",projectsList);
        model.addAttribute("taskNo",taskNo);
        return "projects";
    }
    @GetMapping("/projects&delete&{project_id}")
    public String deleteProject(@PathVariable Long project_id){
        projectService.removeProjectRecursively(project_id);
        // przekierowanie na url
        return "redirect:/projects";
    }
    @GetMapping("/project&{project_id}")
    public String showProjectDetails(@PathVariable Long project_id, Model model) {
        Project project = projectService.getProjectById(project_id);
        model.addAttribute("project",project);
        model.addAttribute("taskDto",new TaskDto());
        return "project";
    }
    @PostMapping("/addTask&{project_id}")
    public String addTaskToProject(
            @ModelAttribute("taskDto") @Valid TaskDto taskDto,
            BindingResult bindingResult,
            @PathVariable Long project_id,
            Model model){
        if(bindingResult.hasErrors()){
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            Project project = projectService.getProjectById(project_id);
            model.addAttribute("project",project);
            model.addAttribute("taskDto",new TaskDto());
            return "project";
        }
        // dodaj taska do DB
        projectService.createTask(taskDto,project_id);
        return "redirect:/project&"+project_id;
    }
    @GetMapping("task&delete&{task_id}")
    public String deleteTask(@PathVariable Long task_id){
        Task task = projectService.removeTask(task_id);
        return "redirect:/project&"+ task.getProject().getProject_id();
    }


}

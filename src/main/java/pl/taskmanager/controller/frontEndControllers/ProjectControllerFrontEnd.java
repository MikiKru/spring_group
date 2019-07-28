package pl.taskmanager.controller.frontEndControllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.taskmanager.model.Project;
import pl.taskmanager.service.ProjectService;
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
        return "project";
    }


}

package pl.taskmanager.controller.frontEndControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.taskmanager.model.Project;
import pl.taskmanager.model.Task;
import pl.taskmanager.model.User;
import pl.taskmanager.model.dto.ProjectDto;
import pl.taskmanager.model.dto.TaskDto;
import pl.taskmanager.service.LoginService;
import pl.taskmanager.service.ProjectService;
import pl.taskmanager.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
@Controller
public class ProjectControllerFrontEnd {

    private ProjectService projectService;
    private LoginService loginService;
    private UserService userService;
    @Autowired
    public ProjectControllerFrontEnd(ProjectService projectService, LoginService loginService, UserService userService) {
        this.projectService = projectService;
        this.loginService = loginService;
        this.userService = userService;
    }

    @GetMapping("/projects")
    public String projects(Model model, Authentication auth){
        List<Project> projectsList = projectService.getAllProjects();
        Long taskNo = projectService.countTasks();
        model.addAttribute("projectsList",projectsList);
        model.addAttribute("taskNo",taskNo);
        model.addAttribute("projectDto", new ProjectDto());
        model.addAttribute("logged_email", loginService.getLoginFromCredentials(auth));
        model.addAttribute("isAdmin", loginService.isAdmin(auth));
        return "projects";
    }
    @PostMapping("/addProject")
    public String addProject(
               @ModelAttribute @Valid ProjectDto projectDto,
               BindingResult bindingResult,
               Model model
    ){
        List<Project> projectsList = projectService.getAllProjects();
        Long taskNo = projectService.countTasks();
        model.addAttribute("projectsList", projectsList);
        model.addAttribute("taskNo", taskNo);
        // jeżeli występują błędy w formularzu
        if(bindingResult.hasErrors()) {
            return "projects";
        }
        // czy dateStart jest > now()
        if(projectDto.getDateStart().isBefore(LocalDate.now())){
            System.out.println("BŁĄD: Data rozpoczęcia przed datą aktualną");
            model.addAttribute("dateStartValid","date start is before now");
            return "projects";
        }
        // czy dateStart < dateStop
        if(projectDto.getDateStart().isAfter(projectDto.getDateStop())){
            System.out.println("BŁĄD: Data rozpoczęcia za datą zakończenia");
            model.addAttribute("dateStopValid","date stop is before date start");
            return "projects";
        }
        // gdy jest wszystko ok -> zapisujemy projekt do DB
            projectService.createProject(projectDto);
            return "redirect:/projects";
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
            @ModelAttribute @Valid TaskDto taskDto,
            BindingResult bindingResult,
            @PathVariable Long project_id,
            Model model){
        if(bindingResult.hasErrors()){
            Project project = projectService.getProjectById(project_id);
            model.addAttribute("project",project);
//            model.addAttribute("taskDto",new TaskDto());
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
    // metoda odwołująca się do widoku wybranego zadania
    @GetMapping("/task&{task_id}")
    public String selectedTask(@PathVariable Long task_id, Model model){
        // wydobycie z bazy danych szukanego taska
        model.addAttribute("task", projectService.getTaskById(task_id));
        // przekazanie do wydoku listy użytkowników do przypisanie do tasków
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("addedUser", new User());
        return "task";
    }

    @PostMapping("/addUserToTask&{task_id}")
    public String addUserToTask(
            @PathVariable Long task_id,
            @ModelAttribute Task task,
            @ModelAttribute User user
    ){
        System.out.println("Added user: " + user);
        // dodanie wybranego usera do listy tasków
//        userService.
        return "redirect:/task&"+task_id;
    }
}

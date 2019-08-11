package pl.taskmanager.controller.frontEndControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerFrontEnd {

    @GetMapping("/")
    public String home(){
        return "redirect:/projects";
    }
}

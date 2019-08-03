package pl.taskmanager.controller.frontEndControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.taskmanager.model.dto.UserDto;
import pl.taskmanager.service.UserService;

@Controller
public class UserControllerFrontEnd {

    UserService userService;
    @Autowired
    public UserControllerFrontEnd(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userDto",new UserDto());
        return "register";
    }
}

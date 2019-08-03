package pl.taskmanager.controller.frontEndControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.taskmanager.model.dto.UserDto;
import pl.taskmanager.service.UserService;

import javax.validation.Valid;

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
        model.addAttribute("password_repeat", "");
        return "register";
    }
    @PostMapping("/register")
    public String register(
            @ModelAttribute @Valid UserDto userDto,
            BindingResult bindingResult,
            @ModelAttribute String password_repeat,
            Model model
    ){
        // błędy formularza
        if (bindingResult.hasErrors()){
            return "register";
        }
        // porwnanie haseł
//        if (!userDto.getPassword().equals(password_repeat)){
//            System.out.println(userDto.getPassword());
//            System.out.println(password_repeat);
//            model.addAttribute("password_error", "different passwords!");
//            return "register";
//        }
        // rejestracja
        userService.addUser(userDto);
        return "redirect:/projects";
    }


}

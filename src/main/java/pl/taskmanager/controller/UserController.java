package pl.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.taskmanager.model.User;
import pl.taskmanager.model.dto.UserDto;
import pl.taskmanager.service.UserService;
import java.util.List;

// nasłuchiwanie na żądania protkołu http
@RestController
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // Żądania http: GET, POST, PUT, DELETE
    @GetMapping("/")
    public String getName(){
        return "hello";
    }
    @GetMapping("/user/{user_name}")
    public String getUserName(@PathVariable String user_name){
        return "hello " + user_name;
    }
    // Obsługa żądań wypisania wszystkich użytkowników z DB
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    // Obsługa żądania rejestracji użytkownika
    @PostMapping("/register/{name}&{lastname}&{email}&{password}")
    public User addUser(
            @PathVariable String name,
            @PathVariable String lastname,
            @PathVariable String email,
            @PathVariable String password
    ){
        return userService.addUser(new UserDto(name,lastname,email,password));
    }



}

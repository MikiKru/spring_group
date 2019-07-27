package pl.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// nasłuchiwanie na żądania protkołu http
@RestController
public class UserController {

    // Żądania http: GET, POST, PUT, DELETE
    @GetMapping("/")
    public String getName(){
        return "hello";
    }
    @GetMapping("/{user_name}")
    public String getUserName(@PathVariable String user_name){
        return "hello " + user_name;
    }



}

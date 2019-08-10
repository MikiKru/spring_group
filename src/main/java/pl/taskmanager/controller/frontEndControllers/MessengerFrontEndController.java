package pl.taskmanager.controller.frontEndControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.taskmanager.model.dto.CommentDto;
import pl.taskmanager.model.dto.ContactDto;
import pl.taskmanager.repository.UserRepository;

import javax.validation.Valid;

@Controller
public class MessengerFrontEndController {

    private UserRepository userRepository;
    @Autowired
    public MessengerFrontEndController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/sendMessage")
    public String sendMessage(
            Model model
    ){
        // lista wszystkich użytkowników
        model.addAttribute("users", userRepository.findAll());
        // przekazanie do widoku modelu contactDto
        model.addAttribute("contactDto", new ContactDto());
        return "contact";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(
            @ModelAttribute @Valid ContactDto contactDto,
            BindingResult bindingResult,
            Authentication auth,
            Model model
    ){
        if(bindingResult.hasErrors()){
            // lista wszystkich użytkowników
            model.addAttribute("users", userRepository.findAll());
            return "contact";
        }
        // auto mailing
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        System.out.println("Content: " + contactDto.getContent());
        System.out.println("User to: " + contactDto.getEmailTo());
        System.out.println("User from: " + userDetails.getUsername());
        return "redirect:/sendMessage";
    }



}

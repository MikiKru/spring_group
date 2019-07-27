package pl.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.taskmanager.model.User;
import pl.taskmanager.repository.UserRepository;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // wypisz wszystkich użytkowników
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}

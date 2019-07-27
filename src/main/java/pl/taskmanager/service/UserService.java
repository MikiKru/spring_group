package pl.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.taskmanager.model.User;
import pl.taskmanager.model.dto.UserDto;
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
    // rejestracja użytkownika
    public User addUser(UserDto user){
        return userRepository.save(
                new User(
                        user.getName(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getPassword())
                );
    }

}

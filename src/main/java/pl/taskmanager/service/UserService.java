package pl.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.taskmanager.model.Role;
import pl.taskmanager.model.User;
import pl.taskmanager.model.dto.UserDto;
import pl.taskmanager.repository.RoleRepository;
import pl.taskmanager.repository.UserRepository;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // wypisz wszystkich użytkowników
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    // rejestracja użytkownika
    public User addUser(UserDto user){
        // utwórz obiekt User
        User registered_user = new User(
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword())); // zwraca hash hasła
        registered_user.addRole(roleRepository.getOne(1L));
        return userRepository.save(registered_user);
    }
    // logowanie użytkownika
    public String loginUser(String email, String password){
        User user = userRepository.findFirstByEmailAndPassword(email,password);
        if(user == null){
            return "błąd logowania";
        }
        return "zarejestrowano: " + user.toString();
    }
    // metoda zwracająca obiekt roli po id roli
    public Role getRoleById(Long id){
        return roleRepository.getOne(id);
    }

}

package com.example.store.projektstore.Service;

//import com.example.store.projektstore.DTO.UserDTO;

import com.example.store.projektstore.DTO.UserDTO;
import com.example.store.projektstore.Model.InformationStore;
import com.example.store.projektstore.Model.Role;
import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Repository.RoleRepository;
import com.example.store.projektstore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findUsersWithRole(String roleName) {
        return userRepository.findByRolesName(roleName);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    public boolean isUserExists(String userLogin) {
        return userRepository.findByLogin(userLogin).isPresent();
    }
    public void sendInformationToUser(InformationStore information, String login) {
        Optional<User> user = userRepository.findByLogin(login);
        User user1 = user.get();
        if (user != null) {
            // Logika wysyłania informacji do użytkownika, np. dodanie do listy informacji użytkownika
            InformationStore newInformation = new InformationStore();
            newInformation.setUser(user.get());
            newInformation.setTitle(information.getTitle());
            newInformation.setDescription(information.getDescription());
            newInformation.setLink(information.getLink());
            newInformation.setCategory(information.getCategory());
            newInformation.setDateAdded(LocalDate.now());

            user1.getInformationStoreList().add(newInformation);
            userRepository.save(user1);
        }
    }

    public UserDTO createUser(UserDTO userDTO) {
        Optional<Role> userRole = roleRepository.findByName("limited_user");

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setLogin(userDTO.getLogin());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAge(userDTO.getAge());
        user.setRoles(Collections.singleton(userRole.get()));

        User newUser = userRepository.save(user);

        UserDTO userResponse = new UserDTO();
        userResponse.setId(newUser.getId());
        userResponse.setFirstName(newUser.getFirstName());
        userResponse.setLastName(newUser.getLastName());
        userResponse.setLogin(newUser.getLogin());
        userResponse.setPassword(newUser.getPassword());
        userResponse.setAge(newUser.getAge());
        userResponse.setRoles(newUser.getRoles());
        return userResponse;
    }

}

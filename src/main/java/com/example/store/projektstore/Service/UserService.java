package com.example.store.projektstore.Service;

import com.example.store.projektstore.DTO.UserDTO;
import com.example.store.projektstore.Model.Role;
import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Repository.RoleRepository;
import com.example.store.projektstore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

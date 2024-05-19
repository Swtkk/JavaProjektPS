package com.example.store.projektstore.Service;

//import com.example.store.projektstore.DTO.UserDTO;

import com.example.store.projektstore.Model.Role;
import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Repository.RoleRepository;
import com.example.store.projektstore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    public boolean isUserExists(String userLogin) {
        return userRepository.findByLogin(userLogin).isPresent();
    }

    public User createUser(User userDTO) {
        Optional<Role> userRole = roleRepository.findByName("limited_user");

        if (userRole.isEmpty()) {
            throw new RuntimeException("Role not found");
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setLogin(userDTO.getLogin());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAge(userDTO.getAge());
        user.setRoles(Collections.singleton(userRole.get()));

        User newUser = userRepository.save(user);
        return newUser;
    }

}

package com.example.store.projektstore.Controller;

import com.example.store.projektstore.Model.Role;
import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Service.RoleService;
import com.example.store.projektstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findUsersWithRole("limited_user"));
        return "userList";
    }

    @PostMapping("/{userId}/approve")
    public String approveUser(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user != null) {
            Role fullUserRole = roleService.findByName("full_user");
            if (fullUserRole != null) {
                Set<Role> roles = new HashSet<>(user.getRoles());
                roles.removeIf(role -> role.getName().equals("limited_user"));
                roles.add(fullUserRole);
                user.setRoles(roles);
                userService.save(user);
            }
        }
        return "redirect:/users";
    }
}

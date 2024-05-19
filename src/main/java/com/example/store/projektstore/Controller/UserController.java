package com.example.store.projektstore.Controller;

import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller   // Zmiana na @Controller, aby obsłużyć widoki Thymeleaf
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String getUsers(Model model){
        List<User> userEntities = userService.getAllUsers();
        model.addAttribute("users", userEntities);
        return "Users";
    }


}



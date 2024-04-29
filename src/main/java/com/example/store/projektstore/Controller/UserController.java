package com.example.store.projektstore.Controller;

import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller   // Zmieniłem @RestController na @Controller, aby obsłużyć widoki Thymeleaf
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String getUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "Users";  // Nazwa szablonu Thymeleaf
    }


}



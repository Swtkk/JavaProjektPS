package com.example.store.projektstore.Controller;

import com.example.store.projektstore.DTO.UserDTO;
import com.example.store.projektstore.Model.User;
//import com.example.store.projektstore.Repository.RoleRepository;
import com.example.store.projektstore.Repository.RoleRepository;
import com.example.store.projektstore.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginAndRegisterController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/login")
    public String getLoginTemplate(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";  // Redirect authenticated users to home
        }
        model.addAttribute("userForm", new User());  // Utworzenie pustego obiektu użytkownika dla formularza
        return "Login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("userForm") User userForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Login";  // Powrót do formularza w przypadku błędów
        }
        //  dodac bledy przy logowaniu
        return "redirect:/";  // Przekierowanie po udanym logowaniu
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";  // Redirect authenticated users to home
        }
        model.addAttribute("registerForm", new User()); // pobieranie forumularza dla nowego uzytkownika
        return "Register";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerForm") User user, BindingResult bindingResult, Model model) {
        model.addAttribute("formSubmitted", true);
        if (userService.isUserExists(user.getLogin())) {
            bindingResult.rejectValue("login", "user.login", "Login jest juz zajety");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerForm", user); // przekazywanie modelu z powrotem z błędami
            return "Register";
        }
        UserDTO newUser = new UserDTO();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setAge(user.getAge());
        newUser.setLogin(user.getLogin());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));


        userService.createUser(newUser);
        return "redirect:/success";
    }

}

package com.example.store.projektstore.Controller;

import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginAndRegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String getLoginTemplate(Model model) {
        model.addAttribute("userForm", new User());  // Utworzenie pustego obiektu użytkownika dla formularza
        return "Login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("userForm") User userForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Login";  // Powrót do formularza w przypadku błędów
        }
        // Logika logowania
        return "redirect:/homepage";  // Przekierowanie po udanym logowaniu
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        model.addAttribute("registerForm", new User()); // pobieranie forumularza dla nowego uzytkownika
        return "Register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerForm") User user, BindingResult bindingResult, Model model) {
        if(userService.isUserExists(user.getLogin())) {
            System.out.println("123");
            bindingResult.rejectValue("login", "user.login","Login jest juz zajety");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerForm", user); // przekazywanie modelu z powrotem z błędami
            return "Register";
        }

        userService.createUser(user);
        return "redirect:/success";
    }

}

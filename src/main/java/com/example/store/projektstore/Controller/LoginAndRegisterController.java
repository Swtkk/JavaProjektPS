package com.example.store.projektstore.Controller;

import com.example.store.projektstore.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginAndRegisterController {


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
    public String registerUser(Model model){
        model.addAttribute("registerForm", new User());
        return "Register";
    }

}

package com.example.store.projektstore.Controller;

import com.example.store.projektstore.Model.Category;
import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Service.CategoryService;
import com.example.store.projektstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String showCreateCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categoryForm";  // Przekierowanie do formularza tworzenia kategorii
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, @AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (bindingResult.hasErrors()) {
            return "categoryForm";
        }
        User user = userService.findByLogin(currentUser.getUsername());
        category.setUser(user);
        categoryService.save(category);
        model.addAttribute("successMessage", "Kategoria została pomyślnie dodana!");
        model.addAttribute("category", new Category());  // Reset formularza
        return "categoryForm";
    }

    @GetMapping
    public String listCategories(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        User user = userService.findByLogin(currentUser.getUsername());
        model.addAttribute("categories", categoryService.findAllByUserLogin(user.getLogin()));
        return "categoryList";  // Widok z listą kategorii
    }
}

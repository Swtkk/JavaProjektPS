package com.example.store.projektstore.Controller;

import com.example.store.projektstore.Model.Category;
import com.example.store.projektstore.Model.InformationStore;
import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Service.CategoryService;
import com.example.store.projektstore.Service.InformationService;
import com.example.store.projektstore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Controller
@SessionAttributes({"sortBy", "sortDir", "filterCategory"})
public class InformationController {

    private final UserService userService;
    private final InformationService informationService;
    private final CategoryService categoryService;

    @Autowired
    public InformationController(UserService userService, InformationService informationService, CategoryService categoryService) {
        this.userService = userService;
        this.informationService = informationService;
        this.categoryService = categoryService;
    }

    @PostMapping("/informations/{informationId}")
    public String deleteInformation(@PathVariable Long informationId) {
        try {
            informationService.deleteInformation(informationId);
            return "redirect:/informations";
        } catch (Exception e) {
            return "errorPage";
        }
    }

    @GetMapping("/informations/{informationId}/edit")
    public String getInformation(@PathVariable Long informationId, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        Optional<InformationStore> informationOptional = informationService.getInformationById(informationId);
        if (!informationOptional.isPresent()) {
            model.addAttribute("error", "Informacja nie znaleziona.");
            return "errorPage";
        }
        User user = userService.findByLogin(currentUser.getUsername());
        model.addAttribute("information", informationOptional.get());
        model.addAttribute("categories", categoryService.findAllByUserLogin(user.getLogin()));
        return "EditInformation";
    }

    @PostMapping("/informations/{informationId}/edit")
    public String updateInformation(@PathVariable Long informationId, @Valid @ModelAttribute("information") InformationStore updatedInformation, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByLogin(currentUser.getUsername());
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllByUserLogin(user.getLogin()));
            return "EditInformation";
        }
        informationService.updateInformation(informationId, updatedInformation);
        return "redirect:/informations";
    }

    @ModelAttribute("sortBy")
    public String sortBy() {
        return "dateAdded";
    }

    @ModelAttribute("sortDir")
    public String sortDir() {
        return "desc";
    }

    @ModelAttribute("filterCategory")
    public String filterCategory() {
        return "";
    }

    @GetMapping("/informations")
    public String getInformations(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "dateAdded") String sortBy, @RequestParam(defaultValue = "desc") String sortDir, @RequestParam(defaultValue = "") String filterCategory, Model model, HttpSession session, @AuthenticationPrincipal UserDetails currentUser) {
        session.setAttribute("sortBy", sortBy);
        session.setAttribute("sortDir", sortDir);
        session.setAttribute("filterCategory", filterCategory);

        User user = userService.findByLogin(currentUser.getUsername());
        Page<InformationStore> paginatedInformation;
        Sort.Direction direction = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        if ("categoryCount".equals(sortBy)) {
            paginatedInformation = informationService.findAllOrderByCategoryCount(user.getLogin(), page, 3, direction);
        } else {
            paginatedInformation = informationService.findPaginatedByUserLogin(user.getLogin(), page, 3, sortBy, direction, filterCategory);
        }

        model.addAttribute("informationsPage", paginatedInformation);
        model.addAttribute("userLogin", user.getLogin());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("filterCategory", filterCategory);
        model.addAttribute("categories", categoryService.findAllByUserLogin(user.getLogin()));
        return "Informations";
    }

    @GetMapping("/add-information")
    public String showAddInformationForm(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByLogin(currentUser.getUsername());
        model.addAttribute("information", new InformationStore());
        model.addAttribute("categories", categoryService.findAllByUserLogin(user.getLogin()));
        return "AddInformation";
    }

    @PostMapping("/add-information")
    public String addInformation(@Valid @ModelAttribute("information") InformationStore information, BindingResult result, Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByLogin(currentUser.getUsername());
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllByUserLogin(user.getLogin()));
            return "AddInformation";
        }
        Category category = categoryService.findById(information.getCategory().getId());
        information.setCategory(category);
        information.setUser(user);
        informationService.saveInformation(information);
        model.addAttribute("successMessage", "Pomyślnie dodano informację.");
        model.addAttribute("information", new InformationStore());
        model.addAttribute("categories", categoryService.findAllByUserLogin(user.getLogin()));
        return "AddInformation";
    }
}

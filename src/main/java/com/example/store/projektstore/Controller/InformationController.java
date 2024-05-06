package com.example.store.projektstore.Controller;

import com.example.store.projektstore.Model.InformationStore;
import com.example.store.projektstore.Model.User;
import com.example.store.projektstore.Service.InformationService;
import com.example.store.projektstore.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class InformationController {

    @Autowired
    private UserService userService;

    @Autowired
    private InformationService informationService;

    @GetMapping("/{login}/informations/{informationId}/edit")
    public String getInformation(@PathVariable String login, @PathVariable Long informationId, Model model) {
        Optional<InformationStore> informationOptional = informationService.getInformationById(informationId);
        if (!informationOptional.isPresent()) {
            model.addAttribute("error", "Informacja nie znaleziona.");
            return "errorPage";  // Możesz stworzyć stronę błędu lub przekierować gdzie indziej
        }
        model.addAttribute("information", informationOptional.get());
        model.addAttribute("login", login);
        model.addAttribute("informationId", informationId);
        return "EditInformation";  // Nazwa pliku HTML formularza edycji
    }

    @PostMapping("/{login}/informations/{informationId}/edit")
    public String updateInformation(@PathVariable String login, @PathVariable Long informationId,
                                    @Valid @ModelAttribute("information") InformationStore updatedInformation,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("login", login);
            model.addAttribute("informationId", informationId);
            model.addAttribute("information", updatedInformation);
            return "EditInformation"; // Zwróć formularz z błędami
        }

        InformationStore information = informationService.updateInformation(informationId, updatedInformation);
        return "redirect:/" + login + "/informations";  // Przekierowanie do listy informacji
    }

    @GetMapping("/{login}/informations")
    public String getInformations(@PathVariable String login, Model model) {
        User user = userService.findByLogin(login);

        List<InformationStore> informationStoreList = user.getInformationStoreList();
        model.addAttribute("informations", informationStoreList);
        model.addAttribute("userLogin", login);
        return "Informations";
    }


    // Przekazuje formularz do dodawania informacji
    @GetMapping("/{login}/add-information")
    public String showAddInformationForm(@PathVariable String login, Model model) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return "errorPage"; // lub zwróć odpowiedni komunikat błędu
        }
        InformationStore information = new InformationStore();
        model.addAttribute("information", information);
        model.addAttribute("login", login);  // Dodajemy login do modelu, aby można było użyć go w formularzu
        return "AddInformation";
    }

    // Wysyła metode post ktora dodaje informacje do bazy oraz przypisuje ja uzytkownikowi
    @PostMapping("/{login}/add-information")
    public String addInformation(@PathVariable String login,
                                 @Valid @ModelAttribute("information") InformationStore information,
                                 BindingResult result, // Dodaj to
                                 Model model) {
        User user = userService.findByLogin(login);
        if (user == null) {
            model.addAttribute("error", "Nie znaleziono użytkownika.");
            return "errorPage";
        }

        if (result.hasErrors()) {
            model.addAttribute("information", information);
            return "AddInformation"; // Zwróć formularz z błędami
        }

        information.setUser(user);
        informationService.saveInformation(information);
        model.addAttribute("successMessage", "Pomyślnie dodano informację.");
        model.addAttribute("information", new InformationStore()); // Reset formularza
        return "AddInformation";
    }


}

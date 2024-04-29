package com.example.store.projektstore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/homepage")
public class HomepageController {

    @GetMapping()
    public String getHomepage(){
        return "Home";
    }
}

package com.example.techstore.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(Authentication authentication, Model model) {

        String username = (authentication != null) ? authentication.getName() : "Гость";

        model.addAttribute("username", username);


        return "products";
    }
}

package com.example.techstore.controller;

import com.example.techstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profilePage(Authentication authentication, Model model) {

        if (authentication != null) {
            String username = authentication.getName();

            model.addAttribute("username", username);
        } else {
            model.addAttribute("username", "Гость");
        }
        return "profile";
    }
    @Autowired
    private UserService userService;

    @PostMapping("/changePassword")
    public String changePassword(Authentication authentication, String oldPassword, String newPassword, String confirmPassword, Model model) {
        String username = authentication.getName();
        if (newPassword.equals(confirmPassword)) {
            boolean success = userService.changePassword(username, oldPassword, newPassword);
            if (success) {
                model.addAttribute("message", "Password changed successfully");
                return "profile"; // Переход на страницу профиля
            } else {
                model.addAttribute("error", "Old password is incorrect");
            }
        } else {
            model.addAttribute("error", "New passwords do not match");
        }
        return "changePassword";
    }
}

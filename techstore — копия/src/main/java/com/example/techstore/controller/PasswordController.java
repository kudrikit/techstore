package com.example.techstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordController {

    @GetMapping("/changePassword")
    public String showChangePasswordForm() {
        return "changePassword";
    }
}

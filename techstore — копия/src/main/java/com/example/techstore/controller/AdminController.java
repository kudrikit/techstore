package com.example.techstore.controller;


import com.example.techstore.model.User;
import com.example.techstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;



    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // список пользователей
    }
    @PostMapping("/changeRole")
    public String changeUserRole(@RequestParam Long userId, @RequestParam String newRole) {
        if (!newRole.startsWith("ROLE_")) {
            newRole = "ROLE_" + newRole;
        }
        userService.updateUserRole(userId, newRole);
        return "redirect:/admin/users";
    }
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/users";
    }

}

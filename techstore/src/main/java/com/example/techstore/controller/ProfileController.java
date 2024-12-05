package com.example.techstore.controller;

import com.example.techstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  @Autowired
  private UserService userService;

  // Получение профиля пользователя
  @GetMapping
  public ResponseEntity<Map<String, String>> profilePage(Authentication authentication) {
    Map<String, String> response = new HashMap<>();

    if (authentication != null) {
      String username = authentication.getName();
      String role = userService.getUserRole(username);
      response.put("username", username);
      response.put("role", role);
    } else {
      response.put("username", "Гость");
    }

    return ResponseEntity.ok(response); // Возвращаем JSON с именем пользователя и ролью
  }

  // Изменение пароля
  @PostMapping("/changePassword")
  public ResponseEntity<Map<String, String>> changePassword(
    Authentication authentication,
    @RequestParam String oldPassword,
    @RequestParam String newPassword,
    @RequestParam String confirmPassword) {

    Map<String, String> response = new HashMap<>();

    if (authentication == null) {
      response.put("error", "User is not authenticated");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    String username = authentication.getName();

    // Проверка совпадения нового пароля и подтверждения
    if (!newPassword.equals(confirmPassword)) {
      response.put("error", "New passwords do not match");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Изменение пароля через UserService
    boolean success = userService.changePassword(username, oldPassword, newPassword);

    if (success) {
      response.put("message", "Password changed successfully");
      return ResponseEntity.ok(response);
    } else {
      response.put("error", "Old password is incorrect");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }
}

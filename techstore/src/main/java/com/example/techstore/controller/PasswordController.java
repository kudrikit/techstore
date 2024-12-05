package com.example.techstore.controller;

import com.example.techstore.model.User;
import com.example.techstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PasswordController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // Эндпоинт для изменения пароля
  @PostMapping("/changePassword")
  public ResponseEntity<String> changePassword(
    Authentication authentication,
    @RequestParam String oldPassword,
    @RequestParam String newPassword) {

    // Получаем текущего пользователя из аутентификации
    User user = userRepository.findByUsername(authentication.getName()).orElse(null);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }

    // Проверяем, совпадает ли старый пароль
    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect old password");
    }

    // Устанавливаем новый пароль и сохраняем пользователя
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);

    return ResponseEntity.ok("Password changed successfully");
  }
}

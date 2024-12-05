package com.example.techstore.controller;

import com.example.techstore.model.User;
import com.example.techstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController  // Используем @RestController для REST API
@RequestMapping("/api/auth")  // Добавляем префикс /api/auth для эндпоинтов аутентификации
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // Регистрация пользователя
  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
    // Проверка, существует ли пользователь с таким же именем
    if (userRepository.findByUsername(username).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
    }

    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password)); // Хешируем пароль
    user.setEmail(email);
    user.setRole("ROLE_USER"); // Присваиваем роль "ROLE_USER" по умолчанию

    userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
  }

  // Вход (здесь просто отображаем сообщение, можно настроить JWT)
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
    String username = loginData.get("username");
    String password = loginData.get("password");

    User user = userRepository.findByUsername(username).orElse(null);
    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
      return ResponseEntity.ok("Login successful");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
  }

}

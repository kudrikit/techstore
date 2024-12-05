package com.example.techstore.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController  // Используем @RestController для REST API
@RequestMapping("/api")  // Добавляем префикс /api для маршрутов API
public class HomeController {

  @GetMapping("/home")
  public Map<String, String> homePage(Authentication authentication) {
    // Проверяем, есть ли аутентифицированный пользователь
    String username = (authentication != null) ? authentication.getName() : "Гость"; // Если не авторизован, показываем "Гость"

    // Создаем JSON-ответ с именем пользователя
    Map<String, String> response = new HashMap<>();
    response.put("username", username);

    return response; // Возвращаем JSON с именем пользователя
  }
}

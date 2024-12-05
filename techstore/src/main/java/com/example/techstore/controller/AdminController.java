package com.example.techstore.controller;

import com.example.techstore.model.User;
import com.example.techstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Используем RestController вместо Controller для REST API
@RequestMapping("/api/admin")  // Добавляем /api для обозначения, что это API эндпоинты
public class AdminController {

  @Autowired
  private UserService userService;

  // Получение списка всех пользователей
  @GetMapping("/users")
  public ResponseEntity<List<User>> viewUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);  // Возвращаем JSON с пользователями
  }

  // Изменение роли пользователя
  @PostMapping("/changeRole")
  public ResponseEntity<?> changeUserRole(@RequestParam Long userId, @RequestParam String newRole) {
    // Добавляем префикс ROLE_, если его еще нет
    if (!newRole.startsWith("ROLE_")) {
      newRole = "ROLE_" + newRole;
    }
    userService.updateUserRole(userId, newRole);
    return ResponseEntity.ok("User role updated successfully"); // Возвращаем сообщение об успешном обновлении
  }

  // Удаление пользователя
  @DeleteMapping("/deleteUser")  // Для REST API лучше использовать метод DELETE
  public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
    userService.deleteUser(userId); // Метод удаления пользователя
    return ResponseEntity.ok("User deleted successfully"); // Возвращаем сообщение об успешном удалении
  }
}

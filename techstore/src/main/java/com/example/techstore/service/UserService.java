package com.example.techstore.service;

import com.example.techstore.model.User;
import com.example.techstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public boolean changePassword(String username, String oldPassword, String newPassword) {
    Optional<User> userOpt = userRepository.findByUsername(username);
    if (userOpt.isPresent() && passwordEncoder.matches(oldPassword, userOpt.get().getPassword())) {
      User user = userOpt.get();
      user.setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(user);
      return true;
    }
    return false;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public void updateUserRole(Long userId, String newRole) {
    userRepository.findById(userId).ifPresent(user -> {
      user.setRole(newRole);
      userRepository.save(user);
    });
  }

  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

  // Добавьте этот метод для получения роли пользователя по имени
  public String getUserRole(String username) {
    return userRepository.findByUsername(username)
      .map(User::getRole)
      .orElse("ROLE_USER"); // Если пользователь не найден, возвращаем роль по умолчанию
  }
}

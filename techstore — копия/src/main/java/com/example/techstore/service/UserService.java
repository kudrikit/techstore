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
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPassword())) {
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
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setRole(newRole); // Измените роль пользователя
            userRepository.save(user); // Сохраните изменения
        }
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId); // Удаляет пользователя по ID
    }
}

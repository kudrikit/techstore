package com.example.techstore.repository;

import com.example.techstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Метод для поиска пользователя по имени
    User findByUsername(String username);
}

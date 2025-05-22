package com.casino.repository;

import com.casino.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByTelegramId(Long telegramId);
}
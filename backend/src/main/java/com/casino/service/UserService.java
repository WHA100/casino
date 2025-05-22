package com.casino.service;

import com.casino.model.User;
import com.casino.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getOrCreateUser(Long telegramId, String username) {
        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            user = new User();
            user.setTelegramId(telegramId);
            user.setUsername(username);
            user.setBalance(1000);
            userRepository.save(user);
        }
        return user;
    }
    
    public User updateBalance(Long telegramId, int amount) {
        User user = userRepository.findByTelegramId(telegramId);
        if (user != null) {
            user.setBalance(user.getBalance() + amount);
            return userRepository.save(user);
        }
        return null;
    }
} 
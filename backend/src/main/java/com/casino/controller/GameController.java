package com.casino.controller;

import com.casino.model.User;
import com.casino.model.GameHistory;
import com.casino.service.UserService;
import com.casino.repository.GameHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    private final Random random = new Random();

    @PostMapping("/play")
    public ResponseEntity<?> playGame(@RequestParam Long telegramId, @RequestParam int bet) {
        try {
        User user = userService.getOrCreateUser(telegramId, null);
        
        if (user.getBalance() < bet) {
            return ResponseEntity.badRequest().body("Недостаточно средств");
        }

            if (bet <= 0) {
                return ResponseEntity.badRequest().body("Ставка должна быть больше 0");
            }

        boolean isWin = random.nextBoolean();
        int result = isWin ? bet : -bet;
            int balanceBefore = user.getBalance();
        
        user = userService.updateBalance(telegramId, result);
            
            // Сохраняем историю игры
            GameHistory gameHistory = new GameHistory();
            gameHistory.setUser(user);
            gameHistory.setBet(bet);
            gameHistory.setWin(isWin);
            gameHistory.setWinAmount(isWin ? bet : 0);
            gameHistoryRepository.save(gameHistory);
        
        Map<String, Object> response = new HashMap<>();
        response.put("result", isWin ? "Орел" : "Решка");
        response.put("isWin", isWin);
        response.put("balance", user.getBalance());
        response.put("winAmount", isWin ? bet : 0);
            response.put("balanceBefore", balanceBefore);
        
        return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка при обработке игры: " + e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam Long telegramId) {
        try {
        User user = userService.getOrCreateUser(telegramId, null);
        Map<String, Object> response = new HashMap<>();
        response.put("balance", user.getBalance());
        return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Произошла ошибка при получении баланса: " + e.getMessage());
        }
    }
} 
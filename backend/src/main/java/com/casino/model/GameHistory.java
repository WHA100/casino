package com.casino.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "game_history")
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isWin;
    private int bet;
    private int winAmount;
    private LocalDateTime gameTime;

    @PrePersist
    protected void onCreate() {
        gameTime = LocalDateTime.now();
    }
} 
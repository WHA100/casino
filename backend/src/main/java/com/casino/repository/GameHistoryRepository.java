package com.casino.repository;

import com.casino.model.GameHistory;
import com.casino.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    List<GameHistory> findByUserOrderByGameTimeDesc(User user);
} 
package com.casino.config;

import com.casino.bot.CasinoBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {
    private static final Logger logger = LoggerFactory.getLogger(BotConfig.class);

    @Autowired
    private CasinoBot casinoBot;

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                botsApi.registerBot(casinoBot);
                logger.info("Bot registered successfully");
            } catch (TelegramApiException e) {
                logger.error("Error registering bot: {}", e.getMessage());
                // Продолжаем работу даже при ошибке регистрации
            }
            return botsApi;
        } catch (TelegramApiException e) {
            logger.error("Error creating TelegramBotsApi: {}", e.getMessage());
            throw new RuntimeException("Could not create TelegramBotsApi", e);
        }
    }
} 
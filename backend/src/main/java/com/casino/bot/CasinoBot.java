package com.casino.bot;

import com.casino.model.User;
import com.casino.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CasinoBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(CasinoBot.class);

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.webapp-url}")
    private String webappUrl;

    @Autowired
    private UserService userService;

    @Override
    public String getBotUsername() {
        logger.info("Bot username: {}", botUsername);
        return botUsername;
    }

    @Override
    public String getBotToken() {
        logger.info("Bot token length: {}", botToken.length());
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Received update: {}", update);
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getUserName();
            
            logger.info("Received message: {} from user: {} (chatId: {})", messageText, username, chatId);

            if (messageText.equals("/start")) {
                logger.info("Processing /start command");
                User user = userService.getOrCreateUser(chatId, username);
                sendWelcomeMessage(chatId, user.getBalance());
            } else if (messageText.equals("/balance")) {
                logger.info("Processing /balance command");
                User user = userService.getOrCreateUser(chatId, username);
                sendBalanceMessage(chatId, user.getBalance());
            }
        }
    }

    private void sendWelcomeMessage(long chatId, int balance) {
        logger.info("Sending welcome message to chatId: {} with balance: {}", chatId, balance);
        
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format("Добро пожаловать в Суперказик! 🎰\nВаш баланс: %d монет\nНажмите кнопку ниже, чтобы начать игру:", balance));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("🎮 Играть");
        button.setWebApp(new org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo(webappUrl));
        
        row.add(button);
        keyboard.add(row);
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

        try {
            execute(message);
            logger.info("Welcome message sent successfully");
        } catch (TelegramApiException e) {
            logger.error("Error sending welcome message", e);
        }
    }

    private void sendBalanceMessage(long chatId, int balance) {
        logger.info("Sending balance message to chatId: {} with balance: {}", chatId, balance);
        
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(String.format("Ваш текущий баланс: %d монет", balance));

        try {
            execute(message);
            logger.info("Balance message sent successfully");
        } catch (TelegramApiException e) {
            logger.error("Error sending balance message", e);
        }
    }
} 
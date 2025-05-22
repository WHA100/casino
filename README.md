# Super Casino

Проект онлайн-казино с Telegram ботом и веб-интерфейсом.

## Структура проекта

```
super-casino/
├── backend/                 # Java Spring Boot приложение
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── casino/
│   │   │   │           ├── CasinoApplication.java
│   │   │   │           ├── bot/
│   │   │   │           │   └── CasinoBot.java
│   │   │   │           └── config/
│   │   │   │               └── WebConfig.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   └── pom.xml
├── frontend/               # Веб-приложение
│   ├── index.html
│   ├── styles.css
│   ├── app.js
│   └── render.yaml
├── .gitignore
└── README.md
```

## Требования

- Java 17
- Maven
- PostgreSQL
- Node.js (опционально, для разработки frontend)

## Установка и запуск

### Backend

1. Настройте переменные окружения:
   ```bash
   export BOT_USERNAME=your_bot_username
   export BOT_TOKEN=your_bot_token
   ```

2. Настройте базу данных PostgreSQL:
   - Создайте базу данных `casino`
   - Обновите настройки в `application.yml`

3. Запустите приложение:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

### Frontend

Frontend размещен на render.com и автоматически деплоится при пуше в репозиторий.

## Разработка

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/your-username/super-casino.git
   ```

2. Создайте новую ветку для разработки:
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. Внесите изменения и создайте pull request

## Лицензия

MIT 
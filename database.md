# Архитектура базы данных для "Суперказик"

## Основные сущности и таблицы

### 1. Пользователь (users)
- id (SERIAL PRIMARY KEY) — уникальный идентификатор пользователя
- telegram_id (BIGINT UNIQUE NOT NULL) — Telegram ID пользователя
- username (VARCHAR) — имя пользователя в Telegram
- balance (INTEGER NOT NULL DEFAULT 1000) — текущий баланс пользователя
- created_at (TIMESTAMP NOT NULL DEFAULT NOW()) — дата регистрации

### 2. История игр (game_history)
- id (SERIAL PRIMARY KEY)
- user_id (INTEGER NOT NULL, FOREIGN KEY -> users.id)
- bet_amount (INTEGER NOT NULL) — сумма ставки
- result (VARCHAR NOT NULL) — результат броска ("Орел" или "Решка")
- win (BOOLEAN NOT NULL) — выиграл ли пользователь
- balance_before (INTEGER NOT NULL) — баланс до игры
- balance_after (INTEGER NOT NULL) — баланс после игры
- played_at (TIMESTAMP NOT NULL DEFAULT NOW()) — время игры

## Связи
- Один пользователь может иметь много записей в истории игр (связь один-ко-многим: users.id -> game_history.user_id)

## Пример схемы (SQL)

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    telegram_id BIGINT UNIQUE NOT NULL,
    username VARCHAR,
    balance INTEGER NOT NULL DEFAULT 1000,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
и
CREATE TABLE game_history (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    bet_amount INTEGER NOT NULL,
    result VARCHAR NOT NULL,
    win BOOLEAN NOT NULL,
    balance_before INTEGER NOT NULL,
    balance_after INTEGER NOT NULL,
    played_at TIMESTAMP NOT NULL DEFAULT NOW()
);
```

## Описание
- Таблица `users` хранит данные о каждом пользователе и его балансе.
- Таблица `game_history` фиксирует каждую игру: ставку, результат, изменение баланса и время. 
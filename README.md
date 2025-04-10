# Shelter Donate App

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)

Приложение для управления пожертвованиями приютам для животных. Позволяет пользователям регистрироваться, совершать пожертвования и просматривать информацию. Администраторы имеют расширенные права для управления системой.

## Функционал
- Регистрация и аутентификация пользователей
- Административная панель
- Интеграция с приютами
- Управление пользователями, приютами и городами (для администраторов расширенные права доступа)
- Система пожертвований
- Просмотр баланса пользователей

## Технологии
- Backend: Java 17, Spring Boot 3.x
- База данных: H2/PostgreSQL/flyway
- Безопасность: Spring Security
- Сборка: Gradle

## Установка
1. Клонировать репозиторий:
```bash
git clone https://github.com/OnepunchZone/Shelter-Donate-App.git
```
2. Настроить базу данных в application.yaml:
```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:file:./database;MODE=PostgreSQL
    username: sa // по умолчанию
    password:    // пусто по умолчанию
```
+ После запуска приложения создаются таблицы и некоторые заполняются. Работа с БД через h2-консоль по адресу http://localhost:8289/h2-console
3. Запустить приложение:
```bash
./gradlew bootRun
```
## API Endpoints
### Аутентификация и пользователи
|Метод	|Путь	|Описание	|Доступ|
|-------|-----|---------|------|
| POST	| /api/v1/auth/login | Вход в систему | Все |
| POST	| /api/v1/auth/new-user |	Регистрация нового пользователя | Все |
| GET	| /api/v1/auth/all | Получить список всех пользователей | ADMIN |
| GET	| /api/v1/auth/{id} |	Получить пользователя по ID |	ADMIN |
### Города
|Метод	|Путь	|Описание	|Доступ|
|-------|-----|---------|------|
| POST	| /api/v1/cities/new-city |	Добавление нового города | ADMIN |
| GET	| /api/v1/cities/all | Получить список всех городов | Все |
| GET	| /api/v1/cities/{id} |	Получить город по ID |	Все (зарег и авториз) |
### Приюты
|Метод	|Путь	|Описание	|Доступ|
|-------|-----|---------|------|
| POST	| /api/v1/shelters/new-shelter |	Добавление нового приюта | ADMIN |
| GET	| /api/v1/shelters/all | Получить список всех приютов | Все |
| GET	| /api/v1/shelters/{id} |	Получить приют по ID |	Все (зарег и авториз) |
### Пожертвования
|Метод	|Путь	|Описание	|Доступ|
|-------|-----|---------|------|
| POST	| /api/v1/donations/new-donate/{id} |	Пожертвование пользователя(по id)на счёт приюта или города по их id | Все (зарег и авториз) |
| GET	| /api/v1/donations/all | Получить список всех донатов | Все |
| GET	| /api/v1/donations/{id} |	Посмотреть иформацию о донате по ID | ADMIN |

## Примеры запрсов
#### Города
1. Получить все города
  - Метод: GET
  - URL: http://localhost:8289/api/v1/cities/all
  - Headers: (нет)
2. Получить город по ID
  - Метод: GET
  - URL: http://localhost:8289/api/v1/cities/{id}
  - Пример: http://localhost:8289/api/v1/cities/1
  - Headers: (нет)
3. Добавить новый город (только для ADMIN)
  - Метод: POST
  - URL: http://localhost:8289/api/v1/cities/new-city
  - Headers:
    Content-Type: application/json
  - Body (raw, JSON):
```json
{
  "name": "Новый город",
  "accountNumber": "1234567890"
}
```
#### Приюты
1. Получить все приюты
  - Метод: GET
  - URL: http://localhost:8289/api/v1/shelters/all
  - Headers: (нет)
2. Получить приют по ID
  - Метод: GET
  - URL: http://localhost:8289/api/v1/shelters/{id}
  - Пример: http://localhost:8289/api/v1/shelters/1
  - Headers: (нет)
3. Добавить новый приют (только для ADMIN)
  - Метод: POST
  - URL: http://localhost:8289/api/v1/shelters/new-shelter
  - Headers:
    Content-Type: application/json
  - Body (raw, JSON):
```json
{
  "name": "Новый приют",
  "accountNumber": "0987654321",
  "cityId": 1
}
```
#### Донаты
1. Получить все пожертвования
 - Метод: GET
 - URL: http://localhost:8289/api/v1/donations/all
 - Headers: (нет)
2. Получить пожертвование по ID (только для ADMIN)
 - Метод: GET
 - URL: http://localhost:8289/api/v1/donations/{id}
 - Пример: http://localhost:8289/api/v1/donations/1
3. Создать новое пожертвование
 - Метод: POST
 - URL: http://localhost:8289/api/v1/donations/new-donate/{userId}
 - Пример: http://localhost:8289/api/v1/donations/new-donate/1
 - Headers:
   Content-Type: application/json
 - Body (raw, JSON):
Для пожертвования приюту:
```json
{
  "amount": 100.50,
  "shelterId": 1
}
```
  Для пожертвования городу:
```json
{
  "amount": 200.75,
  "cityId": 1
}
```
#### Пользователи
1. Получить всех пользователей (только для ADMIN)
 - Метод: GET
 - URL: http://localhost:8289/api/v1/auth/all
2. Получить пользователя по ID (только для ADMIN)
 - Метод: GET
 - URL: http://localhost:8289/api/v1/auth/{id}
 - Пример: http://localhost:8289/api/v1/auth/1
3. Добавить нового пользователя
 - Метод: POST
 - URL: http://localhost:8289/api/v1/auth/new-user
 - Headers:
   Content-Type: application/json
 - Body (raw, JSON):
```json
{
  "username": "newuser",
  "password": "password123",
  "balance": 1000.00,
  "role": "ROLE_USER"
}
```
## Настройки безопасности
 - Все эндпоинты доступные аутентификации
   + "/api/v1/auth/**"
   + "/api/v1/cities/all"
   + "/api/v1/shelters/all"
   + "/api/v1/donations/all"
 - Пароли хранятся в зашифрованном виде (BCrypt)
 - Ролевая модель доступа (USER/ADMIN)

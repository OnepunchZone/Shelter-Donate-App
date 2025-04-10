INSERT INTO cities (name, account_number, balance) VALUES
('Москва', 'CITY-MOSCOW-001', 10000.00),
('Челябинск', 'CITY-CHEL-002', 5000.00);

INSERT INTO shelters (name, account_number, balance, city_id) VALUES
('Приют "Хвостик и лапки"', 'SHELTER-001', 2000.00, 1),
('Приют "Делай добро"', 'SHELTER-002', 1500.00, 1),
('Приют "Пушистик"', 'SHELTER-003', 3000.00, 2);
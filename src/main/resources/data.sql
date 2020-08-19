--Заполнение таблицы "Пользователи"
INSERT INTO TBL_USERS(first_name, last_name, email) VALUES
    ('Dan', 'Vega', 'danvega@gmail.com'),
    ('Jen', 'Vega', 'jenvega@gmail.com'),
    ('Bella', 'Vega', 'bellavega@gmail.com');

--Таблица "Аккаунт пользователя"
INSERT INTO TBL_SBERACCOUNT(account_name, cash) VALUES
('Аккаунт №1',500.00),
('Аккаунт №2', 1000.85),
('Аккаунт №3', 830.65),
('Аккаунт №4', 23.54),
('Аккаунт №5', 25.95);


INSERT INTO TBL_SBERCLIENT(user_id, account_id) VALUES
((SELECT u.ID FROM TBL_USERS u WHERE u.EMAIL = 'danvega@gmail.com'), (SELECT sa.ID FROM TBL_SBERACCOUNT sa WHERE sa.ACCOUNT_NAME = 'Аккаунт №1')),
((SELECT u.ID FROM TBL_USERS u WHERE u.EMAIL = 'jenvega@gmail.com'), (SELECT sa.ID FROM TBL_SBERACCOUNT sa WHERE sa.ACCOUNT_NAME = 'Аккаунт №2')),
((SELECT u.ID FROM TBL_USERS u WHERE u.EMAIL = 'bellavega@gmail.com'), (SELECT sa.ID FROM TBL_SBERACCOUNT sa WHERE sa.ACCOUNT_NAME = 'Аккаунт №3')),
((SELECT u.ID FROM TBL_USERS u WHERE u.EMAIL = 'jenvega@gmail.com'), (SELECT sa.ID FROM TBL_SBERACCOUNT sa WHERE sa.ACCOUNT_NAME = 'Аккаунт №1')),
((SELECT u.ID FROM TBL_USERS u WHERE u.EMAIL = 'bellavega@gmail.com'), (SELECT sa.ID FROM TBL_SBERACCOUNT sa WHERE sa.ACCOUNT_NAME = 'Аккаунт №1'));

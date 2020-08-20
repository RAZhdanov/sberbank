# sberbank

Функции сервиса:

- Получить всех Клиентов: **GET /api/v1/clients**
- Получить все Счета: **GET /api/v1/accounts**
- Получить все Счета Клиента: **GET /api/v1/clients/{clientId}/accounts**
- Получить Клиента по id: **GET /api/v1/clients/{id}**
- Получить Счет по id: **GET /api/v1/accounts/{id}**
- Перевести деньги со Счета A на Счет B: **POST /api/v1/accounts/sendMoney/{sourceAccountId}/{targetAccountId}/{amount}**
- Получить все транзакции по Счету: **GET /api/v1/accounts/{accountId}/transactions**
- Cкомпилированный jar-файл находится в build/libs и работает автономно без дополнительных настроек окружения
- Юнит тесты на сервис перевода средств написаны
- Swagger для визуализации API подключен: **http://[host]:[port]/swagger-ui.html**

- В задание это не входило, но также реализовал HATEOAS линки

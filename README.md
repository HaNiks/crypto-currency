# Crypto Currency watcher

Написать простой REST-сервис просмотра котировок криптовалют

## Функциональность

* Просмотр списка доступных криптовалют (REST-метод)
* Просмотр актуальной цены для указаной криптовалюты (REST-метод - код валюты передается пользователем)
* Записать в лог сообщение о изменении цены более чем на 1%. Для это пользователь регестрирует себя с помощью
  REST-метода notify и передает свое имя(username) и код криптовалюты(symbol). В момент регистрации cохраняяется текущаю
  цена указаной криптовалюты с привязкой к пользоватлю. Как только актуальная цена для указаной валюты поменялась более
  чем на 1%., в лог сервера выводится сообщение уровня WARN в котором указан: код валюты, имя пользователя и процент
  изменения цены с момента регистрации.

## Требования

* Вы можете использовать Java или Kotlin (любой версии)
* Используте Spring или Spring Boot (можно использовать https://start.spring.io/ для ускорения)
* Актуальные цены храните в реляционной базе - можно использовать любую (H2, Mysql, Postgres,…)
* Список доступных криптовалют предопределен и является частью конфигурации сервера
  Список валют:
  [ {“id”:”90”,”symbol”:”BTC”}, {“id”:”80”,”symbol”:”ETH”}, {“id”:”48543”,”symbol”:”SOL”} ]
* Раз в минуту актуальные цены для доступных криптовалют запрашиваются c внешнего источника CoinLore и сохраняются в
  базу данных.
* Что бы получить актуальные цену по коду криптовалюты используйте open API Crypto API | CoinLore
  Method Ticker (Specific Coin): https://api.coinlore.net/api/ticker/?id=90 (BTC)
* Когда пользователь запрашивает актуальную цену для указаной криптовалюты - данные должны быть получены из базы данных
* опубликуйте свою работу на github как публичный проект и скинте на ссылку по почте

##### Использовано:
* Серверный framework: Finagle (построенный на основе Netty)
* База данных (ставится отдельно): Apache Cassandra (DataStax Community Edition) - (DataStax Java 2.1 for Apache Cassandra)

Формат запроса (JSON):
```
{
  "type": "PING",
  "userId": "1e7b05a0-0285-48ea-885e-5cc3b56fe05f"
}
```


======

##### Дополнительно планировалось:
* Легковесное DI - Dagger (http://google.github.io/dagger/)

Более удобное использование сервисов для работы с БД

======

##### Опционально:
* Слой бизнес логики - основная манипуляция данными

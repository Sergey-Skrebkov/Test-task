# Test-task

## Тестовое задание
Приложение написано при использование фреймверка spring 3.2.1 и языка java 17 в качестве сборщика приложения использовал maven

## Подготовка

Установите:

- [docker-desktop](https://www.docker.com) - инструмент для управления несколькими контейнерами.

## Запуск

### Запуск docker-compose
```
docker-compose up
```

### Для выполнения запроса обратитесь на адрес методом POST
```http request
http://localhost:8080/documents/checkDocument
```
В теле запроса добавьте JSON
```json
{
  "document" :{
    "products" : [
      {
        "name" : "Товар1",
        "count" : 6,
        "tags" : ["C"]
      },
      {
        "name" : "Товар2",
        "count" : 2,
        "tags" : ["B","A"]
      },
      {
        "name" : "Товар1",
        "count" : 3,
        "tags" : ["A"]
      }
    ]
  },

  "tags" : [
    {
      "tag" : "A",
      "count" : 2
    },
    {
      "tag" : "B",
      "count" : 2
    },
    {
      "tag" : "C",
      "count" : 6
    }
  ]
}
```

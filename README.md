Данный проект - аналог асинхронной очереди.
Приложение запускает Socket и ждет клиентов.
Клиенты могут быть двух типов: отправители (publisher), получатели (subscriber).

 - В качестве клиента используется cURL. https://curl.se/download.html

 - В качестве протокола используется HTTP. 
===========================================================================

Отправитель посылает запрос на добавление данных с указанием очереди (weather) и значением параметра (temperature=18). Сообщение помещается в конец очереди. 
Если очереди нет в сервисе, то нужно создать новую и поместить в нее сообщение.

Получатель посылает запрос на получение данных с указанием очереди. Сообщение забирается из начала очереди и удаляется.
Если в очередь приходят несколько получателей, то они поочередно получают сообщения из очереди.
Каждое сообщение в очереди может быть получено только одним получателем.

Примеры запросов.

POST-запрос должен добавить элементы в очередь weather.
curl -X POST -d "temperature=18" http://localhost:9000/queue/weather
queue указывает на режим «очередь».
weather указывает на имя очереди.

GET-запрос должен получить элементы из очереди weather.
curl -X GET http://localhost:9000/queue/weather
Ответ: temperature=18

# week4Chat

Неделя 4: Работа со списками, RecyclerView, DiffUtil, pull to refresh,
Paging

![image](https://user-images.githubusercontent.com/77270310/191953538-110fd70b-9024-4c3e-a1cd-2ee2058ac598.png)

1.0. Создать новое приложение

1.1. Реализовать список чатов

1.2. Сделать класс Repository который будет поставлять список чатов (сам
список и данные в ранее загруженном списке, должны как-то меняться при
каждом запросе; цель смоделировать появление новых чатов в списке и
смоделировать изменение (например кол-ва непрочитанных сообщений) уже
в ранее загруженных чатах, наполнять можно рандомными данными)
Дополнительно:

1.2.1. В классе Repository реализовать функцию которая будет где-то брать данные и передавать в качестве результата

1.2.2. Эта функция должна моделировать поведение бэкенда, как буд-то мы
делаем запрос на сервер, и с него получаем список реальных чатов (Использована библиотека Faker https://github.com/serpro69/kotlin-faker)

1.2.3. Список чатов хранящийся на сервере периодически меняется, это
следует реализовать при моделировании 

1.2.4. Содержимое данных в объекте чата (ChatData) так же периодически
меняются, это следует реализовать при моделировании getChats()

1.3. Добавить элемент для обновления списка - swipe to refresh

1.4. Использовать DiffUtil для обновления списка

1.5 Загружать данные из репозитория частями по 10 штук. Если
пользователь доскроллил до конца загруженного списка, загрузить
следующую порцию, добавить в конец списка

1.6. По нажатию на чат - открывать экран чата

- за образец взять один из мессенджеров которым пользуетесь, и сделать
максимально похожим образом
- делайте так как буд-то вы делаете реальный messenger

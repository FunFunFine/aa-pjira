# AA: pJIRA 
(p for popug)

## Week zero
<img width="898" alt="image" src="https://user-images.githubusercontent.com/31823086/193460574-16275dcc-075b-4ae0-84e9-63bd08e61ce6.png">

## Week one

> Разобрать каждое требование на составляющие (актор, команда, событие, query). Определить, как все бизнес цепочки будут выглядеть и на какие шаги они будут разбиваться.
> Построить модель данных для системы и модель доменов.

<img width="820" alt="image" src="https://user-images.githubusercontent.com/31823086/193464519-029fea1d-d589-4f15-b602-1b669e88911f.png">



Данные:
- User (попуг или менеджер)
- Account (для каждого отдельного попуга и один для менеджмента (не уверен, но пока ладно))
- Task 
- Payment 

> Определить, какие общие данные нужны для разных доменов и как связаны данные между разными доменами.

User <-> Account через popugId/managerId 
Task <-> Payment через стоимость таски

> Разобраться, какие сервисы, кроме тудушника, будут в нашей системе и какие между ними могут быть связи

Сервисы:
- Авторизация
- Трекер
- Аккаунтинг

Аккаунтинг асинхронно получает апдейты от Трекера
Трекер синхронно получает авторизационные данные по юзеру от Авторизации

> Определить все бизнес события, необходимые для работы системы. Отобразить кто из сервисов является продьюсером, а кто консьюмером бизнес событий.

- Task assigned to popug
  - produced by Tracker
- Task set as completed
  - produced by Tracker
  - consumed by Accounting
- New task created
  - produced by Tracker
- Tasks shuffled 
  - produced by Tracker
- Money paid out
  - produced by Accounting
- Money taken \[from account\]
  - produced by Accounting
- Money received \[to account\]
  - produced by Accounting

> Выписать все CUD события и какие данные нужны для этих событий, которые необходимы для работы системы. Отобразить кто из сервисов является продьюсером, а кто консьюмером CUD событий.

- а вот нет их у меня чего-то
- почему — не пойму
- 🥲

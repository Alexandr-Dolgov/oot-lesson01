# OrderFlow — демо занятия 1

Код к колоде `slides/01-ответственность-и-абстракция-v2.md`. JDK 21, Maven, JUnit 5, AssertJ.
В IDEA: File → Open → `demo/lesson01/pom.xml` → Open as Project.

| Пакет | Что это |
|---|---|
| `legacy` | Стартовое состояние: `OrderService` на `String`/`double`, `Object[]` в `HashMap`, без тестов |
| `domain` | Эталон после демо: `Money`, `OrderId`, `Email`, `Customer`, `OrderItem`, `Order`, тарифы доставки |
| `app` | Сценарий `PlaceOrder`, порты `OrderRepository` и `Notifier`, новый `Main` |
| `infra` | `InMemoryOrderRepository`, `ConsoleNotifier` |

`domain`, `app` и `infra` — шпаргалка. На занятии их удобно убрать (например, в отдельную ветку) и писать заново по шагам.

## Сценарий демо

| Шаг | Слайд | Что делаем | Что показать |
|---|---|---|---|
| 1 | «Найти всё, что знает о доставке» | `Ctrl+Shift+F` → `"express"` в `legacy` | 4 места в `OrderService` + `Main`; компилятор ни одно не подскажет |
| 2 | «Воспроизвести баг» | запустить `legacy.Main`, точка останова в `pay()` | оплата 3594.70 отклонена: `3490 * 1.03 = 3594.7000000000003` |
| 3 | «Выделить `Money`» | `Money` как `record`, `MoneyTest` | `new Money(-500)` бросает исключение; `equals` бесплатно |
| 4 | «Собрать `Order` и сломать его» | сначала `this.items = items`, тест чистит `items()`, затем `List.copyOf` | `private` не спас инвариант; `pay()`/`ship()` — единственные точки мутации |
| 5 | «Сценарий и фальшивый нотификатор» | `PlaceOrder`, `RecordingNotifier`, `PlaceOrderTest` | красный тест без вызова `notifier`, затем зелёный |
| 6 | «Добавить самовывоз» | `Pickup implements DeliveryTariff`, тест в `OrderTest` | `git diff`: только новые файлы, старые тесты зелёные |

Ожидаемый вывод `legacy.Main` (фрагмент):

```text
Ошибка: оплата 3594.7 не совпадает с суммой заказа 3594.7000000000003
Заказов: 3, экспресс: 1, выручка: 62481.0 руб.
```

## Что намеренно не перенесено в домен

Страховка, промокоды, отчёт, сроки в уведомлениях. Это материал для задачи 2 (аудит) и домашней работы.

## Запуск

```powershell
mvn test        # все тесты
```

`legacy.Main` и `app.Main` запускаются из IDEA: Run на методе `main`.

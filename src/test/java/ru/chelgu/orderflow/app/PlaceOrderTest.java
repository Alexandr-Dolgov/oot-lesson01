package ru.chelgu.orderflow.app;

import org.junit.jupiter.api.Test;
import ru.chelgu.orderflow.domain.Customer;
import ru.chelgu.orderflow.domain.CustomerId;
import ru.chelgu.orderflow.domain.Email;
import ru.chelgu.orderflow.domain.ExpressDelivery;
import ru.chelgu.orderflow.domain.Order;
import ru.chelgu.orderflow.domain.OrderItem;
import ru.chelgu.orderflow.infra.InMemoryOrderRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.chelgu.orderflow.domain.Money.rubles;

class PlaceOrderTest {

    /** Фальшивый нотификатор: ничего не отправляет, только запоминает. */
    static class RecordingNotifier implements Notifier {
        final List<Order> notified = new ArrayList<>();

        @Override
        public void orderPlaced(Customer customer, Order order) {
            notified.add(order);
        }
    }

    private final Customer anna = new Customer(new CustomerId("c-1"), "Анна",
            new Email("anna@example.ru"), "+79000000001");

    @Test
    void заказ_сохранён_и_клиент_уведомлён() {
        var repository = new InMemoryOrderRepository();
        var notifier = new RecordingNotifier();
        var placeOrder = new PlaceOrder(repository, notifier);

        Order order = placeOrder.place(anna,
                List.of(new OrderItem("Ноутбук", 1, rubles(64990))), new ExpressDelivery());

        assertThat(repository.find(order.id())).contains(order);
        assertThat(notifier.notified).containsExactly(order);
    }
}

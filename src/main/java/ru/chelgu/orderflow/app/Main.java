package ru.chelgu.orderflow.app;

import ru.chelgu.orderflow.domain.CourierDelivery;
import ru.chelgu.orderflow.domain.Customer;
import ru.chelgu.orderflow.domain.CustomerId;
import ru.chelgu.orderflow.domain.Email;
import ru.chelgu.orderflow.domain.Order;
import ru.chelgu.orderflow.domain.OrderItem;
import ru.chelgu.orderflow.domain.Pickup;
import ru.chelgu.orderflow.infra.ConsoleNotifier;
import ru.chelgu.orderflow.infra.InMemoryOrderRepository;

import java.util.List;

import static ru.chelgu.orderflow.domain.Money.rubles;

/** Сценарий legacy.Main после рефакторинга. Здесь, и только здесь, собирается приложение. */
public class Main {

    public static void main(String[] args) {
        PlaceOrder placeOrder = new PlaceOrder(new InMemoryOrderRepository(), new ConsoleNotifier());

        Customer anna = new Customer(new CustomerId("c-1"), "Анна",
                new Email("anna@example.ru"), "+79000000001");
        List<OrderItem> items = List.of(
                new OrderItem("Чайник", 1, rubles(2490)),
                new OrderItem("Кружка", 2, rubles(350)));

        Order byCourier = placeOrder.place(anna, items, new CourierDelivery());
        byCourier.pay();
        byCourier.ship();

        Order byPickup = placeOrder.place(anna, items, new Pickup());

        System.out.println(byCourier);
        System.out.println(byPickup);
    }
}

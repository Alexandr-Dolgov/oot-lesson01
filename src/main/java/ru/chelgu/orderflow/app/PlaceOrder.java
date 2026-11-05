package ru.chelgu.orderflow.app;

import ru.chelgu.orderflow.domain.Customer;
import ru.chelgu.orderflow.domain.DeliveryTariff;
import ru.chelgu.orderflow.domain.Order;
import ru.chelgu.orderflow.domain.OrderItem;

import java.util.List;

/** Сценарий «оформить заказ». Зависимости приходят снаружи, через конструктор. */
public final class PlaceOrder {

    private final OrderRepository orders;
    private final Notifier notifier;

    public PlaceOrder(OrderRepository orders, Notifier notifier) {
        this.orders = orders;
        this.notifier = notifier;
    }

    public Order place(Customer customer, List<OrderItem> items, DeliveryTariff delivery) {
        Order order = new Order(orders.nextId(), customer.id(), items, delivery);
        orders.save(order);
        notifier.orderPlaced(customer, order);
        return order;
    }
}

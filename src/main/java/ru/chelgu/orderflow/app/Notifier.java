package ru.chelgu.orderflow.app;

import ru.chelgu.orderflow.domain.Customer;
import ru.chelgu.orderflow.domain.Order;

/** Как сообщить клиенту — SMS, почта, Telegram, — решает реализация. */
public interface Notifier {

    void orderPlaced(Customer customer, Order order);
}

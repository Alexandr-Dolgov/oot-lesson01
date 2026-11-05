package ru.chelgu.orderflow.app;

import ru.chelgu.orderflow.domain.Order;
import ru.chelgu.orderflow.domain.OrderId;

import java.util.Optional;

/** Где хранятся заказы, решает тот, кто собирает приложение, а не {@link PlaceOrder}. */
public interface OrderRepository {

    OrderId nextId();

    void save(Order order);

    Optional<Order> find(OrderId id);
}

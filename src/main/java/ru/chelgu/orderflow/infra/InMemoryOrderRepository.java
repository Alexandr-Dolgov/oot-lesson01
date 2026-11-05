package ru.chelgu.orderflow.infra;

import ru.chelgu.orderflow.app.OrderRepository;
import ru.chelgu.orderflow.domain.Order;
import ru.chelgu.orderflow.domain.OrderId;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class InMemoryOrderRepository implements OrderRepository {

    private final Map<OrderId, Order> orders = new LinkedHashMap<>();
    private int counter = 0;

    @Override
    public OrderId nextId() {
        counter++;
        return new OrderId("ORD-" + counter);
    }

    @Override
    public void save(Order order) {
        orders.put(order.id(), order);
    }

    @Override
    public Optional<Order> find(OrderId id) {
        return Optional.ofNullable(orders.get(id));
    }
}

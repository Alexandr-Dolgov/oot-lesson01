package ru.chelgu.orderflow.domain;

import java.util.List;
import java.util.Objects;

/**
 * Заказ — сущность: равенство по идентификатору, состояние меняется.
 *
 * <p>Инварианты:
 * <ul>
 *   <li>в заказе есть хотя бы одна позиция;</li>
 *   <li>статус движется только вперёд: NEW → PAID → SHIPPED;</li>
 *   <li>отправленный заказ отменить нельзя.</li>
 * </ul>
 */
public final class Order {

    private final OrderId id;
    private final CustomerId customer;
    private final List<OrderItem> items;
    private final DeliveryTariff delivery;
    private OrderStatus status = OrderStatus.NEW;

    public Order(OrderId id, CustomerId customer, List<OrderItem> items, DeliveryTariff delivery) {
        this.id = Objects.requireNonNull(id);
        this.customer = Objects.requireNonNull(customer);
        this.delivery = Objects.requireNonNull(delivery);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Заказ не может быть пустым");
        }
        this.items = List.copyOf(items);
    }

    public Money goods() {
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(Money.ZERO, Money::plus);
    }

    public Money total() {
        Money goods = goods();
        return goods.plus(delivery.cost(goods));
    }

    public void pay() {
        requireStatus(OrderStatus.NEW);
        status = OrderStatus.PAID;
    }

    public void ship() {
        requireStatus(OrderStatus.PAID);
        status = OrderStatus.SHIPPED;
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Отправленный заказ отменить нельзя: " + id);
        }
        status = OrderStatus.CANCELLED;
    }

    private void requireStatus(OrderStatus expected) {
        if (status != expected) {
            throw new IllegalStateException(
                    "Заказ " + id + " в статусе " + status + ", ожидался " + expected);
        }
    }

    public OrderId id() {
        return id;
    }

    public CustomerId customer() {
        return customer;
    }

    public List<OrderItem> items() {
        return items;
    }

    public DeliveryTariff delivery() {
        return delivery;
    }

    public OrderStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Order other && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Order[" + id + ", " + status + ", " + total() + "]";
    }
}

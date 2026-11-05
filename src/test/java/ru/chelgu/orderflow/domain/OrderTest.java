package ru.chelgu.orderflow.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.chelgu.orderflow.domain.Money.rubles;

class OrderTest {

    private final OrderId id = new OrderId("ORD-1");
    private final CustomerId customer = new CustomerId("c-1");
    private final List<OrderItem> items = List.of(
            new OrderItem("Чайник", 1, rubles(2490)),
            new OrderItem("Кружка", 2, rubles(350)));

    @Test
    void пустой_заказ_создать_нельзя() {
        assertThatThrownBy(() -> new Order(id, customer, List.of(), new CourierDelivery()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("пустым");
    }

    @Test
    void сумма_складывается_из_товаров_и_доставки() {
        var order = new Order(id, customer, items, new CourierDelivery());

        assertThat(order.total()).isEqualTo(rubles(3490));
    }

    @Test
    void самовывоз_бесплатный() {
        var order = new Order(id, customer, items, new Pickup());

        assertThat(order.total()).isEqualTo(rubles(3190));
    }

    @Test
    void позиции_нельзя_удалить_снаружи() {
        var order = new Order(id, customer, items, new CourierDelivery());

        assertThatThrownBy(() -> order.items().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void изменение_исходного_списка_не_меняет_заказ() {
        var source = new ArrayList<>(items);
        var order = new Order(id, customer, source, new CourierDelivery());

        source.clear();

        assertThat(order.items()).hasSize(2);
    }

    @Test
    void неоплаченный_заказ_отправить_нельзя() {
        var order = new Order(id, customer, items, new CourierDelivery());

        assertThatThrownBy(order::ship)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void отправленный_заказ_отменить_нельзя() {
        var order = new Order(id, customer, items, new CourierDelivery());
        order.pay();
        order.ship();

        assertThatThrownBy(order::cancel)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void заказы_равны_по_идентификатору() {
        var a = new Order(id, customer, items, new CourierDelivery());
        var b = new Order(id, customer, items, new Pickup());

        assertThat(a).isEqualTo(b);
    }
}

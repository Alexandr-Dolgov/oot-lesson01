package ru.chelgu.orderflow.infra;

import ru.chelgu.orderflow.app.Notifier;
import ru.chelgu.orderflow.domain.Customer;
import ru.chelgu.orderflow.domain.Money;
import ru.chelgu.orderflow.domain.Order;

/** Правило поддержки из legacy: крупные заказы — по SMS, остальные — письмом. */
public final class ConsoleNotifier implements Notifier {

    private static final Money SMS_FROM = Money.rubles(10_000);

    @Override
    public void orderPlaced(Customer customer, Order order) {
        String text = customer.name() + ", заказ " + order.id() + " принят. Сумма: " + order.total();
        if (order.total().compareTo(SMS_FROM) > 0) {
            System.out.println("[SMS " + customer.phone() + "] " + text);
        } else {
            System.out.println("[EMAIL " + customer.email() + "] " + text);
        }
    }
}

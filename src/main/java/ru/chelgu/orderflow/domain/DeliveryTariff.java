package ru.chelgu.orderflow.domain;

/**
 * Тариф доставки: сколько стоит и сколько дней везти.
 *
 * <p>Новый тариф — новый класс; {@link Order} при этом не меняется.
 * Подробно о полиморфизме — на занятии 2.
 */
public interface DeliveryTariff {

    Money cost(Money goods);

    int days();
}

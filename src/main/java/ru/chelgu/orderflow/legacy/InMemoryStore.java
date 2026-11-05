package ru.chelgu.orderflow.legacy;

import java.util.LinkedHashMap;
import java.util.Map;

/** «База данных» стартового проекта: строки заказов по идентификатору. */
class InMemoryStore {
    final Map<String, Object[]> orders = new LinkedHashMap<>();
}

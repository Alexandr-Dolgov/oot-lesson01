package ru.chelgu.orderflow.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.chelgu.orderflow.domain.Money.rubles;

class MoneyTest {

    @Test
    void double_теряет_копейки() {
        assertThat(3490 * 1.03).isNotEqualTo(3594.70);
    }

    @Test
    void money_не_теряет_копейки() {
        assertThat(rubles(3490).plus(new Money(10_470))).isEqualTo(new Money(359_470));
    }

    @Test
    void отрицательную_сумму_создать_нельзя() {
        assertThatThrownBy(() -> new Money(-500))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void равенство_по_значению() {
        assertThat(rubles(100)).isEqualTo(new Money(10_000));
    }
}

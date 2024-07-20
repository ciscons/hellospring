package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.math.BigDecimal.valueOf;

public class PaymentTest {
    @Test
    @DisplayName("Payment 객체 생성 테스트")
    void createPrepared() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L, "USD", BigDecimal.TEN, valueOf(1_000), LocalDateTime.now(clock)
        );

        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));
        Assertions.assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    @DisplayName("시간이 아직 유효한지 테스트")
    void isValid() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
                1L, "USD", BigDecimal.TEN, valueOf(1_000), LocalDateTime.now(clock)
        );

        Assertions.assertThat(payment.isValid(clock)).isTrue();
        // 30분을 지연 시킨 후 테스트
        Assertions.assertThat(
                payment.isValid(Clock.offset(clock, Duration.of(30, ChronoUnit.MINUTES)))).isFalse();
    }
}

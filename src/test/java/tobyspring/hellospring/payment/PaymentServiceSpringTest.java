package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestPaymentConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
public class PaymentServiceSpringTest {

    @Autowired PaymentService paymentService;
    @Autowired ExRateProviderStub exRateProviderStub;
    @Autowired Clock clock;

    @Test
    @DisplayName("spring DI를 통해 테스트를 수행한다.")
    void convertedAmount() throws IOException {
        /*
        Spring Container Test
        ContextConfiguration 으로 테스트를 실행할 때 TestObject 클래스의 구성정보를 읽은 다음에 이걸 가지고 spring container를 생성
        beanFactory로 생성된 Bean을 가져올 수 있다.
        Junit으로 Spring의 기능을 이용해서 확장하려면 ExtendWith 어노테이션을 추가해줘야 한다.
        */
//        BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
//        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환율정보 가져온다 -> null이 아닌 값이면 잘 가져다 넣었다고 본다
        // BigDecimal은 금액 계산에 좋지만, isEqualTo로 비교하는 것은 옳지않다. 소수점 이하까지 사용해야 하는 경우 결과가 다르게 나올 수 있음
        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        // exRate : 500
        exRateProviderStub.setExRate(valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));
    }

    @Test
    @DisplayName("환율이 30분동안 유지되는가")
    void validUntil() throws IOException {
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // vaild until의 prepare() 30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }
}

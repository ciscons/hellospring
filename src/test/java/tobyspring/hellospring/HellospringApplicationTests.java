package tobyspring.hellospring;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import tobyspring.hellospring.payment.PaymentService;

@SpringBootTest
@ContextConfiguration(classes = HellospringApplication.class)
class HellospringApplicationTests {

    @Test
    void contextLoads() {
    }

}

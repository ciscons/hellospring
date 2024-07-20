package tobyspring.hellospring.learningTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockTest {
    // Clock을 이용해서 LocalDateTime.now를 가져온다
    // Exception 발생으로 인프런에 질문 남긴 후 주석처리함 
//    @Test
//    void clock() {
//        // 현재 위치한 시간대 기준시간
//        Clock clock = Clock.systemDefaultZone();
//
//        LocalDateTime dt1 = LocalDateTime.now(clock);
//        LocalDateTime dt2 = LocalDateTime.now(clock);
//
//        Assertions.assertThat(dt2).isAfter(dt1);
//    }

    // Clock을 Test에서 사용할 때 원하는 시간을 지정해서 현재 시간을 가져올 수 있게 할 수 있는가?
    @Test
    void fixedClock() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        LocalDateTime dt3 = LocalDateTime.now(clock).plusHours(1);

        Assertions.assertThat(dt2).isEqualTo(dt1);
        Assertions.assertThat(dt3).isEqualTo(dt1.plusHours(1));
    }
}

package tobyspring.hellospring.exrate;

import tobyspring.hellospring.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedExRateProvider implements ExRateProvider {
    private final ExRateProvider target;

    private BigDecimal cachedExRate;
    private LocalDateTime cachedExpiryTime;

    public CachedExRateProvider(ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExRate(String currency)  {
        System.out.println("cachedExRate : " + cachedExRate);
        if (cachedExRate == null || cachedExpiryTime.isBefore(LocalDateTime.now())) {
            cachedExRate = this.target.getExRate(currency);
            cachedExpiryTime = LocalDateTime.now().plusSeconds(3);

            System.out.println("Cache Updated");
        }

        return cachedExRate;
    }
}

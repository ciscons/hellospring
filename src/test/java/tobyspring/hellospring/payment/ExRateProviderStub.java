package tobyspring.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;

public class ExRateProviderStub implements ExRateProvider {
    // 환율 정보를 미리 가지고 있는다
    private BigDecimal exRate;

    public BigDecimal getExRate() {
        return exRate;
    }

    public void setExRate(BigDecimal exRate) {
        this.exRate = exRate;
    }

    public ExRateProviderStub(BigDecimal exRate) {
        this.exRate = exRate;
    }

    @Override
    public BigDecimal getExRate(String currency)  {
        return exRate;
    }
}

package tobyspring.hellospring.exrate;

import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.api.ErApiExRateExtrator;
import tobyspring.hellospring.api.HttpClientApiExecutor;
import tobyspring.hellospring.payment.ExRateProvider;
import java.math.BigDecimal;

public class WebApiExRateProvider implements ExRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.runApiForExRate(url, new HttpClientApiExecutor(), new ErApiExRateExtrator());
    }
}

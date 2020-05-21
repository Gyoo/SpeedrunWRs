package ooo.gyoo.speedrunwrs.api;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SRComRequestInterceptor implements RequestInterceptor {

    final String apiKey;

    public SRComRequestInterceptor(@Value("${wrbot.speedruncom.apiKey}") final String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        requestTemplate.header("Host", "www.speedrun.com");
        requestTemplate.header("Accept", "application/json");
        requestTemplate.header("X-API-Key", this.apiKey);
    }
}

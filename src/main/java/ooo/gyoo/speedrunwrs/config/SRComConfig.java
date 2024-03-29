package ooo.gyoo.speedrunwrs.config;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import ooo.gyoo.speedrunwrs.api.SRComClient;
import ooo.gyoo.speedrunwrs.api.SRComRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SRComConfig {

    @Autowired
    private SRComRequestInterceptor srComRequestInterceptor;

    @Bean
    public SRComClient srCom() {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .requestInterceptor(this.srComRequestInterceptor)
                .target(SRComClient.class, "https://www.speedrun.com/api/v1");
    }

}

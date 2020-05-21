package ooo.gyoo.speedrunwrs.config;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import ooo.gyoo.speedrunwrs.api.SRCom;
import ooo.gyoo.speedrunwrs.api.SRComRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SRComConfig {

    @Autowired
    private SRComRequestInterceptor srComRequestInterceptor;

    @Bean
    public SRCom srCom() {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .requestInterceptor(this.srComRequestInterceptor)
                .target(SRCom.class, "https://www.speedrun.com/api/v1");
    }

}

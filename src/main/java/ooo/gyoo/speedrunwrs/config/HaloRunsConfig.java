package ooo.gyoo.speedrunwrs.config;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import ooo.gyoo.speedrunwrs.api.HaloRunsClient;
import ooo.gyoo.speedrunwrs.api.SRComClient;
import ooo.gyoo.speedrunwrs.api.SRComRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HaloRunsConfig {

    @Bean
    public HaloRunsClient haloRunsClient() {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .target(HaloRunsClient.class, "https://haloruns.z20.web.core.windows.net/content");
    }

}

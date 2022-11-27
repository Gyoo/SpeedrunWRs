package ooo.gyoo.speedrunwrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpeedrunwrsApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SpeedrunwrsApplication.class, args);
    }

}

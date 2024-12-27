package ooo.gyoo.speedrunwrs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfig {

    @Bean(name = "srcomThreadPool")
    public Executor srcomThreadPool(){
        return Executors.newScheduledThreadPool(1);
    }

    @Bean(name = "halorunsThreadPool")
    public Executor halorunsThreadPool(){
        return Executors.newScheduledThreadPool(1);
    }

    @Bean(name = "blueskyThreadPool")
    public Executor blueskyThreadPool(){
        return Executors.newScheduledThreadPool(1);
    }

    @Bean(name = "messageThreadPool")
    public Executor messageThreadPool(){
        return Executors.newScheduledThreadPool(1);
    }

}

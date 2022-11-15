package ooo.gyoo.speedrunwrs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import twitter4j.Twitter;

@Configuration
@Profile("prod")
public class TwitterConfig {

    @Value("${wrbot.twitter.consumerKey}")
    private String consumerKey;

    @Value("${wrbot.twitter.consumerSecret}")
    private String consumerSecret;

    @Value("${wrbot.twitter.accessToken}")
    private String accessToken;

    @Value("${wrbot.twitter.accessTokenSecret}")
    private String accessTokenSecret;

    @Bean
    public Twitter getTwitter() {
        return Twitter.newBuilder()
                .oAuthConsumer(this.consumerKey, this.consumerSecret)
                .oAuthAccessToken(this.accessToken, this.accessTokenSecret)
                .build();
    }

}

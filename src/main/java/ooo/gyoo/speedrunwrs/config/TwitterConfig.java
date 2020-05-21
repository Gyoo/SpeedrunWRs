package ooo.gyoo.speedrunwrs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

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
        final ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(this.consumerKey)
                .setOAuthConsumerSecret(this.consumerSecret)
                .setOAuthAccessToken(this.accessToken)
                .setOAuthAccessTokenSecret(this.accessTokenSecret);
        final TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }

}

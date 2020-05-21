package ooo.gyoo.speedrunwrs.config;

import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class MastodonConfig {

    @Value("${wrbot.mastodon.instanceName}")
    private String instanceName;

    @Value("${wrbot.mastodon.accessToken}")
    private String accessToken;

    @Bean
    public MastodonClient mastodonClient() {
        return new MastodonClient.Builder(this.instanceName, new OkHttpClient.Builder(), new Gson()).accessToken(this.accessToken).build();
    }

}

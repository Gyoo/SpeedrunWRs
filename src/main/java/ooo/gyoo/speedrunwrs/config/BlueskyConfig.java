package ooo.gyoo.speedrunwrs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import work.socialhub.kbsky.BlueskyFactory;
import work.socialhub.kbsky.api.entity.com.atproto.server.ServerCreateSessionRequest;
import work.socialhub.kbsky.auth.BearerTokenAuthProvider;
import work.socialhub.kbsky.domain.Service;

@Configuration
@Profile("prod")
public class BlueskyConfig {

    @Value("${wrbot.bluesky.handle}")
    private String handle;

    @Value("${wrbot.bluesky.password}")
    private String password;

    @Bean
    public BearerTokenAuthProvider bearerTokenAuthProvider() {
        ServerCreateSessionRequest request = new ServerCreateSessionRequest();
        request.setIdentifier(handle);
        request.setPassword(password);
        String accessJwt = BlueskyFactory.INSTANCE.instance(Service.BSKY_SOCIAL.getUri())
                .server()
                .createSession(request).getData().accessJwt;

        return new BearerTokenAuthProvider(accessJwt);
    }

}

package ooo.gyoo.speedrunwrs.model.bluesky;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import work.socialhub.kbsky.BlueskyFactory;
import work.socialhub.kbsky.api.entity.com.atproto.server.ServerCreateSessionRequest;
import work.socialhub.kbsky.auth.BearerTokenAuthProvider;
import work.socialhub.kbsky.domain.Service;

import java.util.concurrent.TimeUnit;

@Component
@Profile("prod")
public class AuthTokenGenerator {

    @Value("${wrbot.bluesky.handle}")
    private String handle;

    @Value("${wrbot.bluesky.password}")
    private String password;

    private BearerTokenAuthProvider token;

    public BearerTokenAuthProvider getToken() {
        return token;
    }

    @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS, initialDelay = 0)
    @Async(value = "blueskyThreadPool")
    public void refresh() {
        ServerCreateSessionRequest request = new ServerCreateSessionRequest();
        request.setIdentifier(handle);
        request.setPassword(password);
        String accessJwt = BlueskyFactory.INSTANCE.instance(Service.BSKY_SOCIAL.getUri())
                .server()
                .createSession(request).getData().accessJwt;
        this.token = new BearerTokenAuthProvider(accessJwt);
    }
}

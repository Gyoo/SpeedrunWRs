package ooo.gyoo.speedrunwrs.service.impl;

import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.entity.Status;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import com.sys1yagi.mastodon4j.api.method.Statuses;
import ooo.gyoo.speedrunwrs.service.SubmitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class MastodonService implements SubmitService {

    static Logger LOGGER = LoggerFactory.getLogger(MastodonService.class);

    private final MastodonClient mastodon;

    @Autowired
    public MastodonService(final MastodonClient mastodon) {
        this.mastodon = mastodon;
    }

    @Override
    public void submit(final String message) {
        final Statuses statuses = new Statuses(this.mastodon);
        try {
            statuses.postStatus(message, null, null, false, null, Status.Visibility.Public).execute();
        } catch (final Mastodon4jRequestException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

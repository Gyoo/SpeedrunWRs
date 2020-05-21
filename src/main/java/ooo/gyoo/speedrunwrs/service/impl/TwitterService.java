package ooo.gyoo.speedrunwrs.service.impl;

import ooo.gyoo.speedrunwrs.service.SubmitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
@Profile("prod")
public class TwitterService implements SubmitService {

    static Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);
    private final Twitter twitter;

    @Autowired
    public TwitterService(final Twitter twitter) {
        this.twitter = twitter;
    }

    @Override
    public void submit(final String message) {
        try {
            this.twitter.updateStatus(message);
        } catch (final TwitterException e) {
            LOGGER.error(e.getMessage());
        }

    }
}

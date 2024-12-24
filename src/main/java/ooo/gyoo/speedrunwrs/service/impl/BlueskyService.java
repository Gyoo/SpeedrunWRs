package ooo.gyoo.speedrunwrs.service.impl;

import ooo.gyoo.speedrunwrs.service.SubmitService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import work.socialhub.kbsky.Bluesky;
import work.socialhub.kbsky.BlueskyFactory;
import work.socialhub.kbsky.api.entity.app.bsky.feed.FeedPostRequest;
import work.socialhub.kbsky.auth.BearerTokenAuthProvider;
import work.socialhub.kbsky.model.app.bsky.embed.EmbedExternal;
import work.socialhub.kbsky.model.app.bsky.embed.EmbedExternalExternal;

import static work.socialhub.kbsky.domain.Service.BSKY_SOCIAL;

@Service
@Profile("prod")
public class BlueskyService implements SubmitService {

    static Logger LOGGER = LoggerFactory.getLogger(BlueskyService.class);

    private final BearerTokenAuthProvider blueskyAuthProvider;
    private final Bluesky bluesky;

    public BlueskyService(BearerTokenAuthProvider blueskyAuthProvider) {
        this.blueskyAuthProvider = blueskyAuthProvider;
        this.bluesky = BlueskyFactory.INSTANCE.instance(BSKY_SOCIAL.getUri());
    }

    @Override
    public void submit(final String title, final String url) {
        FeedPostRequest postRequest = new FeedPostRequest(blueskyAuthProvider);
        postRequest.setText(title);
        EmbedExternal embed = new EmbedExternal();
        EmbedExternalExternal external = new EmbedExternalExternal();
        external.setUri(url);
        external.setDescription(StringUtils.EMPTY);
        external.setTitle(title);
        embed.setExternal(external);
        postRequest.setEmbed(embed);
        this.bluesky.feed().post(postRequest);
    }
}

package ooo.gyoo.speedrunwrs.service.impl;

import ooo.gyoo.speedrunwrs.api.HaloRunsClient;
import ooo.gyoo.speedrunwrs.model.MessageQueue;
import ooo.gyoo.speedrunwrs.model.haloruns.Entry;
import ooo.gyoo.speedrunwrs.model.haloruns.HaloRunsResponse;
import ooo.gyoo.speedrunwrs.model.haloruns.Participant;
import ooo.gyoo.speedrunwrs.utils.DurationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

@Service
public class HaloRunsService {

    static Logger LOGGER = LoggerFactory.getLogger(HaloRunsService.class);
    private final HaloRunsClient haloRunsClient;
    private final MessageQueue messageQueue;

    private final File file;

    private String lastRunId;

    @Autowired
    public HaloRunsService(HaloRunsClient haloRunsClient, final MessageQueue messageQueue) {
        this.haloRunsClient = haloRunsClient;
        this.messageQueue = messageQueue;

        this.file = new File("last-haloruns.txt");
        try {
            if (this.file.exists()) {
                if (!Files.readAllLines(this.file.toPath()).isEmpty()) {
                    this.lastRunId = Files.readAllLines(this.file.toPath()).getFirst();
                } else {
                    this.lastRunId = "";
                }
            } else {
                this.file.createNewFile();
            }
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 0)
    @Async(value = "halorunsThreadPool")
    public void run() {
        try {
            HaloRunsResponse response = haloRunsClient.listRuns();
            for(Entry entry : response.entries()){
                if(StringUtils.equalsIgnoreCase(entry.runId(), lastRunId)) break;
                if(StringUtils.equalsIgnoreCase("Full Game", entry.levelName())){
                    String time = DurationUtils.getTime(entry.duration());
                    final StringBuilder output = new StringBuilder(entry.gameName() + " - " + entry.categoryName()
                            + " in " + time
                            + " by " + entry.participants().stream().map(Participant::username).collect(Collectors.joining(", ")));
                    if (output.length() < 300) {
                        try {
                            this.messageQueue.getQueue().put(Pair.of(output.toString(), "https://haloruns.com" + entry.leaderboardUrl()));
                        } catch (final InterruptedException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                } else {
                    LOGGER.info("Run {} is not full game", entry.runId());
                }
            }
            this.lastRunId = response.entries().getFirst().runId();
            Files.write(this.file.toPath(), this.lastRunId.getBytes());
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}

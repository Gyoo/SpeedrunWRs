package ooo.gyoo.speedrunwrs.service.impl;

import ooo.gyoo.speedrunwrs.api.HaloRunsClient;
import ooo.gyoo.speedrunwrs.model.MessageQueue;
import ooo.gyoo.speedrunwrs.model.haloruns.Entry;
import ooo.gyoo.speedrunwrs.model.haloruns.HaloRunsResponse;
import ooo.gyoo.speedrunwrs.model.haloruns.Participant;
import ooo.gyoo.speedrunwrs.model.srcom.run.Player;
import ooo.gyoo.speedrunwrs.utils.DurationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
                    this.lastRunId = Files.readAllLines(this.file.toPath()).get(0);
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
            for(Entry entry : response.getEntries()){
                if(StringUtils.equalsIgnoreCase(entry.getRunId(), lastRunId)) break;
                if(StringUtils.equalsIgnoreCase("Full Game", entry.getLevelName())){
                    String time = DurationUtils.getTime(entry.getDuration());
                    final StringBuilder output = new StringBuilder(entry.getGameName() + " - " + entry.getCategoryName()
                            + " in " + time
                            + " by " + entry.getParticipants().stream().map(Participant::getUsername).collect(Collectors.joining(", ")));
                    //output.append(": ").append(entry.getParticipants().stream().map(Participant::getEvidenceLink).collect(Collectors.joining("\n")));
                    output.append("\nhttps://haloruns.com").append(entry.getLeaderboardUrl());
                    if (output.length() < 280) {
                        try {
                            this.messageQueue.getQueue().put(output.toString());
                        } catch (final InterruptedException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                } else {
                    LOGGER.info("Run " + entry.getRunId() + " is not full game");
                }
            }
            this.lastRunId = response.getEntries().get(0).getRunId();
            Files.write(this.file.toPath(), this.lastRunId.getBytes());
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}

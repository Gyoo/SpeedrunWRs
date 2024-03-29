package ooo.gyoo.speedrunwrs.service.impl;

import kotlin.collections.ArrayDeque;
import ooo.gyoo.speedrunwrs.api.SRComClient;
import ooo.gyoo.speedrunwrs.model.MessageQueue;
import ooo.gyoo.speedrunwrs.model.srcom.category.Category;
import ooo.gyoo.speedrunwrs.model.srcom.game.Game;
import ooo.gyoo.speedrunwrs.model.srcom.leaderboard.Leaderboard;
import ooo.gyoo.speedrunwrs.model.srcom.run.Player;
import ooo.gyoo.speedrunwrs.model.srcom.run.Run;
import ooo.gyoo.speedrunwrs.model.srcom.variable.Variable;
import ooo.gyoo.speedrunwrs.utils.DurationUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class SRComService {

    static Logger LOGGER = LoggerFactory.getLogger(SRComService.class);
    private final MessageQueue messageQueue;

    private final SRComClient srComClient;

    private File file;

    private final Collection<String> lastRunsId = new CircularFifoQueue<>(100);

    @Autowired
    public SRComService(final MessageQueue messageQueue, final SRComClient srComClient) {
        this.messageQueue = messageQueue;
        this.srComClient = srComClient;

        this.file = new File("last.txt");
        try {
            if (this.file.exists()) {
                if (!Files.readAllLines(this.file.toPath()).isEmpty()) {
                    this.lastRunsId.addAll(Files.readAllLines(this.file.toPath()));
                }
            } else {
                this.file.createNewFile();
            }
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 0)
    @Async(value = "srcomThreadPool")
    public void run() {
        final List<Run> runs = this.srComClient.listRuns().getData();
        Collections.reverse(runs); //Reading list from last to first because lastRunsId is FIFO. We want the "oldest" runs to be purged first.
        for (final Run run : runs) {
            if (this.lastRunsId.contains(run.getId())) {
                continue;
            }
            this.lastRunsId.add(run.getId());
            if (run.getLevel() != null) {
                LOGGER.info("Run " + run.getId() + " is an IL");
                continue;
            }
            final Map<String, Variable> variables = this.getVariables(run);
            final Leaderboard leaderboard = this.srComClient.getTop(run.getGame().getData().getId(), run.getCategory().getData().getId(), run.getValues()).getData();
            if (run.getId().equals(leaderboard.getRuns().get(0).getRun().getId())) {
                final Game game = run.getGame().getData();
                final Category category = run.getCategory().getData();
                if (category.getMiscellaneous()) {
                    LOGGER.info("Run " + run.getId() + " is misc");
                    continue;
                }
                if (game.getRomhack()) {
                    LOGGER.info("Run " + run.getId() + " is a romhack");
                    continue;
                }
                if (!game.getRuleset().isRequireVerification()) {
                    LOGGER.info("Run " + run.getId() + " is not verified");
                    continue;
                }
                if (game.getNames().getInternational().toLowerCase().contains("memes") ||
                        category.getName().toLowerCase().contains("memes") ||
                        game.getNames().getInternational().toLowerCase().contains("category extensions") ||
                        category.getName().toLowerCase().contains("category extensions") ||
                        category.getName().toLowerCase().contains("score")) {
                    LOGGER.info("Run " + run.getId() + " is a meme");
                    continue;
                }
                if(Duration.parse(run.getTimes().getPrimary()).toMillis() < 1000){
                    LOGGER.info("Run " + run.getId() + " is too short");
                    continue;
                }
                final String time = DurationUtils.getTime(Duration.parse(run.getTimes().getPrimary()));
                final String timing = this.getTiming(leaderboard);
                final String variablesString = this.variablesToString(run, variables);
                final StringBuilder output = new StringBuilder(game.getNames().getInternational() + " - " + category.getName() + " " + variablesString + "in " + time + timing + " by");
                for (final Player player : run.getPlayers().getData()) {
                    switch (player.getRel()) {
                        case "user" -> {
                            final String username = player.getNames().getInternational();
                            output.append(" ").append(username).append(",");
                        }
                        case "guest" -> output.append(" ").append(player.getName()).append(",");
                    }
                }
                output.deleteCharAt(output.length() - 1);
                output.append(": ").append(run.getWeblink());
                if (output.length() < 280) {
                    try {
                        this.messageQueue.getQueue().put(output.toString());
                    } catch (final InterruptedException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            } else {
                LOGGER.info("Run " + run.getId() + " is not a WR");
            }
        }
        try {
            FileUtils.writeLines(this.file, this.lastRunsId);
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Map<String, Variable> getVariables(final Run run) {
        final Map<String, Variable> variables = new HashMap<>();
        for (final Iterator<Map.Entry<String, String>> it = run.getValues().entrySet().iterator(); it.hasNext(); ) {
            final Map.Entry<String, String> entry = it.next();
            final Variable variable = this.srComClient.getVariable(entry.getKey()).getData();
            if (variable.isSubcategory()) {
                variables.put("var-" + entry.getKey(), variable);
            } else {
                it.remove();
            }
        }
        return variables;
    }

    private String variablesToString(final Run run, final Map<String, Variable> variables) {
        final StringBuilder builder = new StringBuilder();
        for (final Map.Entry<String, String> entry : run.getValues().entrySet()) {
            builder.append("[")
                    .append(variables.get("var-" + entry.getKey()).getValues().getValues().get(entry.getValue()).getLabel())
                    .append("] ");
        }
        return builder.toString();
    }

    private String getTiming(final Leaderboard leaderboard) {
        return switch (leaderboard.getTiming()) {
            case "realtime_noloads" -> " (No Loads)";
            case "ingame" -> " (IGT)";
            default -> "";
        };
    }
}

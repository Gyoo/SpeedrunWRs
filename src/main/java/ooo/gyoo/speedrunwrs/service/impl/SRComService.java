package ooo.gyoo.speedrunwrs.service.impl;

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
import java.time.Duration;
import java.util.*;

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
        final List<Run> runs = this.srComClient.listRuns().data();
        Collections.reverse(runs); //Reading list from last to first because lastRunsId is FIFO. We want the "oldest" runs to be purged first.
        for (final Run run : runs) {
            if (this.lastRunsId.contains(run.id())) {
                continue;
            }
            this.lastRunsId.add(run.id());
            if (run.level() != null) {
                LOGGER.info("Run {} is an IL", run.id());
                continue;
            }
            final Map<String, Variable> variables = this.getVariables(run);
            final Leaderboard leaderboard = this.srComClient.getTop(run.game().data().id(), run.category().data().id(), run.values()).data();
            if (run.id().equals(leaderboard.runs().getFirst().run().id())) {
                final Game game = run.game().data();
                final Category category = run.category().data();
                if (category.miscellaneous()) {
                    LOGGER.info("Run {} is misc", run.id());
                    continue;
                }
                if (game.romhack()) {
                    LOGGER.info("Run {} is a romhack", run.id());
                    continue;
                }
                if (!game.ruleset().requireVerification()) {
                    LOGGER.info("Run " + run.id() + " is not verified");
                    continue;
                }
                if (game.names().international().toLowerCase().contains("memes") ||
                        category.name().toLowerCase().contains("memes") ||
                        game.names().international().toLowerCase().contains("category extensions") ||
                        category.name().toLowerCase().contains("category extensions") ||
                        category.name().toLowerCase().contains("score")) {
                    LOGGER.info("Run {} is a meme", run.id());
                    continue;
                }
                if(Duration.parse(run.times().primary()).toMillis() < 1000){
                    LOGGER.info("Run {} is too short", run.id());
                    continue;
                }
                final String time = DurationUtils.getTime(Duration.parse(run.times().primary()));
                final String timing = this.getTiming(leaderboard);
                final String variablesString = this.variablesToString(run, variables);
                final StringBuilder output = new StringBuilder(game.names().international() + " - " + category.name() + " " + variablesString + "in " + time + timing + " by");
                for (final Player player : run.players().data()) {
                    switch (player.rel()) {
                        case "user" -> {
                            final String username = player.names().international();
                            output.append(" ").append(username).append(",");
                        }
                        case "guest" -> output.append(" ").append(player.name()).append(",");
                    }
                }
                output.deleteCharAt(output.length() - 1);
                if (output.length() < 300) {
                    try {
                        this.messageQueue.getQueue().put(Pair.of(output.toString(), run.weblink()));
                    } catch (final InterruptedException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            } else {
                LOGGER.info("Run {} is not a WR", run.id());
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
        for (final Iterator<Map.Entry<String, String>> it = run.values().entrySet().iterator(); it.hasNext(); ) {
            final Map.Entry<String, String> entry = it.next();
            final Variable variable = this.srComClient.getVariable(entry.getKey()).data();
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
        for (final Map.Entry<String, String> entry : run.values().entrySet()) {
            builder.append("[")
                    .append(variables.get("var-" + entry.getKey()).values().values().get(entry.getValue()).label())
                    .append("] ");
        }
        return builder.toString();
    }

    private String getTiming(final Leaderboard leaderboard) {
        return switch (leaderboard.timing()) {
            case "realtime_noloads" -> " (No Loads)";
            case "ingame" -> " (IGT)";
            default -> "";
        };
    }
}

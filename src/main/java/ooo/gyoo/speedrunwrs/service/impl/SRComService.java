package ooo.gyoo.speedrunwrs.service.impl;

import ooo.gyoo.speedrunwrs.api.SRCom;
import ooo.gyoo.speedrunwrs.model.MessageQueue;
import ooo.gyoo.speedrunwrs.model.category.Category;
import ooo.gyoo.speedrunwrs.model.game.Game;
import ooo.gyoo.speedrunwrs.model.leaderboard.Leaderboard;
import ooo.gyoo.speedrunwrs.model.run.Player;
import ooo.gyoo.speedrunwrs.model.run.Run;
import ooo.gyoo.speedrunwrs.model.variable.Variable;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class SRComService {

    static Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
    private final MessageQueue messageQueue;

    private final SRCom srCom;

    private final File file;

    private String lastRunId;

    @Autowired
    public SRComService(final MessageQueue messageQueue, final SRCom srCom) {
        this.messageQueue = messageQueue;
        this.srCom = srCom;

        this.file = new File("last.txt");
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
    public void run() {
        final List<Run> runs = this.srCom.listRuns().getData();
        for (final Run run : runs) {
            if (run.getId().equals(this.lastRunId)) {
                break;
            }
            if (run.getLevel() != null) {
                LOGGER.info("Run " + run.getId() + " is an IL");
                continue;
            }
            final Map<String, Variable> variables = this.getVariables(run);
            final Leaderboard leaderboard = this.srCom.getTop(run.getGame().getData().getId(), run.getCategory().getData().getId(), run.getValues()).getData();
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
                if (game.getNames().getInternational().toLowerCase().contains("memes") ||
                        category.getName().toLowerCase().contains("memes") ||
                        game.getNames().getInternational().toLowerCase().contains("category extensions") ||
                        category.getName().toLowerCase().contains("category extensions")) {
                    LOGGER.info("Run " + run.getId() + " is a meme");
                    continue;
                }
                final String time = this.getTime(run);
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
                if (output.length() < 140) {
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
        this.lastRunId = runs.get(0).getId();
        try {
            Files.write(this.file.toPath(), this.lastRunId.getBytes());
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Map<String, Variable> getVariables(final Run run) {
        final Map<String, Variable> variables = new HashMap<>();
        for (final Iterator<Map.Entry<String, String>> it = run.getValues().entrySet().iterator(); it.hasNext(); ) {
            final Map.Entry<String, String> entry = it.next();
            final Variable variable = this.srCom.getVariable(entry.getKey()).getData();
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

    private String getTime(final Run run) {
        final Duration duration = Duration.parse(run.getTimes().getPrimary());
        String format;
        if (duration.toMillis() >= 360000000) {
            format = "HHH:mm:ss";
        } else if (duration.toMillis() >= 36000000) {
            format = "HH:mm:ss";
        } else if (duration.toMillis() >= 3600000) {
            format = "H:mm:ss";
        } else if (duration.toMillis() >= 600000) {
            format = "mm:ss";
        } else {
            format = "m:ss";
        }
        if (duration.toMillis() % 1000 != 0) {
            format += ".SSS";
        }
        return DurationFormatUtils.formatDuration(duration.toMillis(), format, true);
    }

    private String getTiming(final Leaderboard leaderboard) {
        return switch (leaderboard.getTiming()) {
            case "realtime_noloads" -> " (No Loads)";
            case "ingame" -> " (IGT)";
            default -> "";
        };
    }
}

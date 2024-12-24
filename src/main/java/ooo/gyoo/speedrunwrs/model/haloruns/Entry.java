package ooo.gyoo.speedrunwrs.model.haloruns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ooo.gyoo.speedrunwrs.utils.NumberToDurationDeserializer;

import java.time.Duration;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Entry(@JsonProperty("RunId")
                    String runId,
                    @JsonProperty("GameName")
                    String gameName,
                    @JsonProperty("CategoryName")
                    String categoryName,
                    @JsonProperty("LevelName")
                    String levelName,
                    @JsonProperty("Difficulty")
                    String difficulty,
                    @JsonProperty("Duration")
                    @JsonDeserialize(using = NumberToDurationDeserializer.class)
                    Duration duration,
                    @JsonProperty("Participants")
                    List<Participant> participants,
                    @JsonProperty("LeaderboardUrl")
                    String leaderboardUrl) {
}

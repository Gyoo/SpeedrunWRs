package ooo.gyoo.speedrunwrs.model.haloruns;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ooo.gyoo.speedrunwrs.utils.NumberToDurationDeserializer;

import java.time.Duration;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry {
    @JsonProperty("RunId")
    private String runId;
    @JsonProperty("GameName")
    private String gameName;
    @JsonProperty("CategoryName")
    private String categoryName;
    @JsonProperty("LevelName")
    private String levelName;
    @JsonProperty("Difficulty")
    private String difficulty;
    @JsonProperty("Duration")
    @JsonDeserialize(using = NumberToDurationDeserializer.class)
    private Duration duration;
    @JsonProperty("Participants")
    private List<Participant> participants;
    @JsonProperty("LeaderboardUrl")
    private String leaderboardUrl;

    public String getLeaderboardUrl() {
        return leaderboardUrl;
    }

    public void setLeaderboardUrl(String leaderboardUrl) {
        this.leaderboardUrl = leaderboardUrl;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}

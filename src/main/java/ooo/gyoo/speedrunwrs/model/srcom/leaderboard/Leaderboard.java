package ooo.gyoo.speedrunwrs.model.srcom.leaderboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leaderboard {

    private List<RankedRun> runs;

    private String timing;

    public List<RankedRun> getRuns() {
        return this.runs;
    }

    public void setRuns(final List<RankedRun> runs) {
        this.runs = runs;
    }

    public String getTiming() {
        return this.timing;
    }

    public void setTiming(final String timing) {
        this.timing = timing;
    }
}

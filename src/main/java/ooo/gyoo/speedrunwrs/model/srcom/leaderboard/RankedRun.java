package ooo.gyoo.speedrunwrs.model.srcom.leaderboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ooo.gyoo.speedrunwrs.model.srcom.run.RunFlat;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankedRun {

    private Integer place;
    private RunFlat run;

    public Integer getPlace() {
        return this.place;
    }

    public void setPlace(final Integer place) {
        this.place = place;
    }

    public RunFlat getRun() {
        return this.run;
    }

    public void setRun(final RunFlat run) {
        this.run = run;
    }
}

package ooo.gyoo.speedrunwrs.model.srcom.leaderboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ooo.gyoo.speedrunwrs.model.srcom.run.RunFlat;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RankedRun(Integer place,
                        RunFlat run) {
}

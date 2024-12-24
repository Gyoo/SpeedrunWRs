package ooo.gyoo.speedrunwrs.model.srcom.leaderboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Leaderboard(List<RankedRun> runs,
                          String timing) {}

package ooo.gyoo.speedrunwrs.api;

import feign.CollectionFormat;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import ooo.gyoo.speedrunwrs.model.haloruns.HaloRunsResponse;
import ooo.gyoo.speedrunwrs.model.srcom.SRComResponse;
import ooo.gyoo.speedrunwrs.model.srcom.leaderboard.Leaderboard;
import ooo.gyoo.speedrunwrs.model.srcom.run.Run;
import ooo.gyoo.speedrunwrs.model.srcom.variable.Variable;

import java.util.List;
import java.util.Map;

public interface HaloRunsClient {

    @RequestLine(value = "GET /boards/recent.json")
    HaloRunsResponse listRuns();

}

package ooo.gyoo.speedrunwrs.api;

import feign.CollectionFormat;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import ooo.gyoo.speedrunwrs.model.SRComResponse;
import ooo.gyoo.speedrunwrs.model.leaderboard.Leaderboard;
import ooo.gyoo.speedrunwrs.model.run.Run;
import ooo.gyoo.speedrunwrs.model.variable.Variable;

import java.util.List;
import java.util.Map;

public interface SRCom {

    @RequestLine(value = "GET /runs?status=verified&orderby=verify-date&direction=desc&embed=game,category,players", collectionFormat = CollectionFormat.CSV)
    SRComResponse<List<Run>> listRuns();

    @RequestLine("GET /leaderboards/{gameId}/category/{categoryId}?top=1")
    SRComResponse<Leaderboard> getTop(@Param("gameId") String gameId, @Param("categoryId") String categoryId, @QueryMap Map<String, String> queryMap);

    @RequestLine("GET /variables/{id}")
    SRComResponse<Variable> getVariable(@Param("id") String id);

}

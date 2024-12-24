package ooo.gyoo.speedrunwrs.api;

import feign.RequestLine;
import ooo.gyoo.speedrunwrs.model.haloruns.HaloRunsResponse;

public interface HaloRunsClient {

    @RequestLine(value = "GET /boards/recent.json")
    HaloRunsResponse listRuns();

}

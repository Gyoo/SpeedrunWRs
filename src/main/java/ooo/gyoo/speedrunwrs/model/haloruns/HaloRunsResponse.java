package ooo.gyoo.speedrunwrs.model.haloruns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HaloRunsResponse(@JsonProperty("Entries") List<Entry> entries) {
}

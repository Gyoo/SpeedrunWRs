package ooo.gyoo.speedrunwrs.model.haloruns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Participant(@JsonProperty("Username") String username,
                          @JsonProperty("EvidenceLink") String evidenceLink) {
}

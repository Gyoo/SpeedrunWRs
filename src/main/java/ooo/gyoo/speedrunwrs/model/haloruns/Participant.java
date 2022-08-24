package ooo.gyoo.speedrunwrs.model.haloruns;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Participant {

    @JsonProperty("Username")
    private String username;
    @JsonProperty("EvidenceLink")
    private String evidenceLink;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEvidenceLink() {
        return evidenceLink;
    }

    public void setEvidenceLink(String evidenceLink) {
        this.evidenceLink = evidenceLink;
    }
}

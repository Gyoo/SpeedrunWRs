package ooo.gyoo.speedrunwrs.model.srcom.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ruleset {

    @JsonProperty("require-verification")
    private boolean requireVerification;

    public boolean isRequireVerification() {
        return this.requireVerification;
    }

    public void setRequireVerification(final boolean requireVerification) {
        this.requireVerification = requireVerification;
    }
}

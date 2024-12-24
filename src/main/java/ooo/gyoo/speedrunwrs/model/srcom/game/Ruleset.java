package ooo.gyoo.speedrunwrs.model.srcom.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Ruleset(@JsonProperty("require-verification") boolean requireVerification) {}

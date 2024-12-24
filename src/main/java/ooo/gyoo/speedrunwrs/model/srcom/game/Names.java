package ooo.gyoo.speedrunwrs.model.srcom.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Names(String international) {}

package ooo.gyoo.speedrunwrs.model.srcom.run;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Times(String primary) {
}

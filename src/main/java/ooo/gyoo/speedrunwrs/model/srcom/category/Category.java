package ooo.gyoo.speedrunwrs.model.srcom.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Category(String id,
                       String name,
                       Boolean miscellaneous) {
}

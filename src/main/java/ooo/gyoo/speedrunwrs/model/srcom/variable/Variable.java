package ooo.gyoo.speedrunwrs.model.srcom.variable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Variable(@JsonProperty("is-subcategory") boolean isSubcategory,
                       Values values) {
}

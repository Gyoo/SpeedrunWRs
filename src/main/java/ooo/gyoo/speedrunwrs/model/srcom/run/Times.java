package ooo.gyoo.speedrunwrs.model.srcom.run;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Times {

    private String primary;

    public String getPrimary() {
        return this.primary;
    }

    public void setPrimary(final String primary) {
        this.primary = primary;
    }
}

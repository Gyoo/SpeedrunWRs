package ooo.gyoo.speedrunwrs.model.srcom.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private String id;
    private String name;
    private Boolean miscellaneous;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean getMiscellaneous() {
        return this.miscellaneous;
    }

    public void setMiscellaneous(final Boolean miscellaneous) {
        this.miscellaneous = miscellaneous;
    }
}

package ooo.gyoo.speedrunwrs.model.variable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Variable {

    @JsonProperty("is-subcategory")
    private boolean isSubcategory;

    private Values values;

    public boolean isSubcategory() {
        return this.isSubcategory;
    }

    public void setSubcategory(final boolean subcategory) {
        this.isSubcategory = subcategory;
    }

    public Values getValues() {
        return this.values;
    }

    public void setValues(final Values values) {
        this.values = values;
    }
}

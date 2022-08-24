package ooo.gyoo.speedrunwrs.model.srcom.run;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ooo.gyoo.speedrunwrs.model.srcom.game.Names;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    private String rel;
    private String id;
    private String name;
    private String uri;
    private Names names;

    public String getRel() {
        return this.rel;
    }

    public void setRel(final String rel) {
        this.rel = rel;
    }

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

    public String getUri() {
        return this.uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public Names getNames() {
        return this.names;
    }

    public void setNames(final Names names) {
        this.names = names;
    }
}

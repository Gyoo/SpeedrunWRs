package ooo.gyoo.speedrunwrs.model.srcom.run;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunFlat {

    private String id;
    private String game;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getGame() {
        return this.game;
    }

    public void setGame(final String game) {
        this.game = game;
    }
}

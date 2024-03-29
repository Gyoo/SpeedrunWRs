package ooo.gyoo.speedrunwrs.model.srcom.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    private String id;
    private Names names;
    private Boolean romhack;
    private Ruleset ruleset;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Names getNames() {
        return this.names;
    }

    public void setNames(final Names names) {
        this.names = names;
    }

    public Boolean getRomhack() {
        return this.romhack;
    }

    public void setRomhack(final Boolean romhack) {
        this.romhack = romhack;
    }

    public Ruleset getRuleset() {
        return this.ruleset;
    }

    public void setRuleset(final Ruleset ruleset) {
        this.ruleset = ruleset;
    }
}

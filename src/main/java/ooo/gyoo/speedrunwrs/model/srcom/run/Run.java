package ooo.gyoo.speedrunwrs.model.srcom.run;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ooo.gyoo.speedrunwrs.model.srcom.SRComResponse;
import ooo.gyoo.speedrunwrs.model.srcom.category.Category;
import ooo.gyoo.speedrunwrs.model.srcom.game.Game;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Run {

    private String id;
    private String weblink;
    private SRComResponse<Game> game;
    private String level;
    private SRComResponse<Category> category;
    private SRComResponse<List<Player>> players;
    private Times times;
    private Map<String, String> values;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getWeblink() {
        return this.weblink;
    }

    public void setWeblink(final String weblink) {
        this.weblink = weblink;
    }

    public SRComResponse<Game> getGame() {
        return this.game;
    }

    public void setGame(final SRComResponse<Game> game) {
        this.game = game;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(final String level) {
        this.level = level;
    }

    public SRComResponse<Category> getCategory() {
        return this.category;
    }

    public void setCategory(final SRComResponse<Category> category) {
        this.category = category;
    }

    public SRComResponse<List<Player>> getPlayers() {
        return this.players;
    }

    public void setPlayers(final SRComResponse<List<Player>> players) {
        this.players = players;
    }

    public Times getTimes() {
        return this.times;
    }

    public void setTimes(final Times times) {
        this.times = times;
    }

    public Map<String, String> getValues() {
        return this.values;
    }

    public void setValues(final Map<String, String> values) {
        this.values = values;
    }
}

package ooo.gyoo.speedrunwrs.model.srcom.run;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ooo.gyoo.speedrunwrs.model.srcom.SRComResponse;
import ooo.gyoo.speedrunwrs.model.srcom.category.Category;
import ooo.gyoo.speedrunwrs.model.srcom.game.Game;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Run(String id,
                  String weblink,
                  SRComResponse<Game> game,
                  String level,
                  SRComResponse<Category> category,
                  SRComResponse<List<Player>> players,
                  Times times,
                  Map<String, String> values) { }

package ooo.gyoo.speedrunwrs.model.srcom.run;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ooo.gyoo.speedrunwrs.model.srcom.game.Names;

/**
 * Created by Gyoo on 18/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Player(String rel,
                     String id,
                     String name,
                     String uri,
                     Names names) { }

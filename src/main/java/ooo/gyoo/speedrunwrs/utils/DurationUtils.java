package ooo.gyoo.speedrunwrs.utils;

import ooo.gyoo.speedrunwrs.model.srcom.run.Run;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;

public class DurationUtils {

    public static String getTime(final Duration duration) {
        String format;
        if (duration.toMillis() >= 360000000) {
            format = "HHH:mm:ss";
        } else if (duration.toMillis() >= 36000000) {
            format = "HH:mm:ss";
        } else if (duration.toMillis() >= 3600000) {
            format = "H:mm:ss";
        } else if (duration.toMillis() >= 600000) {
            format = "mm:ss";
        } else {
            format = "m:ss";
        }
        if (duration.toMillis() % 1000 != 0) {
            format += ".SSS";
        }
        return DurationFormatUtils.formatDuration(duration.toMillis(), format, true);
    }


}

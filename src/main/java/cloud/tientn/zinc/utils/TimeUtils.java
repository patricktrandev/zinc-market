package cloud.tientn.zinc.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    private static LocalDateTime START_TIME;

    public static String getCurrentTime() {
        if (START_TIME == null) {
            throw new IllegalStateException("You must first call TimeUtil.resetClock()");
        }

        long diff = ChronoUnit.MILLIS.between(START_TIME, LocalDateTime.now());

        return String.format("%02d", diff/100) + ": ";
    }
    public static void resetClock() {
        START_TIME = LocalDateTime.now();
    }
}

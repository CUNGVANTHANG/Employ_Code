package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(new Date());
    }
}

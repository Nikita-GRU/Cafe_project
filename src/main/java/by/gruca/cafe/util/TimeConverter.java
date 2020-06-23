package by.gruca.cafe.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter {
    public static String convertFromLocalDateTimeToSQLDateTime(LocalDateTime time) {
        if (time != null && !(time.toString().isEmpty())) {
            return time.toString().replaceAll("T", " ").split("\\.", 2)[0];
        }
        return "";
    }

    public static LocalDateTime convertFromSQLDateTimeToLocalDateTime(String dateTime) {
        if (dateTime != null && !dateTime.isEmpty())
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return LocalDateTime.now();

    }
}

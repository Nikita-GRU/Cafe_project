package by.gruca.cafe.util;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
        System.out.println(TimeConverter.convertFromLocalDateTimeToSQLDateTime(LocalDateTime.now()));

    }
}

package com.example.Calculator_Project.util;

import java.time.LocalTime;

public class GreetingUtil {


    private static final LocalTime MORNING = LocalTime.of(0, 0);
    private static final LocalTime AFTER_NOON = LocalTime.of(12, 0);
    private static final LocalTime EVENING = LocalTime.of(16, 0);
    private static final LocalTime NIGHT = LocalTime.of(21, 0);

    private static LocalTime now = LocalTime.now();

    public static String getGreeting() {
        if (between(MORNING, AFTER_NOON))
            return "Good Morning";
        else if (between(AFTER_NOON, EVENING))
            return "Good Afternoon";
        else if (between(EVENING, NIGHT))
            return "Good Evening";
        else
            return "Good Evening";
    }

    private static boolean between(LocalTime start, LocalTime end) {
        return (!now.isBefore(start)) && now.isBefore(end);
    }
}
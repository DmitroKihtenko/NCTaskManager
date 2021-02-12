package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service;

import java.time.LocalDateTime;

public class DateTimeArithmetic {
    public static LocalDateTime trimNanos(LocalDateTime time) {
        if(time == null) {
            throw new IllegalArgumentException(
                    "Method's parameter has null value"
            );
        }
        return time.minusNanos(time.getNano());
    }

    public static LocalDateTime trimSeconds(LocalDateTime time) {
        return trimNanos(time).minusSeconds(time.getSecond());
    }
}

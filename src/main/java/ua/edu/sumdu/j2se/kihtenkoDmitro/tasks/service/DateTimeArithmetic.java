package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service;

import java.time.LocalDateTime;

public class DateTimeArithmetic {
    public static LocalDateTime trimSeconds(LocalDateTime time) {
        time = time.minusNanos(time.getNano());
        return time.minusSeconds(time.getSecond());
    }
}

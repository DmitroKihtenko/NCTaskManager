package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service;

import java.time.format.DateTimeFormatter;

public class Formatter {
    protected static DateTimeFormatter mainDay;
    protected static DateTimeFormatter mainTime;
    protected static DateTimeFormatter inputDate;
    protected static DateTimeFormatter outputDate;

    static {
        mainDay = DateTimeFormatter.ofPattern("EEEE, dd LLLL, yyyy");
        mainTime = DateTimeFormatter.ofPattern("HH:mm");
        inputDate = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
        outputDate = DateTimeFormatter.ofPattern("yy MM dd HH:mm");
    }

    public static DateTimeFormatter getMainDay() {
        return mainDay;
    }

    public static DateTimeFormatter getMainTime() {
        return mainTime;
    }

    public static DateTimeFormatter getMainDateInput() {
        return inputDate;
    }

    public static DateTimeFormatter getMainDateOutput() {
        return outputDate;
    }
}

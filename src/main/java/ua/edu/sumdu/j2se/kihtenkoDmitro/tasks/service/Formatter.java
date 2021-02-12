package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service;

import java.time.format.DateTimeFormatter;

public class Formatter {
    protected static DateTimeFormatter inputDate;
    protected static DateTimeFormatter outputDate;
    protected static DateTimeFormatter inputTime;

    static {
        inputDate = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
        outputDate = DateTimeFormatter.ofPattern("yy MM dd HH:mm");
        inputTime = DateTimeFormatter.ofPattern("HH:mm");
    }

    public static DateTimeFormatter getMainTimeInput() {
        return inputTime;
    }

    public static DateTimeFormatter getMainDateInput() {
        return inputDate;
    }

    public static DateTimeFormatter getMainDateOutput() {
        return outputDate;
    }
}

package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util;

public class LineCOut {
    public void printLine(String line) {
        if(line == null) {
            throw new IllegalArgumentException(
                    "String parameter has null value"
            );
        }
        System.out.println(line);
    }

    public void printLines(String ... lines) {
        for(String line : lines) {
            printLine(line);
        }
    }
}

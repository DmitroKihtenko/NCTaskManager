package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util;

import org.apache.log4j.Logger;

public class LineCOut {
    private final static Logger logger =
            Logger.getLogger(LineCOut.class);

    public void printLine(String line) {
        if(line == null) {
            throw new IllegalArgumentException(
                    "String parameter has null value"
            );
        }
        System.out.println(line);
    }

    public void printLines(String ... lines) {
        if(lines == null) {
            throw new IllegalArgumentException(
                    "String parameter list has null value"
            );
        }
        if(lines.length == 0) {
            logger.warn(
                    "An empty list passed for print"
            );
        }
        for(String line : lines) {
            printLine(line);
        }
    }
}

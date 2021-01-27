package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.CInStatusBuffer;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Menu;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.StringChecker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class StatusInput {
    private static final Logger logger =
            Logger.getLogger(StatusInput.class);

    protected CInStatusBuffer statusBuffer;
    protected int inputLineLimit;

    public StatusInput(CInStatusBuffer status) {
        setBuffer(status);
    }

    public void setLineLimit(int limit) {
        if(limit <= 0) {
            throw new IllegalArgumentException(
                    "Expected positive limit parameter"
            );
        }
        inputLineLimit = limit;

        logger.debug(
                "Set length limit for input lines " + limit
        );
    }

    int getLineLimit() {
        return inputLineLimit;
    }

    public String nextLine() {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        if(line.length() > inputLineLimit) {
            statusBuffer.setStatus("Expected line no more than " +
                    inputLineLimit + " characters");
            do {
                logger.debug(
                        "User's input error"
                );

                line = in.nextLine();
            } while (line.length() > inputLineLimit);
            statusBuffer.setStatusOk();
        }
        return line;
    }

    public String nextTextLine() {
        Scanner in = new Scanner(System.in);
        boolean wasError = false;
        String line;
        do {
            line = in.nextLine();
            if (line.length() > inputLineLimit) {
                statusBuffer.setStatus("Expected line no more than " +
                        inputLineLimit + " characters");

                logger.debug(
                        "User's input error"
                );

                wasError = true;
                continue;
            }
            if (StringChecker.checkEmpty(line)) {
                statusBuffer.setStatus("Expected not empty line");

                logger.debug(
                        "User's input error"
                );

                wasError = true;
                continue;
            }
            break;
        } while (true);
        if(wasError) {
            statusBuffer.setStatusOk();
        }
        return line;
    }

    public void setBuffer(CInStatusBuffer status) {
        if(status == null) {
            throw new IllegalArgumentException(
                    "Buffer parameter has null value"
            );
        }
        statusBuffer = status;
    }

    public CInStatusBuffer getBuffer() {
        return statusBuffer;
    }

    public int nextIntFrom(int from, int to) {
        Scanner in = new Scanner(System.in);
        int inValue;

        if(from > to) {
            int swapVal = from;
            from = to;
            to = swapVal;
        }

        while(true) {
            try {
                inValue = Integer.valueOf(in.nextLine());
            } catch (NumberFormatException e) {
                statusBuffer.setStatus("Expected numeric input!");
                continue;
            }

            if(inValue < from) {
                statusBuffer.setStatus("Expected value no less than " +
                        from + "!");

                logger.debug(
                        "User's input error"
                );

                continue;
            } else if (inValue > to) {
                statusBuffer.setStatus("Expected value no more than " +
                        to + "!");

                logger.debug(
                        "User's input error"
                );

                continue;
            }
            return inValue;
        }
    }

    public boolean nextBoolean() {
        return !(nextIntFrom(0, 1) == 0);
    }

    public LocalDateTime nextTime(DateTimeFormatter format,
                              LocalDateTime from, LocalDateTime to) {
        if(format == null || from == null || to == null) {
            throw new IllegalArgumentException(
                    "Method's parameter has null value"
            );
        }

        Scanner in = new Scanner(System.in);
        String line;
        LocalDateTime returnTime;
        boolean wasError = false;
        if(from.isAfter(to)) {
            LocalDateTime swapVal = from;
            from = to;
            to = swapVal;
        }
        while(true) {
            try {
                line = in.nextLine();
                returnTime = LocalDateTime.parse(line, format);
            } catch (DateTimeParseException e) {
                statusBuffer.setStatus("Invalid date time format");

                logger.debug(
                        "User's input error"
                );

                wasError = true;
                continue;
            }
            if(returnTime.isBefore(from)) {
                statusBuffer.setStatus("Expected time no less" +
                        " than " + from.format(format));

                logger.debug(
                        "User's input error"
                );

                wasError = true;
                continue;
            }
            if(returnTime.isAfter(to)) {
                statusBuffer.setStatus("Expected time no more" +
                            " than " + to.format(format));

                logger.debug(
                        "User's input error"
                );

                wasError = true;
                continue;
            }
            break;
        }
        if(wasError) {
            statusBuffer.setStatusOk();
        }
        return returnTime;
    }

    public int nextMenu(Menu menu) {
        return nextIntFrom(1, menu.getSize());
    }
}

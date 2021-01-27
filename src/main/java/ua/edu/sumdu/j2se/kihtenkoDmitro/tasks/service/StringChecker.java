package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service;

import org.apache.log4j.Logger;

public class StringChecker {
    private static final Logger logger =
            Logger.getLogger(StringChecker.class);

    public static boolean checkEmpty(String str) {
        logger.debug(
                "Checking string for emptiness"
        );

        if(str.length() == 0) {
            return true;
        }
        char symbol;
        for(int counter = 0; counter < str.length(); counter++) {
            symbol = str.charAt(counter);
            if(symbol != ' ' && symbol != '\t') {
                return false;
            }
        }
        return true;
    }
}

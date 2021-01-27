package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util;

import org.apache.log4j.Logger;

public class SeparateCOut extends LineCOut {
    protected char separatorSymbol;
    protected int separatorLen;

    public SeparateCOut() {
        separatorSymbol = '*';
        separatorLen = 100;
    }

    public void setSeparatorSymbol(char symbol) {
        if(symbol == '\n' || symbol == '\t' || symbol == '\b' ||
        symbol == '\r' || symbol == '\f') {
            throw new IllegalArgumentException(
                    "Method parameter refers to escape sequence"
            );
        }
        separatorSymbol = symbol;

    }

    public char getSeparatorSymbol() {
        return separatorSymbol;
    }

    public void setSeparatorLen(int len) {
        if(len <= 0) {
            throw new IllegalArgumentException(
                    "Separator length parameter has non-positive value"
            );
        }
        separatorLen = len;
    }

    public int getSeparatorLen() {
        return separatorLen;
    }

    public void printSeparate() {
        StringBuilder line = new StringBuilder();
        for(int counter = 0; counter < separatorLen;
            counter++) {
            line.append(separatorSymbol);
        }
        printLine(line.toString());
    }
}

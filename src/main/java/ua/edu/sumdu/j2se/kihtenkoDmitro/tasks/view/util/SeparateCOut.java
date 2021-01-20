package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util;

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
                    "Method parameter refers to escape sequences"
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

    public void printSeparate() {
        StringBuilder line = new StringBuilder();
        for(int counter = 0; counter < separatorLen;
            counter++) {
            line.append(separatorSymbol);
        }
        printLine(line.toString());
    }
}

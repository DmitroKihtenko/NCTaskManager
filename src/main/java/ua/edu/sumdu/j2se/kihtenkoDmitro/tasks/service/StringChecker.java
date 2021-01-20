package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service;

public class StringChecker {
    public static boolean checkCtrlChars(String str) {
        return str.matches("\t\n\r\f\u0085\u2028\u2029");
    }

    public static boolean checkEmpty(String str) {
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

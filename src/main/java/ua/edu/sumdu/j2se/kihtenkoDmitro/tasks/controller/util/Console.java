package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util;

import java.io.IOException;

public class Console {
    public static void clear() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}

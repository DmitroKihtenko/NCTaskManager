package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.Controller;

public class ControllerThread extends Thread {
    Controller controller;

    ControllerThread(Controller controller) {
        if(controller == null) {
            throw new IllegalArgumentException(
                    "SystemTime parameter has null value"
            );
        }
        this.controller = controller;
        setDaemon(true);
        setPriority(MIN_PRIORITY);
    }

    @Override
    public void run() {
        while(true) {
            controller.process();
        }
    }
}

package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import java.util.LinkedList;

public class GeneralController implements Controller {
    protected LinkedList<ControllerHandler> controllers;
    Action currentAction;

    public GeneralController(Action startAction) {
        if(startAction == null) {
            throw new IllegalArgumentException(
                    "Start handler parameter has null value"
            );
        }
        controllers = new LinkedList<>();
        currentAction = startAction;
    }

    public void attach(ControllerHandler controller) {
        if(controller == null) {
            throw new IllegalArgumentException(
                    "Controller parameter has null value"
            );
        }
        controllers.add(controller);
    }

    @Override
    public Action process() {
        do {
            for(ControllerHandler temp : controllers) {
                if(temp.canHandle(currentAction)) {
                    currentAction = temp.process();
                }
            }
        } while(currentAction != Action.EXIT);
        return Action.EXIT;
    }
}

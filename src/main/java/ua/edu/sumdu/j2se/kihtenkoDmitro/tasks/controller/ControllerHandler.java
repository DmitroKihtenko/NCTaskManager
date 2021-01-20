package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

public abstract class ControllerHandler implements Controller {
    protected Action handleAction;

    public boolean canHandle(Action action) {
        return handleAction == action;
    }
}

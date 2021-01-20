package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Observable;

public abstract class ObjectController<T> extends
        ControllerHandler {
    protected T observable;

    public ObjectController(Observable object) {
        if(object == null) {
            throw new IllegalArgumentException(
                    "Observable object has null value"
            );
        }
        observable = (T)object;
    }
}

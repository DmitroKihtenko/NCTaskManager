package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.util.ObserversLinkedMap;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.util.ObserversList;

public abstract class Observable {
    private ObserversList observers;

    public Observable() {
        observers = new ObserversLinkedMap();
    }

    public void setObservers(ObserversList list) {
        if(list == null) {
            throw new IllegalArgumentException(
                    "Observer's list parameter has null value"
            );
        }
        observers = list;
    }

    public ObserversList getObservers() {
        return observers;
    }
}

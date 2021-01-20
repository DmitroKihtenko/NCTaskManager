package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Observable;

public abstract class ConsoleView<T> implements Observer {
    protected T observable;
    ConsoleView(Observable observable) {
        if(observable == null) {
            throw new IllegalArgumentException(
                    "Observable object parameter has null value"
            );
        }
        observable.getObservers().attach(this, Event.VIEW);
        this.observable = (T)observable;
    }
}

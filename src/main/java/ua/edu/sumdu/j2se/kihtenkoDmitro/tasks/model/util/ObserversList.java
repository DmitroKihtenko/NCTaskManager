package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.util;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Observer;

public interface ObserversList extends Cloneable {
    void attach(Observer observer, Event event);
    boolean detach(Observer observer, Event event);

    void updateAll(Event event);
    void updateAll();

    ObserversList clone();
}

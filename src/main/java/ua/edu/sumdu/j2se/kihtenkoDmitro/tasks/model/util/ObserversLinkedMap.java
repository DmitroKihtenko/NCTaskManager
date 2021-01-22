package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.util;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Observer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ObserversLinkedMap implements ObserversList {
    protected HashMap<Event, LinkedList<Observer>> observers;

    public ObserversLinkedMap() {
        observers = new HashMap<>();
    }

    @Override
    public void attach(Observer observer, Event event) {
        if(observer == null || event == null) {
            throw new IllegalArgumentException(
                    "Observer or event type parameter has null value"
            );
        }
        if(!observers.containsKey(event)) {
            observers.put(event, new LinkedList<>());
        }
        observers.get(event).add(observer);
    }

    @Override
    public boolean detach(Observer observer, Event event) {
        if(observer == null || event == null) {
            throw new IllegalArgumentException(
                    "Observer or event type parameter has null value"
            );
        }
        if(observers.containsKey(event)) {
            Iterator<Observer> iterator = observers.get(event).iterator();
            Observer temp;

            while(iterator.hasNext()) {
                temp = iterator.next();
                if(temp == observer) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public void updateAll(Event event) {
        if(observers.containsKey(event)) {
            for(Observer temp : observers.get(event)) {
                temp.update();
            }
        }
    }

    @Override
    public ObserversList clone() {
        ObserversList returnList = new ObserversLinkedMap();
        for(Event key : observers.keySet()) {
            for(Observer temp : observers.get(key)) {
                returnList.attach(temp, key);
            }
        }
        return returnList;
    }
}

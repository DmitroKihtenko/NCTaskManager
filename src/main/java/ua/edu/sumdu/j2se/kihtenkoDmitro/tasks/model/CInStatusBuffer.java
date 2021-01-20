package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;

public class CInStatusBuffer extends Observable {
    protected String status;
    protected static String okStatus;

    static {
        okStatus = "Everything is good";
    }

    CInStatusBuffer() {
        status = okStatus;
    }

    public void setStatus(String status) {
        if(status == null) {
            throw new IllegalArgumentException(
                    "Status parameter has null value"
            );
        }
        this.status = status;
        getObservers().updateAll(Event.VIEW);
    }

    public void setStatusOk() {
        status = okStatus;
        getObservers().updateAll(Event.VIEW);
    }

    public String getStatus() {
        return status;
    }
}

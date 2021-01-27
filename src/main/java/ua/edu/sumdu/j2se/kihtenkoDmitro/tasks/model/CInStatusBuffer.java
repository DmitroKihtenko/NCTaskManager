package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;

public class CInStatusBuffer extends Observable {
    private static final Logger logger =
            Logger.getLogger(CInStatusBuffer.class);

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

        logger.debug(
                "Set status \"" + status + "\""
        );

        getObservers().updateAll();
    }

    public void setStatusOk() {
        setStatus(okStatus);
    }

    public String getStatus() {
        return status;
    }
}

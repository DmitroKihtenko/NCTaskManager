package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.DateTimeArithmetic;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Observer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IncomingTasks extends Observable implements Observer {
    private static final Logger logger =
            Logger.getLogger(IncomingTasks.class);

    protected AbstractTaskList generalTasks;

    protected Map<LocalDateTime, Set<Task>> mapIncoming;
    protected Set<Task> setCurrent;
    protected int checkInterval;

    public IncomingTasks(AbstractTaskList tasks) {
        if(tasks == null) {
            throw new IllegalArgumentException(
                    "Task list parameter has null value"
            );
        }
        generalTasks = tasks;
        tasks.getObservers().attach(this, Event.UPDATE);
        checkInterval = 10;
        LocalDateTime nowTime = LocalDateTime.now();
        mapIncoming = Tasks.calendar(generalTasks,
                nowTime.minusMinutes(1),
                nowTime.plusMinutes(checkInterval));
        setCurrent = new HashSet<>();
        for(LocalDateTime time : mapIncoming.keySet()) {
            if(time.equals(DateTimeArithmetic.trimSeconds(nowTime))) {
                setCurrent = mapIncoming.get(time);
                break;
            }
        }
    }

    public void check() {
        logger.debug(
                "Checking changes in incoming task list"
        );

        boolean wasChanges = false;
        LocalDateTime nowTime = LocalDateTime.now();
        Map<LocalDateTime, Set<Task>> newMapIncoming =
                Tasks.calendar(generalTasks,
                nowTime.minusMinutes(1),
                nowTime.plusMinutes(checkInterval));
        Set<Task> newSetCurrent = new HashSet<>();
        if(!newMapIncoming.equals(mapIncoming)) {
            mapIncoming = newMapIncoming;
            wasChanges = true;
        }
        for(LocalDateTime time : mapIncoming.keySet()) {
            if(time.equals(DateTimeArithmetic.trimSeconds(nowTime))) {
                newSetCurrent = mapIncoming.get(time);
                break;
            }
        }
        if(!newSetCurrent.equals(setCurrent)) {
            setCurrent = newSetCurrent;
            wasChanges = true;
        }
        if(wasChanges) {
            logger.info(
                    "Found changes in incoming tasks list"
            );

            getObservers().updateAll(Event.VIEW);
        } else {
            logger.debug(
                    "Changes not found"
            );
        }
    }

    public int getCheckInterval() {
        return checkInterval;
    }

    public Map<LocalDateTime, Set<Task>> getMapIncoming() {
        return mapIncoming;
    }

    public Set<Task> getSetCurrent() {
        return setCurrent;
    }

    @Override
    public void update() {
        check();
    }
}

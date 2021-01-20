package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.DateTimeArithmetic;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Observer;

import java.time.LocalDateTime;

public class IncomingTasks extends Observable implements Observer {
    protected AbstractTaskList generalTasks;

    protected Iterable<Task> listIncoming;
    protected LinkedTaskList listCurrent;
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
        listIncoming = Tasks.incoming(generalTasks,
                LocalDateTime.now().minusMinutes(1),
                LocalDateTime.now().plusMinutes(checkInterval));
        listCurrent = new LinkedTaskList();
        for(Task task : listIncoming) {
            LocalDateTime nowTime = LocalDateTime.now();
            LocalDateTime nextTime = task.nextTimeAfter(nowTime.
                    minusMinutes(1));
            if(nextTime != null && nextTime.equals(nowTime)) {
                listCurrent.add(task);
            }
        }
    }

    public void check() {
        Iterable<Task> newIncomingList = Tasks.incoming(generalTasks,
                LocalDateTime.now().minusMinutes(1),
                LocalDateTime.now().plusMinutes(checkInterval));
        LinkedTaskList newCurrList = new LinkedTaskList();
        boolean wasChanges = false;
        if(!listIncoming.equals(newIncomingList)) {
            listIncoming = newIncomingList;
            wasChanges = true;
        }
        for(Task task : listIncoming) {
            LocalDateTime nowTime = DateTimeArithmetic.
                    trimSeconds(LocalDateTime.now());
            LocalDateTime nextTime = task.nextTimeAfter(nowTime.
                    minusMinutes(1));
            if(nextTime != null && nextTime.equals(nowTime)) {
                newCurrList.add(task);
            }
        }
        if(!listCurrent.equals(newCurrList)) {
            listCurrent = newCurrList;
            wasChanges = true;
        }
        if(wasChanges) {
            getObservers().updateAll(Event.VIEW);
        }
    }

    public int getCheckInterval() {
        return checkInterval;
    }

    public Iterable<Task> getIncomingList() {
        return listIncoming;
    }

    public LinkedTaskList getCurrentList() {
        return listCurrent;
    }

    @Override
    public void update() {
        check();
    }
}

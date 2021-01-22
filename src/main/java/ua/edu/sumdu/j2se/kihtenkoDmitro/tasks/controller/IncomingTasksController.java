package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.IncomingTasks;
import java.time.LocalDateTime;

public class IncomingTasksController extends
        ObjectController<IncomingTasks> {
    protected LocalDateTime timeLabel;

    public IncomingTasksController(IncomingTasks incomingTasks) {
        super(incomingTasks);
        handleAction = Action.INCOMING_TASKS;
        timeLabel = LocalDateTime.now();
    }

    @Override
    public Action process() {
        if(LocalDateTime.now().minusMinutes(1).
                isAfter(timeLabel)) {
            observable.check();
            timeLabel = LocalDateTime.now();
        }
        return Action.NOTHING;
    }
}

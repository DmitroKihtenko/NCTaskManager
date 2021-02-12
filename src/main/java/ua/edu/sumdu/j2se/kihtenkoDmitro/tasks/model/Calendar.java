package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class Calendar extends Observable {
    private static final Logger logger =
            Logger.getLogger(Calendar.class);

    protected AbstractTaskList taskList;
    protected SortedMap<LocalDateTime, Set<Task>> taskCalendar;

    public Calendar(AbstractTaskList taskList) {
        if(taskList == null) {
            throw new IllegalArgumentException(
                    "Tasks list parameter has null value"
            );
        }
        this.taskList = taskList;
    }

    public void setTaskList(AbstractTaskList list) {
        if(list == null) {
            throw new IllegalArgumentException(
                    "Task list parameter has null value"
            );
        }
        this.taskList = list;
    }

    public void setTaskCalendar(LocalDateTime start,
                           LocalDateTime end) {
        taskCalendar = Tasks.calendar(taskList, start, end);
        getObservers().updateAll();

        logger.debug(
                "Set new calendar list"
        );
    }

    public SortedMap<LocalDateTime, Set<Task>> getTaskCalendar() {
        return taskCalendar;
    }

    public AbstractTaskList getTaskList() {
        return taskList;
    }
}

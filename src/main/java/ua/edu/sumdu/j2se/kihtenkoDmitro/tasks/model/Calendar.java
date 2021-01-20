package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class Calendar extends Observable {
    AbstractTaskList taskList;
    SortedMap<LocalDateTime, Set<Task>> taskCalendar;

    public Calendar(AbstractTaskList list) {
        if(list == null) {
            throw new IllegalArgumentException(
                    "Tasks list parameter has null value"
            );
        }
        this.taskList = list;
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
        getObservers().updateAll(Event.VIEW);
    }

    public SortedMap<LocalDateTime, Set<Task>> getTaskCalendar() {
        return taskCalendar;
    }

    public AbstractTaskList getTaskList() {
        return taskList;
    }
}

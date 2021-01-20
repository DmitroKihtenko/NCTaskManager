package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util.StatusInput;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.TableBuffer;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.DescriptionBuffer;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Menu;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Task;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.DateTimeArithmetic;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;

import java.time.LocalDateTime;

public class TaskChangeController extends ObjectController<Task> {
    public TaskChangeController(Task task) {
        super(task);
    }

    @Override
    public Action process() {
        TaskView taskView = new TaskView(observable);
        Menu menu = new Menu(
                "Set title",
                "Set start time",
                "Set start, end time and interval",
                "Set end time",
                "Set interval",
                "Set activity",
                "Return and save",
                "Return and roll back"
        );
        menu.setObservers(observable.getObservers());
        MenuView menuView = new MenuView(menu);
        DescriptionBuffer stateBuffer = new DescriptionBuffer();
        stateBuffer.setObservers(observable.getObservers());
        StatusInput in = new StatusInput(stateBuffer);
        StatusView stateView = new StatusView(stateBuffer);
        LocalDateTime start;
        LocalDateTime end;
        int period;
        in.setLineLimit(30);
        do {
            stateBuffer.setDescriptionLines(
                    "Choose menu action number"
            );

            int choice = in.nextMenu(menu);

            switch (choice) {
                case 1:
                    stateBuffer.setDescriptionLines(
                            "Enter title of task"
                    );
                    observable.setTitle(in.nextTextLine());
                    break;
                case 2:
                    stateBuffer.setDescriptionLines(
                            "Enter date and time in format: " +
                                    LocalDateTime.now().
                                            format(Formatter.
                                                    getMainDateInput())
                    );
                    if(observable.isRepeated()) {
                        observable.setTime(in.nextTime(Formatter.
                                        getMainDateInput(),
                                LocalDateTime.now(),
                                observable.getEndTime()));
                    } else {
                        observable.setTime(in.nextTime(Formatter.
                                        getMainDateInput(),
                                DateTimeArithmetic.
                                        trimSeconds(LocalDateTime.now()),
                                LocalDateTime.MAX));
                    }
                    break;
                case 3:
                    TableBuffer fieldBuffer = new TableBuffer(
                            "Start",
                            "End",
                            "Period (in minutes)"
                    );
                    fieldBuffer.setObservers(observable.getObservers());
                    FieldsView fieldsView = new FieldsView(fieldBuffer);
                    stateBuffer.setDescriptionLines(
                            "Set 3 fields",
                            "Task time format example " +
                                    LocalDateTime.now().
                                    format(Formatter.
                                            getMainDateInput())
                    );
                    start = in.nextTime(Formatter.getMainDateInput(),
                            DateTimeArithmetic.trimSeconds(
                                    LocalDateTime.now()),
                            LocalDateTime.MAX);
                    fieldBuffer.setField(1, start.
                            format(Formatter.getMainDateOutput()),
                            2);
                    end = in.nextTime(Formatter.getMainDateInput(),
                            start.plusMinutes(1), LocalDateTime.MAX);
                    fieldBuffer.setField(2, end.format(Formatter.
                                    getMainDateOutput()), 3);
                    period = in.nextIntFrom(1, Integer.MAX_VALUE);
                    fieldBuffer.setField(3, period);
                    observable.setTime(start, end, period);
                    fieldBuffer.getObservers().detach(fieldsView,
                            Event.VIEW);
                    break;
                case 4:
                    if(observable.isRepeated()) {
                        stateBuffer.setDescriptionLines(
                                "Enter end time of repeated task"
                        );
                        observable.setTime(observable.getStartTime(),
                                in.nextTime(Formatter.
                                        getMainDateInput(),
                                observable.getStartTime(),
                                LocalDateTime.MAX),
                                observable.getRepeatInterval());
                    } else {
                        stateBuffer.setStatus(
                                "This task is unrepeatable"
                        );
                    }
                    break;
                case 5:
                    if(observable.isRepeated()) {
                        stateBuffer.setDescriptionLines(
                                "Enter interval of repeated task " +
                                        "(in minutes)"
                        );
                        observable.setTime(observable.getStartTime(),
                                observable.getEndTime(),
                                in.nextIntFrom(1, Integer.MAX_VALUE));
                    } else {
                        stateBuffer.setStatus(
                                "This task is unrepeatable"
                        );
                    }
                    break;
                case 6:
                    stateBuffer.setDescriptionLines(
                            "Enter activity (1 - true, " +
                                    "0 - false)");
                    observable.setActive(in.nextBoolean());
                    break;
                case 7:
                    observable.getObservers().detach(stateView,
                            Event.VIEW);
                    observable.getObservers().detach(menuView,
                            Event.VIEW);
                    observable.getObservers().detach(taskView,
                            Event.VIEW);
                    return Action.SUCCESS;
                default:
                    observable.getObservers().detach(stateView,
                            Event.VIEW);
                    observable.getObservers().detach(menuView,
                            Event.VIEW);
                    observable.getObservers().detach(taskView,
                            Event.VIEW);
                    return Action.FAIL;
            }
        } while (true);
    }
}

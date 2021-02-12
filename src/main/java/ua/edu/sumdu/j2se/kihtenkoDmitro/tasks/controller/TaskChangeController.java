package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import org.apache.log4j.Logger;
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
    private static final Logger logger =
            Logger.getLogger(TaskChangeController.class);

    public TaskChangeController(Task task) {
        super(task);
        handleAction = Action.CHANGE_TASK;
    }

    @Override
    public Action process() {
        logger.debug(
                "Controller " + this + " is active"
        );

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
        new MenuView(menu);

        DescriptionBuffer stateBuffer = new DescriptionBuffer();
        stateBuffer.setObservers(menu.getObservers());
        new StatusView(stateBuffer);

        StatusInput in = new StatusInput(stateBuffer);
        in.setLineLimit(30);

        TableBuffer fieldBuffer = new TableBuffer(
                "Start",
                "End",
                "Period (in minutes)"
        );
        new FieldsView(fieldBuffer);

        LocalDateTime start;
        LocalDateTime end;
        int period;

        do {
            stateBuffer.setDescriptionLines(
                    "Enter menu option number"
            );

            int choice = in.nextMenu(menu);

            switch (choice) {
                case 1:
                    stateBuffer.setDescriptionLines(
                            "Enter title of task"
                    );

                    logger.debug("Attempt to set title of task" +
                            observable
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

                    logger.debug("Attempt to set start time of" +
                            " task" + observable
                    );

                    if(observable.isRepeated()) {
                        observable.setTime(in.nextTime(Formatter.
                                        getMainDateInput(),
                                DateTimeArithmetic.
                                        trimSeconds(LocalDateTime.
                                                now()),
                                observable.getEndTime()));
                    } else {
                        observable.setTime(in.nextTime(Formatter.
                                        getMainDateInput(),
                                DateTimeArithmetic.
                                        trimSeconds(LocalDateTime.
                                                now()),
                                LocalDateTime.MAX));
                    }
                    break;
                case 3:
                    stateBuffer.setDescriptionLines(
                            "Set 3 fields",
                            "Task time format example " +
                                    LocalDateTime.now().
                                    format(Formatter.
                                            getMainDateInput())
                    );
                    fieldBuffer.clear();

                    logger.debug("Attempt to set start time, end" +
                            "end time and interval of task " +
                            observable
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
                    break;
                case 4:
                    logger.debug("Attempt to set end time of task" +
                            observable
                    );

                    if(observable.isRepeated()) {
                        stateBuffer.setDescriptionLines(
                                "Enter end time of repeated task"
                        );

                        observable.setTime(observable.getStartTime(),
                                in.nextTime(Formatter.
                                        getMainDateInput(),
                                observable.getStartTime().
                                        plusMinutes(1),
                                LocalDateTime.MAX),
                                observable.getRepeatInterval());
                    } else {
                        stateBuffer.setStatus(
                                "This task is unrepeatable"
                        );
                    }
                    break;
                case 5:
                    logger.debug("Attempt to set interval of task" +
                            observable
                    );

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
                    logger.debug("Attempt to set activity of task" +
                            observable
                    );

                    stateBuffer.setDescriptionLines(
                            "Enter activity (1 - true, " +
                                    "0 - false)");
                    observable.setActive(in.nextBoolean());
                    break;
                case 7:
                    return Action.SUCCESS;
                default:
                    return Action.FAIL;
            }
        } while (true);
    }
}

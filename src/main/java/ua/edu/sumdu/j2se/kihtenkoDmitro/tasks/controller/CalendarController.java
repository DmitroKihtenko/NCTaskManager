package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util.StatusInput;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.DateTimeArithmetic;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;

import java.time.LocalDateTime;

public class CalendarController extends
        ObjectController<Calendar> {
    private static final Logger logger =
            Logger.getLogger(CalendarController.class);

    public CalendarController(Calendar tasksCalendar) {
        super(tasksCalendar);
        handleAction = Action.CALENDAR_TASKS;
    }

    @Override
    public Action process() {
        Menu menu = new Menu(
                "Enter new time period",
                "Back"
        );
        new MenuView(menu);

        DescriptionBuffer statusBuffer = new DescriptionBuffer();
        statusBuffer.setObservers(menu.getObservers());
        new StatusView(statusBuffer);

        StatusInput in = new StatusInput(statusBuffer);
        TableBuffer fieldsBuffer = new TableBuffer(
                "Begin time",
                "End time"
        );

        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = from.plusDays(1);
        fieldsBuffer.setField(1, from.format(Formatter.
                getMainDateOutput()));
        new FieldsView(fieldsBuffer);
        fieldsBuffer.setField(2, to.format(Formatter.
                getMainDateOutput()));
        observable.setTaskCalendar(from, to);
        do {
            statusBuffer.setDescriptionLines(
                    "Enter menu option number"
            );
            int choice = in.nextMenu(menu);

            if(choice == 1) {
                statusBuffer.setDescriptionLines(
                        "Enter time interval for calendar",
                        "Format example: " + LocalDateTime.now().
                                format(Formatter.getMainDateInput())
                );
                fieldsBuffer.clear();

                logger.debug(
                        "Attempt to set time period for tasks calendar"
                );

                from = in.nextTime(Formatter.getMainDateInput(),
                        DateTimeArithmetic.trimSeconds(LocalDateTime.
                                now()), LocalDateTime.MAX);
                fieldsBuffer.setField(1, from.format(Formatter.
                        getMainDateOutput()), 2);
                to = in.nextTime(Formatter.getMainDateInput(),
                        from, LocalDateTime.MAX);
                fieldsBuffer.setField(2, to.format(Formatter.
                        getMainDateOutput()));
                observable.setTaskCalendar(from.minusMinutes(1), to);
            } else {
                return Action.MAIN_MENU;
            }
        } while(true);
    }
}

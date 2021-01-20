package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util.StatusInput;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.DateTimeArithmetic;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;

import java.time.LocalDateTime;

public class CalendarController extends
        ObjectController<Calendar> {
    public CalendarController(Calendar tasksCalendar) {
        super(tasksCalendar);
        handleAction = Action.CALENDAR_TASK;
    }

    @Override
    public Action process() {
        CalendarView calendarView = new CalendarView(observable);
        Menu menu = new Menu(
                "Enter new time period",
                "Back"
        );
        menu.setObservers(observable.getObservers());
        MenuView menuView = new MenuView(menu);
        DescriptionBuffer statusBuffer = new DescriptionBuffer();
        statusBuffer.setObservers(observable.getObservers());
        StatusView statusView = new StatusView(statusBuffer);
        StatusInput in = new StatusInput(statusBuffer);
        TableBuffer fieldsBuffer = new TableBuffer(
                "Begin time",
                "End time"
        );
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = from.plusDays(1);
        fieldsBuffer.setField(1, from.format(Formatter.
                getMainDateOutput()));
        fieldsBuffer.setField(2, to.format(Formatter.
                getMainDateOutput()));
        observable.setTaskCalendar(from, to);
        fieldsBuffer.setObservers(observable.getObservers());
        FieldsView fieldsView = new FieldsView(fieldsBuffer);
        do {
            statusBuffer.setDescriptionLines(
                    "Enter menu choice"
            );
            int choice = in.nextMenu(menu);

            if(choice == 1) {
                statusBuffer.setDescriptionLines(
                        "Enter time interval for calendar",
                        "Format example: " + LocalDateTime.now().
                                format(Formatter.getMainDateInput())
                );
                fieldsBuffer.clear();
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
                observable.getObservers().detach(menuView,
                        Event.VIEW);
                observable.getObservers().detach(statusView,
                        Event.VIEW);
                observable.getObservers().detach(fieldsView,
                        Event.VIEW);
                observable.getObservers().detach(calendarView,
                        Event.VIEW);
                return Action.MAIN_MENU;
            }
        } while(true);
    }
}

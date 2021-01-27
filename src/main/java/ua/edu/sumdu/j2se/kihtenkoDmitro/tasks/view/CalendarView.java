package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Calendar;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Task;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.TableCOut;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class CalendarView extends ConsoleView<Calendar> {
    protected TableCOut out;

    public CalendarView(Calendar tasksCalendar) {
        super(tasksCalendar);
        out = new TableCOut();
        out.setSeparatorLen(51);
        out.setColumnsWidth(15, 20, 12);
    }

    @Override
    public void update() {
        SortedMap<LocalDateTime, Set<Task>> taskCalendar =
                observable.getTaskCalendar();
        Set<Task> currentSet;
        boolean firstOut;
        out.printSeparate();
        out.printLine(
                "Completion time",
                "Title",
                "Is repeated"
        );
        for(LocalDateTime key : taskCalendar.keySet()) {
            currentSet = taskCalendar.get(key);
            firstOut = true;
            for(Task task : currentSet) {
                if(firstOut) {
                    out.printLine(
                            key.format(Formatter.
                                    getMainDateOutput()),
                            task.getTitle(),
                            task.isRepeated()
                    );
                    firstOut = false;
                } else {
                    out.printLine(
                            " ",
                            task.getTitle(),
                            task.isRepeated()
                    );
                }
            }
        }
        out.printSeparate();
    }
}

package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Task;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.TableCOut;

public class TaskView extends ConsoleView<Task> {
    protected TableCOut out;

    public TaskView(Task task) {
        super(task);
        out = new TableCOut();
        out.setSeparatorLen(95);
        out.setColumnsWidth(20, 9, 11, 16, 16, 16);
    }

    @Override
    public void update() {
        out.setColumnsAligns();
        out.printSeparate();
        out.printLine(
                "Title",
                "Is active",
                "Is repeated",
                "Start time",
                "End time",
                "Period"
        );
        out.printLine(
                observable.getTitle(),
                observable.isActive(),
                observable.isRepeated(),
                observable.getStartTime().
                        format(Formatter.getMainDateOutput()),
                observable.getEndTime().
                        format(Formatter.getMainDateOutput()),
                observable.getRepeatInterval());
        out.printSeparate();
    }
}

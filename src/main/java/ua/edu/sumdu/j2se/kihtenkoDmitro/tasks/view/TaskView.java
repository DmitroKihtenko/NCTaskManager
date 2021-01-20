package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Task;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.TableCOut;

public class TaskView extends ConsoleView<Task> {
    protected TableCOut view;

    public TaskView(Task task) {
        super(task);
        view = new TableCOut();
        view.setColumnsWidth(20, 9, 11, 16, 16, 16);
    }

    @Override
    public void update() {
        view.setColumnsAligns();
        view.printSeparate();
        view.printLine(
                "Title",
                "Is active",
                "Is repeated",
                "Start time",
                "End time",
                "Period"
        );
        view.printLine(
                observable.getTitle(),
                observable.isActive(),
                observable.isRepeated(),
                observable.getStartTime().
                        format(Formatter.getMainDateOutput()),
                observable.getEndTime().
                        format(Formatter.getMainDateOutput()),
                observable.getRepeatInterval());
        view.printSeparate();
    }
}

package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Task;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.TableCOut;

public class AllTasksView extends
        ConsoleView<AbstractTaskList> {
    protected TableCOut out;

    public AllTasksView(AbstractTaskList tasks) {
        super(tasks);
        out = new TableCOut();
        out.setColumnsWidth(3, 30, 12, 18, 18, 10);
    }

    @Override
    public void update() {
        int counter = 1;
        out.printSeparate();
        out.printLine("ACTIVE TASKS");
        out.printSeparate();
        out.printLine(
                "№",
                "Title",
                "Is repeated",
                "Start time",
                "End time",
                "Interval"
                );
        for(Task task : observable) {
            if(task.isActive()) {
                out.printLine(
                        counter,
                        task.getTitle(),
                        task.isRepeated(),
                        task.getStartTime().format(
                                Formatter.getMainDateOutput()),
                        task.getEndTime().format(
                                Formatter.getMainDateOutput()),
                        task.getRepeatInterval()
                );
                counter++;
            }
        }
        counter = 1;
        out.printSeparate();
        out.printLine("INACTIVE TASKS");
        out.printSeparate();
        out.printLine(
                "№",
                "Title",
                "Is repeated",
                "Start time",
                "End time",
                "Interval"
        );
        for(Task task : observable) {
            if(!task.isActive()) {
                out.printLine(
                        counter,
                        task.getTitle(),
                        task.isRepeated(),
                        task.getStartTime().format(
                                Formatter.getMainDateOutput()),
                        task.getEndTime().format(
                                Formatter.getMainDateOutput()),
                        task.getRepeatInterval()
                );
                counter++;
            }
        }
        out.printSeparate();
    }
}

package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.IncomingTasks;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Task;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.TableCOut;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class IncomingTasksView extends ConsoleView<IncomingTasks> {
    protected TableCOut out;

    public IncomingTasksView(IncomingTasks tasks) {
        super(tasks);
        out = new TableCOut();
        out.setSeparatorLen(48);
        out.setColumnsWidth(12, 20, 12);
    }

    @Override
    public void update() {
        Set<Task> setCurrent = observable.getSetCurrent();
        Map<LocalDateTime, Set<Task>> mapIncoming =
                observable.getMapIncoming();
        out.printSeparate();
        out.printLine("Tasks to be completed now");
        out.printSeparate();
        if(setCurrent.size() != 0) {
            out.printLine(
                    "Time",
                    "Title",
                    "Is repeated");
            for(Task task : setCurrent) {
                out.printLine(
                        LocalDateTime.now().
                                format(Formatter.getMainTimeInput()),
                        task.getTitle(),
                        task.isRepeated()
                );
            }
        } else {
            out.printLine("No tasks");
        }
        out.printSeparate();
        out.printLine("Tasks to complete in the next " +
                observable.getCheckInterval() + " minutes");
        out.printSeparate();
        if(!mapIncoming.isEmpty()) {
            out.printLine(
                    "Time",
                    "Title",
                    "Is repeated");
            for(LocalDateTime time : mapIncoming.keySet()) {
                for(Task task : mapIncoming.get(time)) {
                    out.printLine(
                            time.format(Formatter.getMainTimeInput()),
                            task.getTitle(),
                            task.isRepeated()
                    );
                }
            }
        } else {
            out.printLine("No tasks");
        }
        out.printSeparate();
    }
}

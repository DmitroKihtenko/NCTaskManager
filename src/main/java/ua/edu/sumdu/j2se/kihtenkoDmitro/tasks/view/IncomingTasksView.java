package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.IncomingTasks;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Task;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.Formatter;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.TableCOut;

public class IncomingTasksView extends ConsoleView<IncomingTasks> {
    TableCOut out;

    public IncomingTasksView(IncomingTasks tasks) {
        super(tasks);
        out = new TableCOut();
        out.setColumnsWidth(12, 20, 12);
    }

    @Override
    public void update() {
        LinkedTaskList currentList = observable.getCurrentList();
        Iterable<Task> incomingList =
                observable.getIncomingList();
        out.printSeparate();
        out.printLine("Tasks to be completed now");
        out.printSeparate();
        if(currentList.size() != 0) {
            out.printLine(
                    "Time",
                    "Title",
                    "Is repeated");
            for(Task task : currentList) {
                out.printLine(
                        task.getStartTime().
                                format(Formatter.getMainTime()),
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
        if(incomingList.iterator().hasNext()) {
            out.printLine(
                    "Time",
                    "Title",
                    "Is repeated");
            for(Task task : incomingList) {
                out.printLine(
                        task.getStartTime().
                                format(Formatter.getMainTime()),
                        task.getTitle(),
                        task.isRepeated()
                );
            }
        } else {
            out.printLine("No tasks");
        }
        out.printSeparate();
    }
}

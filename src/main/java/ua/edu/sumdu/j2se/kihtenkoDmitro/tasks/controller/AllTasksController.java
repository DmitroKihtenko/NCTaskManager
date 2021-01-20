package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util.StatusInput;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.*;

import java.time.LocalDateTime;

public class AllTasksController extends
        ObjectController<AbstractTaskList> {
    public AllTasksController(AbstractTaskList tasks) {
        super(tasks);
        handleAction = Action.ALL_TASKS;
    }

    @Override
    public Action process() {
        AllTasksView tasksView = new AllTasksView(observable);
        Menu menu = new Menu(
                "New task",
                "Change task",
                "Remove task",
                "Remove all outdated",
                "Back"
        );
        menu.setObservers(observable.getObservers());
        MenuView menuView = new MenuView(menu);
        DescriptionBuffer stateBuffer = new DescriptionBuffer();
        stateBuffer.setObservers(observable.getObservers());
        StatusView statusView = new StatusView(stateBuffer);
        StatusInput in = new StatusInput(stateBuffer);
        Task task = null;
        TaskChangeController controllerChange;
        Action result;

        do {
            stateBuffer.setDescriptionLines(
                    "Enter menu choice"
            );
            int choice = in.nextMenu(menu);

            switch(choice) {
                case 1:
                    task = new Task("New task",
                            LocalDateTime.now().plusMinutes(10));
                    task.setObservers(observable.getObservers());
                    controllerChange =
                            new TaskChangeController(task);
                    result = controllerChange.process();
                    if(result == Action.SUCCESS) {
                        observable.add(task);
                    }
                    break;
                case 2:
                    stateBuffer.setDescriptionLines(
                            "Enter number of task to change"
                    );
                    choice = in.nextIntFrom(1, observable.size());
                    try {
                        task = observable.getTask(choice - 1).
                                clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    task.setObservers(observable.getObservers());
                    controllerChange =
                            new TaskChangeController(task);
                    result = controllerChange.process();
                    if(result == Action.SUCCESS) {
                        observable.remove(observable.
                                getTask(choice - 1));
                        observable.add(task);
                    }
                    break;
                case 3:
                    stateBuffer.setDescriptionLines(
                            "Enter number of task to remove"
                    );
                    choice = in.nextIntFrom(1, observable.size());
                    observable.remove(observable.
                            getTask(choice - 1));
                    break;
                case 4:
                    observable.timeTruncate(LocalDateTime.now(),
                            LocalDateTime.MAX);
                    break;
                default:
                    observable.getObservers().detach(menuView,
                            Event.VIEW);
                    observable.getObservers().detach(statusView,
                            Event.VIEW);
                    observable.getObservers().detach(tasksView,
                            Event.VIEW);
                    return Action.MAIN_MENU;
            }
        } while (true);
    }
}

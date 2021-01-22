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
        observable.getObservers().updateAll(Event.VIEW);

        Menu menu = new Menu(
                "New task",
                "Change task",
                "Remove task",
                "Remove all outdated",
                "Back"
        );
        MenuView menuView = new MenuView(menu);

        DescriptionBuffer stateBuffer = new DescriptionBuffer();
        stateBuffer.setObservers(menu.getObservers());
        StatusView statusView = new StatusView(stateBuffer);

        StatusInput in = new StatusInput(stateBuffer);

        Task task = null;
        TaskView taskView;
        TaskChangeController controllerChange;
        Action result;

        do {
            stateBuffer.setDescriptionLines(
                    "Enter menu option number"
            );
            int choice = in.nextMenu(menu);

            switch(choice) {
                case 1:
                    task = new Task("New task",
                            LocalDateTime.now().plusMinutes(10));
                    taskView = new TaskView(task);
                    taskView.update();
                    controllerChange = new TaskChangeController(task);
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
                    taskView = new TaskView(task);
                    taskView.update();
                    controllerChange = new
                            TaskChangeController(task);
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
                    return Action.MAIN_MENU;
            }
        } while (true);
    }
}

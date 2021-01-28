package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util.StatusInput;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.*;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.*;

import java.time.LocalDateTime;

public class AllTasksController extends
        ObjectController<AbstractTaskList> {
    private static final Logger logger =
            Logger.getLogger(AllTasksController.class);

    public AllTasksController(AbstractTaskList tasks) {
        super(tasks);
        handleAction = Action.ALL_TASKS;
    }

    @Override
    public Action process() {
        logger.debug(
                "Controller " + this + " is active"
        );

        observable.getObservers().updateAll(Event.VIEW);

        Menu menu = new Menu(
                "New task",
                "Change task",
                "Remove task",
                "Remove all outdated and all inactive",
                "Back"
        );
        new MenuView(menu);

        DescriptionBuffer stateBuffer = new DescriptionBuffer();
        stateBuffer.setObservers(menu.getObservers());
        new StatusView(stateBuffer);

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

                    logger.info(
                            "Attempt to create new task"
                    );

                    result = controllerChange.process();
                    if(result == Action.SUCCESS) {
                        observable.add(task);

                        logger.info("Added new user's task to" +
                                " task list"
                        );
                    }
                    break;
                case 2:
                    stateBuffer.setDescriptionLines(
                            "Enter number of task to change"
                    );

                    choice = in.nextIntFrom(1, observable.size());
                    try {
                        logger.info(
                                "Attempt to change task " +
                                        observable.getTask(choice - 1)
                        );

                        task = observable.getTask(choice - 1).
                                clone();
                    } catch (CloneNotSupportedException e) {
                        logger.fatal("Failed attempt to create clone" +
                                " of task " + observable.
                                getTask(choice - 1), e
                        );

                        e.printStackTrace();
                    }
                    taskView = new TaskView(task);
                    taskView.update();
                    controllerChange = new
                            TaskChangeController(task);
                    result = controllerChange.process();
                    if(result == Action.SUCCESS) {
                        observable.replace(choice - 1, task);

                        logger.info(
                                "Successfully changed (replaced) " +
                                        "task " + task
                        );
                    }
                    break;
                case 3:
                    stateBuffer.setDescriptionLines(
                            "Enter number of task to remove"
                    );
                    choice = in.nextIntFrom(1, observable.size());

                    task = observable.getTask(choice - 1);
                    observable.remove(task);

                    logger.info(
                            "Successfully removed from task list " +
                                    "task " + task
                    );

                    break;
                case 4:
                    logger.info(
                            "Attempt to delete outdated tasks"
                    );

                    if(observable.timeTruncate(LocalDateTime.now(),
                            LocalDateTime.MAX)) {
                        logger.info(
                                "Successfully deleted tasks"
                        );
                    } else {
                        logger.info(
                                "Nothing to delete"
                        );
                    }
                    break;
                default:
                    return Action.MAIN_MENU;
            }
        } while (true);
    }
}

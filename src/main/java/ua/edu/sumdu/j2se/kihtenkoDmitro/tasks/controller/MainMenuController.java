package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util.StatusInput;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.IncomingTasks;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Menu;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.DescriptionBuffer;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.MenuView;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.StatusView;

public class MainMenuController extends
        ObjectController<IncomingTasks> {
    private static final Logger logger =
            Logger.getLogger(MainMenuController.class);

    public MainMenuController(IncomingTasks tasks) {
        super(tasks);
        handleAction = Action.MAIN_MENU;
    }

    @Override
    public Action process() {
        logger.debug(
                "Controller " + this + " is active"
        );

        Menu menu = new Menu(
                "Review and change all tasks list",
                "Review tasks list on period",
                "Exit and save"
        );
        new MenuView(menu);

        DescriptionBuffer stateBuffer = new DescriptionBuffer();
        stateBuffer.setObservers(menu.getObservers());
        new StatusView(stateBuffer);

        StatusInput in = new StatusInput(stateBuffer);
        stateBuffer.setDescriptionLines(
                "Enter menu option number"
        );
        int number = in.nextMenu(menu);

        switch(number) {
            case 1:
                return Action.ALL_TASKS;
            case 2:
                return Action.CALENDAR_TASKS;
            default:
                return Action.EXIT;
        }
    }
}

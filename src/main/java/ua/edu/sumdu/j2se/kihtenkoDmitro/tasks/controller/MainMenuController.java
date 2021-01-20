package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.controller.util.StatusInput;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.IncomingTasks;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Menu;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.DescriptionBuffer;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.MenuView;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.StatusView;

public class MainMenuController extends
        ObjectController<IncomingTasks> {
    public MainMenuController(IncomingTasks tasks) {
        super(tasks);
        handleAction = Action.MAIN_MENU;
    }

    @Override
    public Action process() {
        Menu menu = new Menu(
                "Review and change all tasks list",
                "Review task list on period",
                "Exit"
        );
        menu.setObservers(observable.getObservers());
        MenuView menuView = new MenuView(menu);
        DescriptionBuffer stateBuffer = new DescriptionBuffer();
        stateBuffer.setObservers(observable.getObservers());
        StatusView stateView = new StatusView(stateBuffer);
        StatusInput in = new StatusInput(stateBuffer);
        stateBuffer.setDescriptionLines(
                "Enter menu choice number"
        );
        int number = in.nextMenu(menu);
        observable.getObservers().detach(menuView, Event.VIEW);
        observable.getObservers().detach(stateView, Event.VIEW);

        switch(number) {
            case 1:
                return Action.ALL_TASKS;
            case 2:
                return Action.CALENDAR_TASK;
            default:
                return Action.EXIT;
        }
    }
}

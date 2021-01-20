package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Menu;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.SeparateCOut;

public class MenuView extends ConsoleView<Menu> {
    protected SeparateCOut view;

    public MenuView(Menu menu) {
        super(menu);
        view = new SeparateCOut();
    }

    @Override
    public void update() {
        view.printSeparate();
        view.printLine("Choose the action:");
        view.printLines(observable.getOptions());
        view.printSeparate();
    }
}

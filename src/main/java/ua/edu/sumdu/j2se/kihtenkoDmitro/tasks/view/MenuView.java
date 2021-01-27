package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.Menu;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.SeparateCOut;

public class MenuView extends ConsoleView<Menu> {
    protected SeparateCOut out;

    public MenuView(Menu menu) {
        super(menu);
        out = new SeparateCOut();
        out.setSeparatorLen(50);
    }

    @Override
    public void update() {
        out.printSeparate();
        out.printLine("Choose the action:");
        out.printLines(observable.getOptions());
        out.printSeparate();
    }
}

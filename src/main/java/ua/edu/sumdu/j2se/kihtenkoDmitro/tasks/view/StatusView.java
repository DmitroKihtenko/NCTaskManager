package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.DescriptionBuffer;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.SeparateCOut;

public class StatusView extends ConsoleView<DescriptionBuffer> {
    protected SeparateCOut out;

    public StatusView(DescriptionBuffer buffer) {
        super(buffer);
        out = new SeparateCOut();
    }

    @Override
    public void update() {
        out.printSeparate();
        out.printLines(observable.getLines());
        out.printSeparate();
    }
}

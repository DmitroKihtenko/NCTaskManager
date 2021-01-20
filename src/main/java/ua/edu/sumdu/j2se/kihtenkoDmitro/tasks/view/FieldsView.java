package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model.TableBuffer;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util.TableCOut;

public class FieldsView extends ConsoleView<TableBuffer> {
    private TableCOut out;

    public FieldsView(TableBuffer buffer) {
        super(buffer);
        out = new TableCOut();
        out.setColumnsWidth(30, 30);
    }

    @Override
    public void update() {
        out.printSeparate();
        for(int counter = 1; counter <= observable.getFieldsAmount();
        counter++) {
            out.printLine(observable.getLine(counter));
        }
        out.printSeparate();
    }
}

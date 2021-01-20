package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.util;

import java.util.Arrays;
import java.util.Iterator;

public class TableCOut extends SeparateCOut {
    protected int cellsIndent;
    protected char indentSymbol;
    protected int[] columnsWidths;
    protected Alignment[] columnsAligns;

    public enum Alignment {
        LEFT,
        RIGHT
    }

    public TableCOut() {
        columnsWidths = new int[1];
        columnsWidths[0] = separatorLen;
        columnsAligns = new Alignment[1];
        columnsAligns[0] = Alignment.LEFT;
        indentSymbol = '|';
        cellsIndent = 1;
    }

    public void setCellsIndent(int indent) {
        if(indent < 0) {
            throw new IllegalArgumentException(
                    "Negative cell indent parameter"
            );
        }
        cellsIndent = indent;
    }

    public void setIndentSymbol(char symbol) {
        if(symbol == '\n' || symbol == '\t' || symbol == '\b' ||
                symbol == '\r' || symbol == '\f') {
            throw new IllegalArgumentException(
                    "Method parameter refers to escape sequences"
            );
        }
        indentSymbol = symbol;
    }

    public void setColumnsWidth(int ... rowsWidths) {
        if(rowsWidths == null) {
            throw new IllegalArgumentException(
                    "Parameter list has null value"
            );
        }
        columnsWidths = rowsWidths;
    }

    public void setColumnsAligns(Alignment ... values) {
        if(values == null) {
            throw new IllegalArgumentException(
                    "Parameter list has null value"
            );
        }

        int amount = values.length;

        if(amount == 0) {
            columnsAligns = new Alignment[1];
            columnsAligns[0] = Alignment.LEFT;
        } else if (amount == 1) {
            columnsAligns = new Alignment[1];
            columnsAligns[0] = values[0];
        } else {
            columnsAligns = new Alignment[columnsWidths.length];
            Iterator<Alignment> alignIter = Arrays.stream(values).iterator();

            for(int counter = 0; counter < columnsWidths.length; counter++) {
                if(alignIter.hasNext()) {
                    columnsAligns[counter] = alignIter.next();
                } else {
                    columnsAligns[counter] = Alignment.LEFT;
                }
            }
        }
    }

    public void printLine(Object ... cells) {
        int width;
        Alignment align;
        String value;
        String format;
        StringBuilder indent = new StringBuilder();
        for(int counter = 0; counter < cellsIndent; counter++) {
            indent.append(indentSymbol);
        }

        if(columnsAligns.length == 1) {
            align = columnsAligns[0];

            int cycleCount = Math.min(columnsWidths.length,
                    cells.length);

            if(cycleCount != 0) {
                System.out.print(indent);
            }
            for(int counter = 0; counter < cycleCount; counter++) {
                value = cells[counter].toString();
                width = columnsWidths[counter];
                if(value.length() > width) {
                    value = value.substring(0, width - 3) + "...";
                }
                if(align == Alignment.RIGHT) {
                    format = "%" + width + "s";
                } else {
                    format = "%" + "-" + width + "s";
                }

                System.out.printf(format, value);
                System.out.print(indent);
            }
        } else {
            int cycleCount = Math.min(columnsWidths.length,
                    cells.length);

            if(cycleCount != 0) {
                System.out.print(indent);
            }
            for(int counter = 0; counter < cycleCount; counter++) {
                value = cells[counter].toString();
                width = columnsWidths[counter];
                align = columnsAligns[counter];
                if(value.length() > width) {
                    value = value.substring(0, width - 3) + "...";
                }
                if(align == Alignment.RIGHT) {
                    format = "%" + width + "s";
                } else {
                    format = "%" + "-" + width + "s";
                }

                System.out.printf(format, value);
                System.out.print(indent);
            }
        }

        System.out.printf("%n");
    }

    public int getCellsIndent() {
        return cellsIndent;
    }

    public char getIndentSymbol() {
        return indentSymbol;
    }
}

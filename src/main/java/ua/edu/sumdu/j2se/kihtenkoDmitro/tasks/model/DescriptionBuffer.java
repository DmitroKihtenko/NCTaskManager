package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;

public class DescriptionBuffer extends CInStatusBuffer {
    protected String[] descriptionLines;

    public void setDescriptionLines(String ... lines) {
        if(lines == null) {
            throw new IllegalArgumentException(
                    "Lines parameter list has null value"
            );
        }
        if(lines.length != 0) {
            descriptionLines = lines;
        }
        getObservers().updateAll(Event.VIEW);
    }

    public String[] getLines() {
        String[] returnStr;
        if(descriptionLines == null) {
            returnStr = new String[1];
            returnStr[0] = '<' + status + '>';
        } else {
            returnStr = new String[1 + descriptionLines.length];
            returnStr[0] = '<' + status + '>';
            System.arraycopy(descriptionLines, 0, returnStr,
                    1, descriptionLines.length);
        }
        return returnStr;
    }
}

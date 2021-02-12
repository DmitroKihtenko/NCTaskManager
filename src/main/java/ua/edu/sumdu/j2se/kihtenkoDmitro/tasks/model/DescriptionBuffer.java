package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;

public class DescriptionBuffer extends CInStatusBuffer {
    private static final Logger logger =
            Logger.getLogger(DescriptionBuffer.class);

    protected String[] descriptionLines;

    public void setDescriptionLines(String ... lines) {
        if(lines == null) {
            throw new IllegalArgumentException(
                    "Lines parameter list has null value"
            );
        }
        if(lines.length != 0) {
            descriptionLines = lines;

            logger.debug(
                    "Set new description lines"
            );

            getObservers().updateAll();
        }
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

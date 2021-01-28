package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;

public class TableBuffer extends CInFieldsBuffer {
    private static final Logger logger =
            Logger.getLogger(TableBuffer.class);

    protected String[] fieldsNames;

    public TableBuffer(String ... fieldsNames) {
        super(fieldsNames == null ? 1 : fieldsNames.length);
        if(fieldsNames == null) {
            throw new IllegalArgumentException(
                    "Field names parameter list has null value"
            );
        }
        for(String name : fieldsNames) {
            if(name == null) {
                throw new IllegalArgumentException(
                        "Field name in parameter list has null value"
                );
            }
        }
        this.fieldsNames = fieldsNames;

        logger.debug(
                "Set field names"
        );
    }

    public String[] getLine(int number) {
        if(number < 1 || number > getFieldsAmount()) {
            throw new IllegalArgumentException(
                    "Range of fields numbers doesn't contain received" +
                            "number parameter"
            );
        }
        String[] returnStr = new String[2];
        returnStr[0] = fieldsNames[number - 1];
        returnStr[1] = values[number - 1].toString();
        return returnStr;
    }
}

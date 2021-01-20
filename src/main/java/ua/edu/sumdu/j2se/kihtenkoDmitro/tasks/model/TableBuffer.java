package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

public class TableBuffer extends CInFieldsBuffer {
    protected String[] fieldNames;

    public TableBuffer(String ... fieldNames) {
        super(fieldNames.length);
        if(fieldNames == null) {
            throw new IllegalArgumentException(
                    "Field names parameter list has null value"
            );
        }
        for(String name : fieldNames) {
            if(name == null) {
                throw new IllegalArgumentException(
                        "Field name in parameter list has null value"
                );
            }
        }
        this.fieldNames = fieldNames;
    }

    public String[] getLine(int number) {
        if(number < 1 || number > getFieldsAmount()) {
            throw new IllegalArgumentException(
                    "Range of fields numbers doesn't contain received" +
                            "number parameter"
            );
        }
        String[] returnStr = new String[2];
        returnStr[0] = fieldNames[number - 1];
        returnStr[1] = values[number - 1].toString();
        return returnStr;
    }
}

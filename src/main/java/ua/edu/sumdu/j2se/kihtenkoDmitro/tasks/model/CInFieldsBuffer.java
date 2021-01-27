package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;

public class CInFieldsBuffer extends Observable {
    private static final Logger logger =
            Logger.getLogger(CInFieldsBuffer.class);

    protected Object[] values;
    protected int currentField;
    protected static String currentFieldStr;
    protected static String emptyFieldStr;

    static {
        currentFieldStr = "<current enter>";
        emptyFieldStr = "<not entered>";
    }

    public CInFieldsBuffer(int fieldsAmount) {
        if(fieldsAmount <= 0) {
            throw new IllegalArgumentException(
                    "Excepted non-negative amount of fields"
            );
        }
        values = new Object[fieldsAmount];
        clear();
    }

    public void setField(int number, Object field) {
        setField(number, field, Integer.MIN_VALUE);
    }

    public void setField(int number, Object field, int nextCurrent) {
        if (number < 1 || number > getFieldsAmount()) {
            throw new IllegalArgumentException(
                    "Range of fields numbers doesn't contain received" +
                            "number parameter"
            );
        } else if (field == null) {
            throw new IllegalArgumentException(
                    "Field value parameter has null value"
            );
        }
        values[number - 1] = field;

        logger.debug(
                "Saved new field " + field + " by index " +
                        (number - 1)
        );

        if(nextCurrent == Integer.MIN_VALUE) {
            getObservers().updateAll();
        } else {
            setCurrentField(nextCurrent);
        }
    }

    public void setCurrentField(int number) {
        if(number < 1 || number > getFieldsAmount()) {
            throw new IllegalArgumentException(
                    "Range of fields numbers doesn't contain received"
                            + " number parameter"
            );
        }
        currentField = number;
        values[currentField - 1] = currentFieldStr;

        logger.debug(
                "Set current field index " + (number - 1)
        );

        getObservers().updateAll();
    }

    public Object getField(int number) {
        if(number < 1 || number > getFieldsAmount()) {
            throw new IllegalArgumentException(
                    "Range of fields numbers doesn't contain received" +
                            "number parameter"
            );
        }
        return values[number - 1];
    }

    public int getCurrentField() {
        return currentField;
    }

    public void clear() {
        values[0] = currentFieldStr;
        for(int counter = 1; counter < getFieldsAmount(); counter++) {
            values[counter] = emptyFieldStr;
        }

        logger.debug(
                "Cleared fields buffer"
        );

        getObservers().updateAll();
    }

    public int getFieldsAmount() {
        return values.length;
    }
}

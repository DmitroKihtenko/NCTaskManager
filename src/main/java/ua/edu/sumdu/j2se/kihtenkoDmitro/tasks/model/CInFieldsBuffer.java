package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;

public class CInFieldsBuffer extends Observable {
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
        if(nextCurrent == Integer.MIN_VALUE) {
            getObservers().updateAll(Event.VIEW);
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
        getObservers().updateAll(Event.VIEW);
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
        setCurrentField(1);
        for(int counter = 1; counter < getFieldsAmount(); counter++) {
            values[counter] = emptyFieldStr;
        }
        getObservers().updateAll(Event.VIEW);
    }

    public int getFieldsAmount() {
        return values.length;
    }
}

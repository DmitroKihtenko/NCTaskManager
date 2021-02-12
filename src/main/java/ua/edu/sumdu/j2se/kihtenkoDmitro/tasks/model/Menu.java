package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;

public class Menu extends Observable {
    private static final Logger logger =
            Logger.getLogger(Menu.class);

    protected int size;
    protected String[] options;

    public Menu(String ... options) {
        setOptions(options);
    }

    public void setOptions(String ... options) {
        if(options == null) {
            throw new IllegalArgumentException(
                    "String parameters list has null value"
            );
        }

        if(options.length == 0) {
            size = 1;
            this.options = new String[size];
            this.options[0] = "<no info>";
        } else {
            size = options.length;
            this.options = new String[size];
            for(int counter = 0; counter < size; counter++) {
                if(options[counter] == null) {
                    throw new IllegalArgumentException(
                            "String parameter in parameter's " +
                                    "list has null value"
                    );
                }
                this.options[counter] = options[counter];
            }
        }

        logger.debug(
                "Set new menu options"
        );

        getObservers().updateAll();
    }

    public String[] getOptions() {
        String[] returnArr = new String[size];

        for(int counter = 0; counter < size; counter++) {
            returnArr[counter] = counter + 1 +
                    ". " + options[counter];
        }
        return returnArr;
    }

    public int getSize() {
        return size;
    }
}

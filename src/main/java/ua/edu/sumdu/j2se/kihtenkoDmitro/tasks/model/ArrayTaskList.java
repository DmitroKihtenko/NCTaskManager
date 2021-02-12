package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList {
    private static final Logger logger =
            Logger.getLogger(ArrayTaskList.class);

    /**
     * Amount of added or deleted tasks after which the array memory is changed.
     */
    private final static int RESIZE_INTERVAL = 5;
    private Task[] taskArr;

    static {
        type = ListTypes.types.ARRAY;
    }

    public ArrayTaskList() {
        taskArr = new Task[RESIZE_INTERVAL];
    }

    public void add(Task task) {
        if(task == null) {
            throw new NullPointerException(
                    "Task object parameter has null value!"
            );
        }

        if(taskArr.length == taskAmount) {
            Task[] tempArr = new Task[taskAmount + RESIZE_INTERVAL];

            System.arraycopy(taskArr, 0, tempArr, 0, taskAmount);
            taskArr = tempArr;
        }

        taskArr[taskAmount] = task;
        taskAmount++;
        getObservers().updateAll();

        logger.debug(
                "Added new task " + task
        );
    }

    public boolean remove(Task task) {
        if(task == null) {
            throw new NullPointerException(
                    "Task object parameter has null value!"
            );
        }

        boolean searchStatus = false;
        int delIndex;

        for(delIndex = 0; delIndex < taskAmount; delIndex++) {
            if(taskArr[delIndex].equals(task)) {
                searchStatus = true;
                break;
            }
        }

        if(!searchStatus) {
            logger.debug("Removing task not found");

            return false;
        }

        taskArr[delIndex] = null;
        taskAmount--;

        if(delIndex != taskAmount) {
            System.arraycopy(taskArr, delIndex + 1, taskArr, delIndex,
                    taskAmount - delIndex);
        }

        if(taskArr.length - RESIZE_INTERVAL == taskAmount &&
                taskAmount != 0) {
            Task[] tempArr = new Task[taskAmount];

            System.arraycopy(taskArr, 0, tempArr, 0, taskAmount);
            taskArr = tempArr;

            logger.debug("List resized to size " + taskArr.length);
        }

        getObservers().updateAll();

        logger.debug(
                "Removed task " + task
        );

        return true;
    }

    /**
     * After using remove task method indexes of specific objects can change.
     */
    public Task getTask(int index) {
        if(index < 0 || index >= taskAmount) {
            throw new IndexOutOfBoundsException(
                    "Invalid ArrayTaskList index!"
            );
        }

        return taskArr[index];
    }

    @Override
    public void replace(int index, Task task) {
        if(index < 0 || index >= taskAmount) {
            throw new IndexOutOfBoundsException(
                    "Invalid ArrayTaskList index!"
            );
        }

        if(!taskArr[index].equals(task)) {
            taskArr[index] = task;
            getObservers().updateAll();

            logger.debug(
                    "Replaced task " + task
            );
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < taskAmount;
            }

            @Override
            public Task next() {
                if(index == taskAmount) {
                    throw new NoSuchElementException(
                            "Iterator reached last position!"
                    );
                }
                return taskArr[index++];
            }

            @Override
            public void remove() {
                if(index == 0) {
                    throw new IllegalStateException(
                            "Needs calling of next() iterator method!"
                    );
                }

                index--;

                taskArr[index] = null;
                taskAmount--;

                if(index != taskAmount) {
                    System.arraycopy(taskArr, index + 1, taskArr, index,
                            taskAmount - index);
                }

                if(taskArr.length - RESIZE_INTERVAL == taskAmount &&
                        taskAmount != 0) {
                    Task[] tempArr = new Task[taskAmount];

                    System.arraycopy(taskArr, 0, tempArr, 0, taskAmount);
                    taskArr = tempArr;
                }
            }
        };
    }

    @Override
    public ArrayTaskList clone() {
        ArrayTaskList retObj = new ArrayTaskList();
        for(int counter = 0; counter < taskAmount; counter++) {
            retObj.add(taskArr[counter]);
        }
        return retObj;
    }

    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(taskArr, 0, taskAmount);
    }
}

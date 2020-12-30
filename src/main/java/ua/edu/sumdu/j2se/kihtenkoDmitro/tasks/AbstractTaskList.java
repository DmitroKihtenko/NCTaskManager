package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks;

import java.util.Iterator;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {
    protected int taskAmount;

    /**
     * Constant value for all objects of successor's classes. Initializes in successors.
     */
    protected static ListTypes.types type;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract Stream<Task> getStream();

    public int size() {
        return taskAmount;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) {
            return false;
        }
        if (this == otherObject) {
            return true;
        }
        if (getClass() != otherObject.getClass()) {
            return false;
        }
        if(taskAmount != ((AbstractTaskList)otherObject).taskAmount) {
            return false;
        }

        Iterator otherIt = ((AbstractTaskList)otherObject).iterator();
        for(Task thisIt : this) {
            if(!thisIt.equals(otherIt.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskAmount;

        for(Task task : this) {
            result ^= task.hashCode();
        }

        if(type == ListTypes.types.ARRAY) {
            result = ~result;
        }

        return result;
    }

    @Override
    public String toString() {
        Iterator<Task> it = iterator();
        StringBuilder returnStr = new StringBuilder();
        int objNum = 0;

        if(type == ListTypes.types.ARRAY) {
            returnStr.append("ArrayTaskList.class#");
        } else {
            returnStr.append("LinkedTaskList.class#");
        }
        returnStr.append(taskAmount);

        while(it.hasNext()) {
            returnStr.append("#Object");
            returnStr.append(objNum);
            returnStr.append("#");
            returnStr.append(it.next().toString());
            objNum++;
        }

        return new String(returnStr);
    }
}

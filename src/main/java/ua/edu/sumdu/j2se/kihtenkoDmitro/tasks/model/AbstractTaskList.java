package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract class AbstractTaskList extends Observable
        implements Iterable<Task>, Cloneable {
    private static final Logger logger =
            Logger.getLogger(AbstractTaskList.class);

    protected int taskAmount;

    /**
     * Constant value for all objects of successor's classes. Initializes in successors.
     */
    protected static ListTypes.types type;

    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    public abstract Stream<Task> getStream();
    public abstract void replace(int index, Task task);

    public boolean timeTruncate(LocalDateTime from, LocalDateTime to) {
        Iterable<Task> actualTasks = Tasks.incoming(this, from, to);
        Iterator<Task> listIterator = this.iterator();
        Task thisTask;
        boolean wasChanged = false;

        outsideLabel:
        while(listIterator.hasNext()) {
            thisTask = listIterator.next();
            for(Task actualTask : actualTasks) {
                if(thisTask == actualTask) {
                    continue outsideLabel;
                }
            }
            listIterator.remove();
            wasChanged = true;
        }
        if(wasChanged) {
            getObservers().updateAll();

            logger.debug("Successfully truncated list in range [" +
                    from + "; " + to + "]");
        }

        return wasChanged;
    }

    public int size() {
        return taskAmount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        if(taskAmount != ((AbstractTaskList)other).taskAmount) {
            return false;
        }

        Iterator otherIt = ((AbstractTaskList)other).iterator();
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

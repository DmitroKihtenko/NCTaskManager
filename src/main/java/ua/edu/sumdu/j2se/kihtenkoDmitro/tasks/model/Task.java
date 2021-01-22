package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.model;

import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.service.DateTimeArithmetic;
import ua.edu.sumdu.j2se.kihtenkoDmitro.tasks.view.Event;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Class Task for objects task.
 * @author Kikhtenko Dmitro
 * @version 1.0
 */
public class Task extends Observable implements Cloneable, Externalizable {

    /**
     * Stores a title of task.
     */
    private String title;

    /**
     * If task is repeated ({@link Task#isPeriodical}) stores a time of task start.
     * If task is not repeated stores a time of task completion.
     * Seconds are trimmed.
     */
    private LocalDateTime start;

    /**
     * Stores the end time of repeated task, seconds are trimmed.
     */
    private LocalDateTime end;

    /**
     * Stores an interval of task repeat in minutes.
     */
    private int interval;

    /**
     * Stores a state of task activity.
     */
    private boolean isActive;

    /**
     * Indicates whether the task is repeated.
     */
    private boolean isPeriodical;

    public Task() {
        new Task("no info", LocalDateTime.now());
    }

    /**
     * Constructor for non repeated tasks (task activity defaults as false).
     * @param title title of the task
     * @param time time of completion of the task
     * @exception IllegalArgumentException if LocalDateTime object has null value
     * @see Task#Task(String, LocalDateTime, LocalDateTime, int) constructor for repeated tasks
     */
    public Task(String title, LocalDateTime time) {
        setTitle(title);
        setTime(time);
    }

    /**
     * Constructor for repeated tasks (task activity defaults as false).
     * @param title title of the task
     * @param start start time of completion period
     * @param end end time of completion period
     * @param interval interval of task completion
     * @exception IllegalArgumentException if start time more or equal end time or any LocalDateTime object has null value
     * @see Task#Task(String, LocalDateTime) constructor for non-repeated tasks
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
        setTitle(title);
        setTime(start, end, interval);
    }

    /**
     * Getter for title of task.
     * @return title of task
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for title task.
     * @param title title of task
     */
    public void setTitle(String title) {
        this.title = title;
        getObservers().updateAll(Event.VIEW);
    }

    /**
     * Getter for completion time of task.
     * @return start time for repeated task and completion time for non-repeated
     */
    public LocalDateTime getTime() {
        return this.start;
    }

    /**
     * Setter for activity state of task.
     * @param active Sets the activity of task
     */
    public void setActive(boolean active) {
        this.isActive = active;
        getObservers().updateAll(Event.VIEW);
    }

    /**
     * Getter for activity of task.
     * @return is the task active
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Setter for completion time of non-repeated task (sets task as non-repeated).
     * @param time completion time for non-repeated task
     * @exception IllegalArgumentException if any LocalDateTime object has null value
     * @see Task#setTime(LocalDateTime, LocalDateTime, int) setter for repeated task
     */
    public void setTime(LocalDateTime time) {
        if(time == null) {
            throw new IllegalArgumentException(
                    "LocalDateTime parameter has null value!"
            );
        }
        time = DateTimeArithmetic.trimSeconds(time);
        this.start = time;
        if(this.isPeriodical) {
            this.isPeriodical = false;
        }
        getObservers().updateAll(Event.VIEW);
    }

    /**
     * Setter for time parameters of repeated task (sets task as repeated).
     * @param start start time of completion period
     * @param end end time of completion period
     * @param interval interval of task completion
     * @exception IllegalArgumentException if start time more or equal end time or any LocalDateTime object has null value
     * @see Task#setTime(LocalDateTime) setter for non-repeated task
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if(start == null || end == null) {
            throw new IllegalArgumentException(
                    "Time parameter has null value"
            );
        }
        start = DateTimeArithmetic.trimSeconds(start);
        end = DateTimeArithmetic.trimSeconds(end);
        if(start.isAfter(end) || start.isEqual(end) || interval <= 0) {
            throw new IllegalArgumentException(
                    "Time interval logic is violated!"
            );
        }
        this.start = start;
        this.end = end;
        this.interval = interval;

        if(!this.isPeriodical) {
            this.isPeriodical = true;
        }
        getObservers().updateAll(Event.VIEW);
    }

    /**
     * Getter for start time of repeated task.
     * @return start time for repeated task and time of completion for non-repeated
     */
    public LocalDateTime getStartTime() {
        return this.start;
    }

    /** Getter for end time of repeated task.
     * @return end time for repeated task and time of completion for non-repeated
     */
    public LocalDateTime getEndTime() {
        if(this.isPeriodical) {
            return this.end;
        } else {
            return this.start;
        }
    }

    /** Getter for interval of repeated task.
     * @return interval for repeated task and 0 for non-repeated
     */
    public int getRepeatInterval() {
        if(this.isPeriodical) {
            return this.interval;
        } else {
            return 0;
        }
    }

    /**
     * Getter for repeat state of task.
     * @return repeat state of task
     */
    public boolean isRepeated() {
        return this.isPeriodical;
    }

    /**
     * Method that returns next time of task completion after given time.
     * @param current the time relative to which you want to find the completion time
     * @exception IllegalArgumentException if LocalDateTime object has null value
     * @return time of next task completion after current time. If task is not active returns null. If task will not run after current time returns null
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if(current == null) {
            throw new IllegalArgumentException(
                    "LocalDateTime object has null value!"
            );
        }
        if(!this.isActive) {
            return null;
        }

        current = DateTimeArithmetic.trimSeconds(current);
        if(this.isPeriodical) {
            LocalDateTime nextTime = this.start;

            while(!current.isBefore(nextTime)) {
                if(nextTime.plusMinutes(this.interval).isAfter(this.end)) {
                    return null;
                } else {
                    nextTime = nextTime.plusMinutes(this.interval);
                }
            }

            return nextTime;
        } else {
            if(current.isBefore(this.start)) {
                return this.start;
            } else {
                return null;
            }
        }
    }

    /**
     * Equals method for Task class objects.
     * @param otherObject task class object for comparison
     * @return true if tasks have same field values, else return false
     */
    @Override
    public boolean equals(Object otherObject) {
        if(otherObject == null) {
            return false;
        }
        if(this == otherObject) {
            return true;
        }
        if(getClass() != otherObject.getClass()) {
            return false;
        }
        return title.equals(((Task) otherObject).title) &&
                start == ((Task) otherObject).start &&
                end == ((Task) otherObject).end &&
                interval == ((Task) otherObject).interval &&
                isActive == ((Task) otherObject).isActive &&
                isPeriodical == ((Task) otherObject).isPeriodical;
    }

    /**
     * Hash code method for Task class objects.
     * @return Unique hash code for Task class object (there may be collisions)
     */
    @Override
    public int hashCode() {
        int result = 0;

        result ^= title.hashCode();
        result ^= start.hashCode();
        result ^= getEndTime().hashCode();
        result += interval;
        if(isActive) {
            result >>= 3;
        } else {
            result >>= 5;
        }
        if(isPeriodical) {
            result <<= 7;
        } else {
            result <<= 11;
        }

        return result;
    }

    /**
     * To string method for Task class objects.
     * @return string that consists of class name and all object fields through the symbol #
     */
    @Override
    public String toString() {
        return "Task.class#" + title + "#" + start + "#" + end
                + "#" + interval + "#" + isActive + "#" + isPeriodical;
    }

    /**
     * Clone method for the Task class objects.
     * @return clone of the object (pointer on this object)
     * @throws CloneNotSupportedException if class is not implements Cloneable() interface
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task)super.clone();
    }

    /**
     * Overrode method for control Task objects serialization
     * @param out stream where objects are being writing
     * @exception IOException if something wrong with writing into stream
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(title.length());
        out.writeObject(title);
        if(isActive) {
            out.writeInt(1);
        } else {
            out.writeInt(0);
        }
        out.writeInt(getRepeatInterval());
        out.writeLong(start.toEpochSecond(ZoneOffset.UTC));
        if(isRepeated()) {
            out.writeLong(end.toEpochSecond(ZoneOffset.UTC));
        }
    }

    /**
     * Overrode method for control Task objects deserialization
     * @param in stream from which objects are being reading
     * @exception IOException if something wrong with reading from stream
     * @exception ClassNotFoundException if something wrong with deserialization String field title
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        in.readInt();
        title = (String)in.readObject();
        int activity = in.readInt();
        isActive = activity == 1;
        interval = in.readInt();
        start = LocalDateTime.ofEpochSecond(in.readLong(),
                0, ZoneOffset.UTC);
        if(interval == 0) {
            isPeriodical = false;
        } else {
            isPeriodical = true;
            end = LocalDateTime.ofEpochSecond(in.readLong(),
                    0, ZoneOffset.UTC);
        }
    }
}

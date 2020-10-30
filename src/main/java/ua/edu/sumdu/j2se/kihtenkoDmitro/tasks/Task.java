package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks;

public class Task {

    private String title;
    private boolean isActive;

    private int start;
    private int end;
    private int interval;
    private boolean isPeriodical;

    public Task(String title, int time) {
        setTitle(title);
        setTime(time);
    }

    public Task(String title, int start, int end, int interval) {
        setTitle(title);
        setTime(start, end, interval);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTime() {
        return this.start;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setTime(int time) {
        this.start = time;

        if(this.isPeriodical) {
            this.isPeriodical = false;
        }
    }

    public void setTime(int start, int end, int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;

        if(!this.isPeriodical) {
            this.isPeriodical = true;
        }
    }

    public int getStartTime() {
        return this.start;
    }

    public int getEndTime() {
        if(this.isPeriodical) {
            return this.end;
        } else {
            return this.start;
        }
    }

    public int getRepeatInterval() {
        if(this.isPeriodical) {
            return this.interval;
        } else {
            return 0;
        }
    }

    public boolean isRepeated() {
        return this.isPeriodical;
    }

    public int nextTimeAfter(int current) {
        if(!this.isActive) {
            return -1;
        }

        if(this.isPeriodical) {
            int nextTime = this.start;

            while(current >= nextTime) {
                if(nextTime + this.interval <= this.end) {
                    nextTime += this.interval;
                } else {
                    return -1;
                }
            }

            return nextTime;
        } else {
            if(current < this.start) {
                return this.start;
            } else {
                return -1;
            }
        }
    }
}
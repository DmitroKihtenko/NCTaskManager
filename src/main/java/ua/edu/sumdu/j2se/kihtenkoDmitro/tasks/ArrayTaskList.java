package ua.edu.sumdu.j2se.kihtenkoDmitro.tasks;

public class ArrayTaskList extends AbstractTaskList {
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
        }

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
}

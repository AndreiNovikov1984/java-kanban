package Tasks;

import java.util.ArrayList;

public class EpicTask extends Task {

    protected final TypeTask taskType = TypeTask.EPICTASK;
    private ArrayList<Integer> subTaskIdentificator;

    public EpicTask(int taskId, String taskName, String taskDescription) {
        super(taskId, taskName, taskDescription);
    }

    public EpicTask(int taskId, String taskName, StatusTask taskStatus, String taskDescription) {
        super(taskId, taskName, taskStatus, taskDescription);
    }

    @Override
    public String toString() {
        return taskType + "{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", subTaskIdentificator=" + subTaskIdentificator +
                '}';
    }

    public ArrayList<Integer> getSubTaskIdentificator() {
        return subTaskIdentificator;
    }

    @Override
    public TypeTask getTaskType() {
        return taskType;
    }

    public void setSubTaskIdentificator(ArrayList<Integer> subTaskIdentificator) {
        this.subTaskIdentificator = subTaskIdentificator;
    }

}


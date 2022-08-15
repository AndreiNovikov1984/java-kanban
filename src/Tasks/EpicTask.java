package Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EpicTask extends Task {

    protected final TypeTask taskType = TypeTask.EPICTASK;
    private ArrayList<Integer> subTaskIdentificator;

    public EpicTask(int taskId, String taskName, String taskDescription) {
        super(taskId, taskName, taskDescription);
    }

    public EpicTask(int taskId, String taskName, StatusTask taskStatus, String taskDescription, String time,
                    String durationTask) {
        super(taskId, taskName, taskStatus, taskDescription, time, durationTask);
    }

    @Override
    public String toString() {
        return taskType + "{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskDuration='" + (taskDuration == null ? null : taskDuration.toMinutes()) + '\'' +
                ", taskStartTime='" + (taskStartTime == null ? null : taskStartTime.format(formatter)) + '\'' +
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

    public void setEpicTaskStartTime(LocalDateTime taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public void setEpicTaskDuration(Duration taskDuration) {
        this.taskDuration = taskDuration;
    }
}


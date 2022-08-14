package Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Task {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
    protected int taskId;
    protected final TypeTask taskType = TypeTask.TASK;
    protected final String taskName;
    protected final String taskDescription;
    protected StatusTask taskStatus = StatusTask.NEW;
    protected LocalDateTime taskStartTime;
    protected Duration taskDuration;
    protected LocalDateTime taskEndTime;


    public Task(int taskId, String taskName, String taskDescription) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public Task(int taskId, String taskName, String taskDescription, String time, long durationTask) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStartTime = LocalDateTime.parse(time, formatter);
        this.taskDuration = Duration.ofMinutes(durationTask);

    }

    public Task(int taskId, String taskName, StatusTask taskStatus, String taskDescription, String time, String durationTask) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.taskDescription = taskDescription;
        this.taskStartTime = (time.equals("null") ? null : LocalDateTime.parse(time));
        this.taskDuration = (durationTask.equals("null") ? null : Duration.ofMinutes(Long.parseLong(durationTask)));
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public TypeTask getTaskType() {
        return taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public StatusTask getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(StatusTask taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Duration getTaskDuration() {
        return taskDuration;
    }

    public LocalDateTime getTaskStartTime() {
        return taskStartTime;
    }

    public LocalDateTime getTaskEndTime() {
        return taskEndTime = taskStartTime.plus(taskDuration);
    }

    @Override
    public String toString() {
        return taskType + "{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription.length() + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskDuration='" + taskDuration.toMinutes() + '\'' +
                ", taskStartTime='" + taskStartTime.format(formatter) + '\'' +
                '}';
    }
}

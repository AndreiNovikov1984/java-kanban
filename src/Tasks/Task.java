package Tasks;

public class Task {
    protected int taskId;
    protected final TypeTask taskType = TypeTask.TASK;
    protected final String taskName;
    protected final String taskDescription;
    protected StatusTask taskStatus = StatusTask.NEW;

    public Task(int taskId, String taskName, String taskDescription) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public Task(int taskId, String taskName, StatusTask taskStatus, String taskDescription) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.taskDescription = taskDescription;
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

    @Override
    public String toString() {
        return taskType + "{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription.length() + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}

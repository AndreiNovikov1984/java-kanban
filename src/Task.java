public class Task {
    protected int taskId;
    protected final String taskName;
    protected final String taskDescription;
    protected StatusTask taskStatus = StatusTask.NEW;

    public Task(int taskId, String taskName, String taskDescription) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription.length() + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}

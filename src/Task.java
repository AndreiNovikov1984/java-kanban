import java.util.Objects;

public class Task {
    protected String taskName;
    protected String taskDescription;
    protected String taskStatus;


    public Task(String taskName, String taskDescription, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskName, task.taskName) && taskDescription.equals(task.taskDescription) && Objects.equals(taskStatus, task.taskStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskDescription, taskStatus);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription.length() + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}

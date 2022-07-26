package Tasks;

public class SubTask extends Task {

    protected final TypeTask taskType = TypeTask.SUBTASK;
    private Integer epikTaskIdentificator;

    public SubTask(int taskId, String taskName, String taskDescription, Integer epikTaskIdentificator) {
        super(taskId, taskName, taskDescription);
        this.epikTaskIdentificator = epikTaskIdentificator;
    }
    public SubTask(int taskId, String taskName, StatusTask taskStatus, String taskDescription, Integer epikTaskIdentificator) {
        super(taskId, taskName, taskStatus, taskDescription);
        this.epikTaskIdentificator = epikTaskIdentificator;
    }

    public Integer getEpikTaskIdentificator() {
        return epikTaskIdentificator;
    }

    @Override
    public TypeTask getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return taskType + "{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", epikTaskIdentificator=" + epikTaskIdentificator +
                '}';
    }
}

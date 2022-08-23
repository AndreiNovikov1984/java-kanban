package Tasks;

public class SubTask extends Task {

 //   protected final TypeTask subTaskType = TypeTask.SUBTASK;
    private Integer epicTaskIdentificator;

    public SubTask(int taskId, String taskName, String taskDescription, String time, long durationTask, Integer epicTaskIdentificator) {
        super(taskId, taskName, taskDescription, time, durationTask);
        this.epicTaskIdentificator = epicTaskIdentificator;
        this.taskType = TypeTask.SUBTASK;
    }

    public SubTask(int taskId, String taskName, StatusTask taskStatus, String taskDescription, String time, String durationTask, Integer epicTaskIdentificator) {
        super(taskId, taskName, taskStatus, taskDescription, time, durationTask);
        this.epicTaskIdentificator = epicTaskIdentificator;
        this.taskType = TypeTask.SUBTASK;
    }

    public Integer getEpicTaskIdentificator() {
        return epicTaskIdentificator;
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
                ", taskDuration='" + taskDuration.toMinutes() + '\'' +
                ", taskStartTime='" + taskStartTime.format(formatter) + '\'' +
                ", epikTaskIdentificator=" + epicTaskIdentificator +
                '}';
    }
}

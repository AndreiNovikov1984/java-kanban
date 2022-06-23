package Tasks;

public class SubTask extends Task {

    private Integer epikTaskIdentificator;

    public SubTask(int taskId, String taskName, String taskDescription, Integer epikTaskIdentificator) {
        super(taskId, taskName, taskDescription);
        this.epikTaskIdentificator = epikTaskIdentificator;
    }

    public Integer getEpikTaskIdentificator() {
        return epikTaskIdentificator;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", epikTaskIdentificator=" + epikTaskIdentificator +
                '}';
    }
}

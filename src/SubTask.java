import java.util.Objects;

public class SubTask extends Task {

    protected Integer epikTaskIdentificator;

    public SubTask(String taskName, String taskDescription, String taskStatus, Integer epikTaskIdentificator) {
        super(taskName, taskDescription, taskStatus);
        this.epikTaskIdentificator = epikTaskIdentificator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubTask subTask = (SubTask) o;
        return Objects.equals(epikTaskIdentificator, subTask.epikTaskIdentificator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epikTaskIdentificator);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", epikTaskIdentificator=" + epikTaskIdentificator +
                '}';
    }
}

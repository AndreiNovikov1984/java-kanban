import java.util.ArrayList;
import java.util.Objects;

public class EpicTask extends Task {

    protected ArrayList<Integer> subTaskIdentificator;


    public EpicTask(String taskName, String taskDescription, String taskStatus, ArrayList<Integer> subTaskIdentificator) {
        super(taskName, taskDescription, taskStatus);
        this.subTaskIdentificator = subTaskIdentificator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EpicTask epicTask = (EpicTask) o;
        return Objects.equals(subTaskIdentificator, epicTask.subTaskIdentificator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIdentificator);
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", subTaskIdentificator=" + subTaskIdentificator +
                '}';
    }
}


import java.util.ArrayList;

public class EpicTask extends Task {

    private ArrayList<Integer> subTaskIdentificator;


    public EpicTask(int taskId, String taskName, String taskDescription, ArrayList<Integer> subTaskIdentificator) {
        super(taskId, taskName, taskDescription);
        this.subTaskIdentificator = subTaskIdentificator;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", subTaskIdentificator=" + subTaskIdentificator +
                '}';
    }

    public ArrayList<Integer> getSubTaskIdentificator() {
        return subTaskIdentificator;
    }

    public void setSubTaskIdentificator(ArrayList<Integer> subTaskIdentificator) {
        this.subTaskIdentificator = subTaskIdentificator;
    }

}


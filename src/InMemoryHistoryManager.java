import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private ArrayList<Task> listHistory = new ArrayList<>();


    public void add(Task task) {
        listHistory.add(task);
        if (listHistory.size() > 10) listHistory.remove(0);
    }

    public void getHistory() {
        if (listHistory.size() == 0) {
            System.out.println("Не было просмотрено ни одной задачи");
        } else {
            System.out.println("Были просмотрены следующие задачи:");
            for (Task taskHistory : listHistory) {
                System.out.println("ID задачи " + taskHistory.taskId + ", название задачи " + taskHistory.taskName);
            }
        }
    }
}

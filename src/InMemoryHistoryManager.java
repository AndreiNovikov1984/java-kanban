import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private LinkedList<Task> listHistory = new LinkedList<>();


    public void add(Task task) {
        listHistory.addFirst(task);
        if (listHistory.size() > 10) listHistory.pollLast();
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

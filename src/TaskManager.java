import java.util.ArrayList;

public interface TaskManager {

    public void getlistTask();  // метод вывода списка задач?

    public void getlistEpicTask();  // метод вывода списка эпиков

    public void getlistSubTask();  // метод вывода списка подзадач

    public void clearTaskList();   // метод удаления всего списка задач

    public void clearEpicTaskList();   // метод удаления всего списка эпиков

    public void clearSubTaskList();   // метод удаления всего списка подзадач

    public void getTaskByNumber(int taskIdentificator);  // метод получения данных о задаче по идентификатору

    public void createTask(Task task);       // метод создания задачи

    public void createTask(EpicTask epicTask);   // метод создания эпика

    public void createTask(SubTask subTask);        // метод создания подзадачи

    public void refreshTask(Task task);                     // метод обновление задачи

    public void refreshTask(EpicTask epicTask);                 // метод обновление эпика

    public void refreshTask(SubTask subTask);        // метод обновление подзадачи

    public void clearTaskByNumber(int taskIdentificator);   // метод удаление задачи по идентификатору

    public void getSubTaskByEpicNumber(int taskIdentificator);   // метод получения подзадач определенного эпика

    public StatusTask checkEpicStatus(ArrayList<StatusTask> subTaskStatus);     // метод обновления статуса эпика

    public void getHistory(); // метод получения истории просмотров
}

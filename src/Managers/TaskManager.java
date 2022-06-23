package Managers;

import Tasks.Task;
import Tasks.EpicTask;
import Tasks.SubTask;

public interface TaskManager {

    public void getlistTask();  // метод вывода списка задач?

    public void getlistEpicTask();  // метод вывода списка эпиков

    public void getlistSubTask();  // метод вывода списка подзадач

    public void clearTaskList();   // метод удаления всего списка задач

    public void clearEpicTaskList();   // метод удаления всего списка эпиков

    void clearSubTaskList();   // метод удаления всего списка подзадач

    void getTaskByNumber(int taskIdentificator);  // метод получения данных о задаче по идентификатору

    void createTask(Task task);       // метод создания задачи

    void createTask(EpicTask epicTask);   // метод создания эпика

    void createTask(SubTask subTask);        // метод создания подзадачи

    void refreshTask(Task task);                     // метод обновление задачи

    void refreshTask(EpicTask epicTask);                 // метод обновление эпика

    void refreshTask(SubTask subTask);        // метод обновление подзадачи

    void clearTaskByNumber(int taskIdentificator);   // метод удаление задачи по идентификатору

    void getSubTaskByEpicNumber(int taskIdentificator);   // метод получения подзадач определенного эпика

    void getHistory(); // метод получения истории просмотров
}

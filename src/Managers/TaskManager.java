package Managers;

import Tasks.StatusTask;
import Tasks.Task;
import Tasks.EpicTask;
import Tasks.SubTask;

import java.util.ArrayList;
import java.util.Map;

public interface TaskManager {

    Map<Integer, Task> getlistTask();  // метод вывода списка задач?

    Map<Integer, EpicTask> getlistEpicTask();  // метод вывода списка эпиков

    Map<Integer, SubTask> getlistSubTask();  // метод вывода списка подзадач

    void clearTaskList();   // метод удаления всего списка задач

    void clearEpicTaskList();   // метод удаления всего списка эпиков

    void clearSubTaskList();   // метод удаления всего списка подзадач

    Task getTaskByNumber(int taskIdentificator);  // метод получения данных о задаче по идентификатору

    void createTask(Task task);       // метод создания задачи

    void createTask(EpicTask epicTask);   // метод создания эпика

    void createTask(SubTask subTask);        // метод создания подзадачи

    void refreshTask(Task task);                     // метод обновление задачи

    void refreshTask(EpicTask epicTask);                 // метод обновление эпика

    void refreshTask(SubTask subTask);        // метод обновление подзадачи

    void clearTaskByNumber(int taskIdentificator);   // метод удаление задачи по идентификатору

    ArrayList<Integer> getSubTaskByEpicNumber(int taskIdentificator);   // метод получения подзадач определенного эпика

    void getHistory(); // метод получения истории просмотров
}

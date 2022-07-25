package Managers;

import Tasks.Task;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.StatusTask;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private Map<Integer, Task> listTask = new HashMap<>();
    private Map<Integer, EpicTask> listEpicTask = new HashMap<>();
    private Map<Integer, SubTask> listSubTask = new HashMap<>();
    private InMemoryHistoryManager historyManager = Managers.getDefaultHistory();
    private static Integer identificator = 0;
    Path BackUpFile = Paths.get("backup.csv");

    public FileBackedTasksManager(Path backUpFile) {
        BackUpFile = backUpFile;
    }

    @Override
    public void getlistTask() { // метод вывода списка задач
        super.getlistTask();
    }

    @Override
    public void getlistEpicTask() { // метод вывода списка эпиков
        super.getlistEpicTask();
    }

    @Override
    public void getlistSubTask() { // метод вывода списка подзадач
        super.getlistSubTask();
    }

    @Override
    public void clearTaskList() {  // метод удаления всего списка задач
        super.clearTaskList();
        save();
    }

    @Override
    public void clearEpicTaskList() {  // метод удаления всего списка эпиков
        super.clearEpicTaskList();
        save();
    }

    @Override
    public void clearSubTaskList() {  // метод удаления всего списка подзадач
        super.clearSubTaskList();
        save();
    }

    @Override
    public void getTaskByNumber(int taskIdentificator) { // метод получения данных о задаче по идентификатору
        super.getTaskByNumber(taskIdentificator);
        save();
    }

    @Override
    public void createTask(Task task) {      // метод создания задачи
        super.createTask(task);
        save();
    }

    @Override
    public void createTask(EpicTask epicTask) {  // метод создания эпика
        super.createTask(epicTask);
    }

    @Override
    public void createTask(SubTask subTask) {        // метод создания подзадачи
        super.createTask(subTask);
    }

    @Override
    public void refreshTask(Task task) {                    // метод обновление задачи
        super.refreshTask(task);
        save();
    }

    @Override
    public void refreshTask(EpicTask epicTask) {                // метод обновление эпика
        super.refreshTask(epicTask);
        save();
    }

    @Override
    public void refreshTask(SubTask subTask) {       // метод обновление подзадачи
        super.refreshTask(subTask);
        save();
    }

    @Override
    public void clearTaskByNumber(int taskIdentificator) {  // метод удаление задачи по идентификатору
        super.clearTaskByNumber(taskIdentificator);
        save();
    }
    @Override
    public void getSubTaskByEpicNumber(int taskIdentificator) {  // метод получения подзадач определенного эпика
        super.getSubTaskByEpicNumber(taskIdentificator);
    }

    protected StatusTask checkEpicStatus(ArrayList<StatusTask> subTaskStatus) {    // метод обновления статуса эпика
        return super.checkEpicStatus(subTaskStatus);
    }
@Override
    public void getHistory() { // метод получения истории просмотров
    super.getHistory();
    save();
}
        private void save() {

    }
}

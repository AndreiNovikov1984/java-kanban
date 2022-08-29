package Managers;

import Tasks.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpTaskManager extends FileBackedTasksManager {
    private final String host;
    private final String userName;

    private final KVTaskClient clientKVServer;

    public HttpTaskManager(String host, String userName) {
        this.clientKVServer = new KVTaskClient(host);
        this.host = host;
        this.userName = userName;
    }

    @Override
    public Map<Integer, Task> getlistTask() { // метод вывода списка задач
        return super.getlistTask();
    }

    @Override
    public Map<Integer, EpicTask> getlistEpicTask() { // метод вывода списка эпиков
        return super.getlistEpicTask();
    }

    @Override
    public Map<Integer, SubTask> getlistSubTask() { // метод вывода списка подзадач
        return super.getlistSubTask();
    }

    @Override
    public void clearTaskList() {  // метод удаления всего списка задач
        super.clearTaskList();
    }

    @Override
    public void clearEpicTaskList() {  // метод удаления всего списка эпиков
        super.clearEpicTaskList();
    }

    @Override
    public void clearSubTaskList() {  // метод удаления всего списка подзадач
        super.clearSubTaskList();
    }

    @Override
    public Task getTaskByNumber(int taskIdentificator) { // метод получения данных о задаче по идентификатору
        return super.getTaskByNumber(taskIdentificator);
    }

    @Override
    public void createTask(Task task) {      // метод создания задачи
        super.createTask(task);
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
    }

    @Override
    public void refreshTask(EpicTask epicTask) {                // метод обновление эпика
        super.refreshTask(epicTask);
    }

    @Override
    public void refreshTask(SubTask subTask) {       // метод обновление подзадачи
        super.refreshTask(subTask);
    }

    @Override
    public void clearTaskByNumber(int taskIdentificator) {  // метод удаление задачи по идентификатору
        super.clearTaskByNumber(taskIdentificator);
    }

    @Override
    public ArrayList<Integer> getSubTaskByEpicNumber(int taskIdentificator) {  // метод получения подзадач определенного эпика
        return super.getSubTaskByEpicNumber(taskIdentificator);
    }

    protected StatusTask checkEpicStatus(ArrayList<StatusTask> subTaskStatus) {    // метод обновления статуса эпика
        return super.checkEpicStatus(subTaskStatus);
    }

    @Override           // метод получения истории просмотров
    public List<Task> getHistory() {           // метод получения истории просмотров
        return super.getHistory();
    }

    @Override
    public String save() {    // метод сохранения данных на сервере
        String backUpOnline = super.save();
        clientKVServer.saveOnServer(userName, backUpOnline);
        return backUpOnline;
    }

    @Override
    public Set<Task> getPrioritizedTasks() {            // метод получения сортированного списка
        return super.getPrioritizedTasks();
    }

    @Override
    public List<Integer> validateTaskTime(Task task) {              // метод проверки пересечения задач
        return super.validateTaskTime(task);
    }


    public static HttpTaskManager loadOnline(String userName) {   // метод загрузки данных с сервера
        HttpTaskManager taskManager = new HttpTaskManager("http://localhost:", userName);
        String backUpOnline = taskManager.clientKVServer.loadFromServer(userName);
        String[] lines = backUpOnline.split("\n");
        int maxID = 0;
        for (int i = 1; i < lines.length; i++) {
            String[] line = lines[i].split(",");
            if (line[0].equals("")) {
                line = lines[i + 1].split(",");
                for (int j = 0; j < line.length; j++) {
                    Convert.fromString(Integer.parseInt(line[j].trim()), taskManager.getHistoryManager(), taskManager);
                }
                break;
            } else if (TypeTask.valueOf(line[1].trim()) == TypeTask.TASK) {
                Task task = Convert.fromStringTask(line);
                taskManager.refreshTask(task);
                taskManager.getlistTask().put(task.getTaskId(), task);
                if (maxID < task.getTaskId()) {
                    maxID = task.getTaskId();
                }
            } else if (TypeTask.valueOf(line[1].trim()) == TypeTask.EPICTASK) {
                EpicTask epic = Convert.fromStringEpic(line);
                taskManager.getlistEpicTask().put(epic.getTaskId(), epic);
                if (maxID < epic.getTaskId()) {
                    maxID = epic.getTaskId();
                }
            } else if (TypeTask.valueOf(line[1].trim()).equals(TypeTask.SUBTASK)) {
                SubTask subTask = Convert.fromStringSub(line);
                taskManager.refreshTask(subTask);
                taskManager.getlistSubTask().put(subTask.getTaskId(), subTask);
                if (maxID < subTask.getTaskId()) {
                    maxID = subTask.getTaskId();
                }
            }
        }
        taskManager.identificator = maxID;
        return taskManager;
    }
}

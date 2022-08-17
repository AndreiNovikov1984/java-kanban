package Managers;

import Tasks.Task;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.StatusTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private Map<Integer, Task> listTask = new HashMap<>();
    private Map<Integer, EpicTask> listEpicTask = new HashMap<>();
    private Map<Integer, SubTask> listSubTask = new HashMap<>();
    private InMemoryHistoryManager historyManager = Managers.getDefaultHistory();
    protected Integer identificator = 0;

    @Override
    public Map<Integer, Task> getlistTask() { // метод вывода списка задач
        return listTask;
    }

    @Override
    public Map<Integer, EpicTask> getlistEpicTask() { // метод вывода списка эпиков
        return listEpicTask;
    }

    @Override
    public Map<Integer, SubTask> getlistSubTask() { // метод вывода списка подзадач
        return listSubTask;
    }

    @Override
    public void clearTaskList() {  // метод удаления всего списка задач
        for (int taskNumber : listTask.keySet()) {
            historyManager.remove(taskNumber);
        }
        listTask.clear();
    }

    @Override
    public void clearEpicTaskList() {  // метод удаления всего списка эпиков
        for (int taskNumber : listEpicTask.keySet()) {
            historyManager.remove(taskNumber);
        }

        listEpicTask.clear();
        clearSubTaskList();
    }

    @Override
    public void clearSubTaskList() {  // метод удаления всего списка подзадач
        for (int taskNumber : listSubTask.keySet()) {
            historyManager.remove(taskNumber);
        }
        listSubTask.clear();
        for (int epicTaskNumber : listEpicTask.keySet()) {
            listEpicTask.get(epicTaskNumber).setSubTaskIdentificator(null);
            listEpicTask.get(epicTaskNumber).setTaskStatus(StatusTask.NEW);
        }
    }

    @Override
    public Task getTaskByNumber(int taskIdentificator) { // метод получения данных о задаче по идентификатору
        Task taskByNum = null;
        for (int taskNumber : listTask.keySet()) {
            if (taskNumber == taskIdentificator) {
                System.out.println(listTask.get(taskNumber));
                historyManager.add(listTask.get(taskNumber));
                taskByNum = listTask.get(taskNumber);
            }
        }
        for (int epicTaskNumber : listEpicTask.keySet()) {
            if (epicTaskNumber == taskIdentificator) {
                System.out.println(listEpicTask.get(epicTaskNumber));
                historyManager.add(listEpicTask.get(epicTaskNumber));
                taskByNum = listEpicTask.get(epicTaskNumber);
            }
        }
        for (int subTaskNumber : listSubTask.keySet()) {
            if (subTaskNumber == taskIdentificator) {
                System.out.println(listSubTask.get(subTaskNumber));
                historyManager.add(listSubTask.get(subTaskNumber));
                taskByNum = listSubTask.get(subTaskNumber);
            }
        }
        return (taskByNum == null ? null : taskByNum);
    }

    @Override
    public void createTask(Task task) {      // метод создания задачи
        task.setTaskId(++identificator);
        listTask.put(task.getTaskId(), task);
    }

    @Override
    public void createTask(EpicTask epicTask) {  // метод создания эпика
        epicTask.setTaskId(++identificator);
        refreshTask(epicTask);
        listEpicTask.put(epicTask.getTaskId(), epicTask);
    }

    @Override
    public void createTask(SubTask subTask) {        // метод создания подзадачи
        subTask.setTaskId(++identificator);
        listSubTask.put(subTask.getTaskId(), subTask);
        refreshTask(listEpicTask.get(subTask.getEpicTaskIdentificator()));
    }

    @Override
    public void refreshTask(Task task) {                    // метод обновление задачи
        listTask.put(task.getTaskId(), task);
    }

    @Override
    public void refreshTask(EpicTask epicTask) {                // метод обновление эпика
        ArrayList<Integer> subTaskNumberTemporary = new ArrayList<>();
        ArrayList<StatusTask> subTaskStatusTemporary = new ArrayList<>();
        for (int subTaskNumber : listSubTask.keySet()) {
            if (listSubTask.get(subTaskNumber).getEpicTaskIdentificator() == epicTask.getTaskId()) {
                subTaskNumberTemporary.add(subTaskNumber);
                subTaskStatusTemporary.add(listSubTask.get(subTaskNumber).getTaskStatus());
            }
        }
        if (subTaskStatusTemporary.size() == 0) {
            epicTask.setTaskStatus(StatusTask.NEW);
        } else {
            epicTask.setTaskStatus(checkEpicStatus(subTaskStatusTemporary));
        }
        epicTask.setSubTaskIdentificator(subTaskNumberTemporary);
        listEpicTask.put(epicTask.getTaskId(), checkEpicTime(epicTask));
    }

    @Override
    public void refreshTask(SubTask subTask) {       // метод обновление подзадачи
        listSubTask.put(subTask.getTaskId(), subTask);
        if (listEpicTask.containsKey(subTask.getEpicTaskIdentificator())) {
            refreshTask(listEpicTask.get(subTask.getEpicTaskIdentificator()));
        }
    }

    @Override
    public void clearTaskByNumber(int taskIdentificator) {  // метод удаление задачи по идентификатору
        for (int taskNumber : listTask.keySet()) {
            if (taskNumber == taskIdentificator) {
                listTask.remove(taskNumber);
                System.out.println("Задача ID - " + taskNumber + " удалена");
                historyManager.remove(taskNumber);
                return;
            }
        }
        for (int epicTaskNumber : listEpicTask.keySet()) {
            if (epicTaskNumber == taskIdentificator) {
                if (listEpicTask.get(epicTaskNumber).getSubTaskIdentificator() != null) {
                    ArrayList<Integer> subTaskNumberTemporary = listEpicTask.get(epicTaskNumber).getSubTaskIdentificator();
                    for (int Number : subTaskNumberTemporary) {
                        listSubTask.remove(Number);
                        historyManager.remove(Number);
                    }
                }
                listEpicTask.remove(epicTaskNumber);
                historyManager.remove(epicTaskNumber);
                System.out.println("Эпик ID - " + epicTaskNumber + " удален");
                return;
            }
        }
        for (int subTaskNumber : listSubTask.keySet()) {
            if (subTaskNumber == taskIdentificator) {
                int epikTaskIdentificatorTemporary = listSubTask.get(subTaskNumber).getEpicTaskIdentificator();
                listSubTask.remove(subTaskNumber);
                historyManager.remove(subTaskNumber);
                System.out.println("Подзадача ID - " + subTaskNumber + " удалена");
                refreshTask(listEpicTask.get(epikTaskIdentificatorTemporary));
                return;
            }
        }
    }

    @Override
    public ArrayList<Integer> getSubTaskByEpicNumber(int taskIdentificator) {  // метод получения подзадач определенного эпика
        if (listEpicTask.containsKey(taskIdentificator)) {
            return listEpicTask.get(taskIdentificator).getSubTaskIdentificator() == null ?
                    null :
                    listEpicTask.get(taskIdentificator).getSubTaskIdentificator();
        } else {
            return null;
        }
    }

    protected StatusTask checkEpicStatus(ArrayList<StatusTask> subTaskStatus) {    // метод обновления статуса эпика
        StatusTask EpicStatus;
        int countNew = 0;
        int countDone = 0;
        for (StatusTask status : subTaskStatus) {
            if (status == StatusTask.NEW) {
                countNew++;
            } else if (status == StatusTask.DONE) {
                countDone++;
            }
        }
        if (countNew == subTaskStatus.size()) {
            EpicStatus = StatusTask.NEW;
        } else if (countDone == subTaskStatus.size()) {
            EpicStatus = StatusTask.DONE;
        } else {
            EpicStatus = StatusTask.IN_PROGRESS;
        }
        return EpicStatus;
    }

    protected EpicTask checkEpicTime(EpicTask epicTask) {
        LocalDateTime epicStartTime = LocalDateTime.of(2500, 1, 1, 0, 0);
        LocalDateTime epicEndTime = LocalDateTime.of(1900, 1, 1, 0, 0);
        ;
        for (int subNum : epicTask.getSubTaskIdentificator()) {
            if (epicStartTime.isAfter(listSubTask.get(subNum).getTaskStartTime())) {
                epicStartTime = listSubTask.get(subNum).getTaskStartTime();
            }
            if (epicEndTime.isBefore(listSubTask.get(subNum).getTaskEndTime())) {
                epicEndTime = listSubTask.get(subNum).getTaskEndTime();
            }
        }
        if (!epicStartTime.isEqual(LocalDateTime.of(2500, 1, 1, 0, 0))) {
            epicTask.setEpicTaskStartTime(epicStartTime);
            epicTask.setEpicTaskDuration(Duration.between(epicStartTime, epicEndTime));
            epicTask.getTaskEndTime();
        }
        return epicTask;
    }

    @Override
    public List<Task> getHistory() { // метод получения истории просмотров
        if (historyManager.getHistory() == null) {
            System.out.println("Не было просмотрено ни одной задачи");
        } else {
            System.out.println("Были просмотрены следующие задачи:");
            for (Task taskHistory : historyManager.getHistory()) {
                System.out.println("ID задачи " + taskHistory.getTaskId() + ", название задачи "
                        + taskHistory.getTaskName());
            }
        }
        return historyManager.getHistory();
    }

    public InMemoryHistoryManager getHistoryManager() {
        return historyManager;
    }
}

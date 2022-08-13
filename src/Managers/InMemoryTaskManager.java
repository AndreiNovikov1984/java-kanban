package Managers;

import Tasks.Task;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.StatusTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private static Map<Integer, Task> listTask = new HashMap<>();
    private static Map<Integer, EpicTask> listEpicTask = new HashMap<>();
    private static Map<Integer, SubTask> listSubTask = new HashMap<>();
    private InMemoryHistoryManager historyManager = Managers.getDefaultHistory();
    protected static Integer identificator = 0;

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
    public void getTaskByNumber(int taskIdentificator) { // метод получения данных о задаче по идентификатору
        for (int taskNumber : listTask.keySet()) {
            if (taskNumber == taskIdentificator) {
                System.out.println(listTask.get(taskNumber));
                historyManager.add(listTask.get(taskNumber));
            }
        }
        for (int epicTaskNumber : listEpicTask.keySet()) {
            if (epicTaskNumber == taskIdentificator) {
                System.out.println(listEpicTask.get(epicTaskNumber));
                historyManager.add(listEpicTask.get(epicTaskNumber));
            }
        }
        for (int subTaskNumber : listSubTask.keySet()) {
            if (subTaskNumber == taskIdentificator) {
                System.out.println(listSubTask.get(subTaskNumber));
                historyManager.add(listSubTask.get(subTaskNumber));
            }
        }
    }

    @Override
    public void createTask(Task task) {      // метод создания задачи
        task.setTaskId(++identificator);
        listTask.put(task.getTaskId(), task);
    }

    @Override
    public void createTask(EpicTask epicTask) {  // метод создания эпика
        epicTask.setTaskId(++identificator);
        listEpicTask.put(epicTask.getTaskId(), epicTask);
    }

    @Override
    public void createTask(SubTask subTask) {        // метод создания подзадачи
        subTask.setTaskId(++identificator);
        listSubTask.put(subTask.getTaskId(), subTask);
        refreshTask(listEpicTask.get(subTask.getEpikTaskIdentificator()));
    }

    @Override
    public void refreshTask(Task task) {                    // метод обновление задачи
        listTask.put(task.getTaskId(), task);
        System.out.println("Задача обновлена");
    }

    @Override
    public void refreshTask(EpicTask epicTask) {                // метод обновление эпика
        ArrayList<Integer> subTaskNumberTemporary = new ArrayList<>();
        ArrayList<StatusTask> subTaskStatusTemporary = new ArrayList<>();
        for (int subTaskNumber : listSubTask.keySet()) {
            if (listSubTask.get(subTaskNumber).getEpikTaskIdentificator() == epicTask.getTaskId()) {
                subTaskNumberTemporary.add(subTaskNumber);
                subTaskStatusTemporary.add(listSubTask.get(subTaskNumber).getTaskStatus());
            }
        }
        if (subTaskStatusTemporary.size() == 0) {
            listEpicTask.get(epicTask.getTaskId()).setTaskStatus(StatusTask.NEW);
        } else {
            listEpicTask.get(epicTask.getTaskId()).setTaskStatus(checkEpicStatus(subTaskStatusTemporary));
        }
        listEpicTask.get(epicTask.getTaskId()).setSubTaskIdentificator(subTaskNumberTemporary);
        listEpicTask.put(epicTask.getTaskId(), checkEpicTime(epicTask));
        System.out.println("Эпик обновлен");
    }

    @Override
    public void refreshTask(SubTask subTask) {       // метод обновление подзадачи
        listSubTask.put(subTask.getTaskId(), subTask);
        System.out.println("Подзадача обновлена");
        refreshTask(listEpicTask.get(listSubTask.get(subTask.getTaskId()).getEpikTaskIdentificator()));
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
                int epikTaskIdentificatorTemporary = listSubTask.get(subTaskNumber).getEpikTaskIdentificator();
                listSubTask.remove(subTaskNumber);
                historyManager.remove(subTaskNumber);
                System.out.println("Подзадача ID - " + subTaskNumber + " удалена");
                refreshTask(listEpicTask.get(epikTaskIdentificatorTemporary));
                return;
            }
        }
    }

    @Override
    public void getSubTaskByEpicNumber(int taskIdentificator) {  // метод получения подзадач определенного эпика
        if (listEpicTask.get(taskIdentificator).getSubTaskIdentificator() != null) {
            ArrayList<Integer> subTaskNumberTemporary = listEpicTask.get(taskIdentificator).getSubTaskIdentificator();
            for (Integer number : subTaskNumberTemporary) {
                System.out.println(listSubTask.get(number));
            }
        } else {
            System.out.println("У данного эпика нет подзадач");
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
        LocalDateTime epicStartTime = LocalDateTime.of(2500,1,1,0,0);
        LocalDateTime epicEndTime = LocalDateTime.of(1900,1,1,0,0);;
        for (int subNum : epicTask.getSubTaskIdentificator()) {
            if (epicStartTime.isAfter(listSubTask.get(subNum).getTaskStartTime())) {
                epicStartTime = listSubTask.get(subNum).getTaskStartTime();
            }
            if (epicEndTime.isBefore(listSubTask.get(subNum).getTaskEndTime())) {
                epicEndTime = listSubTask.get(subNum).getTaskEndTime();
            }
        }
        epicTask.setEpicTaskStartTime(epicStartTime);
        epicTask.setEpicTaskDuration(Duration.between(epicStartTime, epicEndTime));
        epicTask.getTaskEndTime();
        return epicTask;
    }

    @Override
    public void getHistory() { // метод получения истории просмотров
        if (historyManager.getHistory() == null) {
            System.out.println("Не было просмотрено ни одной задачи");
        } else {
            System.out.println("Были просмотрены следующие задачи:");
            for (Task taskHistory : historyManager.getHistory()) {
                System.out.println("ID задачи " + taskHistory.getTaskId() + ", название задачи "
                        + taskHistory.getTaskName());
            }
        }
    }

    public InMemoryHistoryManager getHistoryManager() {
        return historyManager;
    }
}

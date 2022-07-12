package Managers;

import Tasks.Task;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.StatusTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private Map<Integer, Task> listTask = new HashMap<>();
    private Map<Integer, EpicTask> listEpicTask = new HashMap<>();
    private Map<Integer, SubTask> listSubTask = new HashMap<>();
    private InMemoryHistoryManager historyManager = Managers.getDefaultHistory();
    private static Integer identificator = 0;

    @Override
    public void getlistTask() { // метод вывода списка задач
        for (int taskNumber : listTask.keySet()) {
            System.out.println("ID " + taskNumber + ", задача - " + listTask.get(taskNumber).getTaskName());
        }
    }

    @Override
    public void getlistEpicTask() { // метод вывода списка эпиков
        for (int epicTaskNumber : listEpicTask.keySet()) {
            System.out.println("ID " + epicTaskNumber + ", эпик - " + listEpicTask.get(epicTaskNumber).getTaskName());
        }
    }

    @Override
    public void getlistSubTask() { // метод вывода списка подзадач
        for (int subTaskNumber : listSubTask.keySet()) {
            System.out.println("ID " + subTaskNumber + ", подзадача - " + listSubTask.get(subTaskNumber).getTaskName());
        }
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
        System.out.println(listTask.get(task.getTaskId()));
    }

    @Override
    public void createTask(EpicTask epicTask) {  // метод создания эпика
        epicTask.setTaskId(++identificator);
        listEpicTask.put(epicTask.getTaskId(), epicTask);
        System.out.println(listEpicTask.get(epicTask.getTaskId()));
    }

    @Override
    public void createTask(SubTask subTask) {        // метод создания подзадачи
        subTask.setTaskId(++identificator);
        listSubTask.put(subTask.getTaskId(), subTask);
        System.out.println(listSubTask.get(subTask.getTaskId()));
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
            ;
        }
        listEpicTask.get(epicTask.getTaskId()).setSubTaskIdentificator(subTaskNumberTemporary);
        listEpicTask.put(epicTask.getTaskId(), epicTask);
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
                ArrayList<Integer> subTaskNumberTemporary = new ArrayList<>();
                if (listEpicTask.get(epicTaskNumber).getSubTaskIdentificator() != null) {
                    subTaskNumberTemporary = listEpicTask.get(epicTaskNumber).getSubTaskIdentificator();
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
        ArrayList<Integer> subTaskNumberTemporary = new ArrayList<>();
        if (listEpicTask.get(taskIdentificator).getSubTaskIdentificator() != null) {
            subTaskNumberTemporary = listEpicTask.get(taskIdentificator).getSubTaskIdentificator();
            for (Integer number : subTaskNumberTemporary) {
                System.out.println(listSubTask.get(number));
            }
        } else {
            System.out.println("У данного эпика нет подзадач");
        }
    }

    private StatusTask checkEpicStatus(ArrayList<StatusTask> subTaskStatus) {    // метод обновления статуса эпика
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

    @Override
    public void getHistory() { // метод получения истории просмотров
        historyManager.getHistory();
    }
}

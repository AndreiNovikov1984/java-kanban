import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> listTask = new HashMap<>();
    private HashMap<Integer, EpicTask> listEpicTask = new HashMap<>();
    private HashMap<Integer, SubTask> listSubTask = new HashMap<>();
    static Integer identificator = 0;

    void getlistTask() { // метод вывода списка задач
        for (int taskNumber : listTask.keySet()) {
            System.out.println("ID " + taskNumber + ", задача - " + listTask.get(taskNumber).taskName);
        }
    }

    void getlistEpicTask() { // метод вывода списка эпиков
        for (int epicTaskNumber : listEpicTask.keySet()) {
            System.out.println("ID " + epicTaskNumber + ", эпик - " + listEpicTask.get(epicTaskNumber).taskName);
        }
    }

    void getlistSubTask() { // метод вывода списка подзадач
        for (int subTaskNumber : listSubTask.keySet()) {
            System.out.println("ID " + subTaskNumber + ", подзадача - " + listSubTask.get(subTaskNumber).taskName);
        }
    }

    public void clearTaskList() {  // метод удаления всего списка задач
        listTask.clear();
    }

    public void clearEpicTaskList() {  // метод удаления всего списка эпиков
        listEpicTask.clear();
        listSubTask.clear();
    }

    public void clearSubTaskList() {  // метод удаления всего списка подзадач
        listSubTask.clear();
        for (int epicTaskNumber : listEpicTask.keySet()) {
            listEpicTask.get(epicTaskNumber).subTaskIdentificator = null;
            listEpicTask.get(epicTaskNumber).taskStatus = "new";
        }
    }

    public void getTaskByNumber(int taskIdentificator) { // метод получения данных о задаче по идентификатору
        for (int taskNumber : listTask.keySet()) {
            if (taskNumber == taskIdentificator) {
                System.out.println(listTask.get(taskNumber));
            }
        }
        for (int epicTaskNumber : listEpicTask.keySet()) {
            if (epicTaskNumber == taskIdentificator) {
                System.out.println(listEpicTask.get(epicTaskNumber));
            }
        }
        for (int subTaskNumber : listSubTask.keySet()) {
            if (subTaskNumber == taskIdentificator) {
                System.out.println(listSubTask.get(subTaskNumber));
            }
        }
    }

    public void createTask(Task task) {      // метод создания задачи
        listTask.put(++identificator, task);
        System.out.println(listTask);
    }

    public void createEpicTask(EpicTask epicTask) {  // метод создания эпика
        listEpicTask.put(++identificator, epicTask);
        System.out.println(listEpicTask);
    }

    public void createSubTask(SubTask subTask) {        // метод создания подзадачи
        listSubTask.put(++identificator, subTask);
        System.out.println(listSubTask);
        refreshTask(listEpicTask.get(subTask.epikTaskIdentificator));
    }

    public void refreshTask(Task task) {                    // метод обновление задачи
        for (int taskNumber : listTask.keySet()) {
            if (listTask.get(taskNumber).equals(task)) {
                listTask.put(taskNumber, task);
                System.out.println("Задача обновлена");
            }
        }
    }

    public void refreshTask(EpicTask epicTask) {                // метод обновление эпика
        ArrayList<Integer> subTaskNumberTemporary = new ArrayList<>();
        ArrayList<String> subTaskStatusTemporary = new ArrayList<>();
        for (int taskNumber : listEpicTask.keySet()) {
            if (listEpicTask.get(taskNumber).equals(epicTask)) {
                for (int subTaskNumber : listSubTask.keySet()) {
                    if (listSubTask.get(subTaskNumber).epikTaskIdentificator == taskNumber) {
                        subTaskNumberTemporary.add(subTaskNumber);
                        subTaskStatusTemporary.add(listSubTask.get(subTaskNumber).taskStatus);
                    }
                }
                if (subTaskStatusTemporary.size() == 0) {
                    listEpicTask.get(taskNumber).taskStatus = "new";
                } else {
                    listEpicTask.get(taskNumber).taskStatus = checkEpicStatus(subTaskStatusTemporary);
                    ;
                }
                listEpicTask.get(taskNumber).subTaskIdentificator = subTaskNumberTemporary;
                listEpicTask.put(taskNumber, epicTask);
                System.out.println("Эпик обновлен");
            }
        }
    }

    public void refreshSubTask(SubTask subTask) {       // метод обновление подзадачи
        for (int taskNumber : listSubTask.keySet()) {
            if (listSubTask.get(taskNumber).equals(subTask)) {
                listSubTask.put(taskNumber, subTask);
                System.out.println("Подзадача обновлена");
                refreshTask(listEpicTask.get(listSubTask.get(taskNumber).epikTaskIdentificator));
            }
        }
    }

    void clearTaskByNumber(int taskIdentificator) {  // метод удаление задачи по идентификатору
        for (int taskNumber : listTask.keySet()) {
            if (taskNumber == taskIdentificator) {
                listTask.remove(taskNumber);
                System.out.println("Задача ID - " + taskNumber + " удалена");
                return;
            }
        }
        for (int epicTaskNumber : listEpicTask.keySet()) {
            if (epicTaskNumber == taskIdentificator) {
                ArrayList<Integer> subTaskNumberTemporary = new ArrayList<>();
                if (listEpicTask.get(epicTaskNumber).subTaskIdentificator != null) {
                    subTaskNumberTemporary = listEpicTask.get(epicTaskNumber).subTaskIdentificator;
                    for (int Number : subTaskNumberTemporary) {
                        listSubTask.remove(Number);
                    }
                }
                listEpicTask.remove(epicTaskNumber);
                System.out.println("Эпик ID - " + epicTaskNumber + " удален");
                return;
            }
        }
        for (int subTaskNumber : listSubTask.keySet()) {
            if (subTaskNumber == taskIdentificator) {
                int epikTaskIdentificatorTemporary = listSubTask.get(subTaskNumber).epikTaskIdentificator;
                listSubTask.remove(subTaskNumber);
                System.out.println("Подзадача ID - " + subTaskNumber + " удалена");
                refreshTask(listEpicTask.get(epikTaskIdentificatorTemporary));
                return;
            }
        }
    }

    public void getSubTaskByEpicNumber(int taskIdentificator) {  // метод удаление задачи по идентификатору
        ArrayList<Integer> subTaskNumberTemporary = new ArrayList<>();
        if (listEpicTask.get(taskIdentificator).subTaskIdentificator != null) {
            subTaskNumberTemporary = listEpicTask.get(taskIdentificator).subTaskIdentificator;
            for (Integer number : subTaskNumberTemporary) {
                System.out.println(listSubTask.get(number));
            }
        } else {
            System.out.println("У данного эпика нет подзадач");
        }
    }

    public String checkEpicStatus(ArrayList<String> subTaskStatus) {    // метод обновления статуса эпика
        String EpicStatus;
        int countNew = 0;
        int countDone = 0;
        for (String status : subTaskStatus) {
            if (status.equals("new")) {
                countNew++;
            } else if (status.equals("done")) {
                countDone++;
            }
        }
        if (countNew == subTaskStatus.size()) {
            EpicStatus = "new";
        } else if (countDone == subTaskStatus.size()) {
            EpicStatus = "done";
        } else {
            EpicStatus = "in progress";
        }
        return EpicStatus;
    }


}

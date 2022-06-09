import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> listTask = new HashMap<>();
    private HashMap<Integer, EpicTask> listEpicTask = new HashMap<>();
    private HashMap<Integer, SubTask> listSubTask = new HashMap<>();
    static Integer identificator = 0;

    public void getlistTask() { // метод вывода списка задач
        for (int taskNumber : listTask.keySet()) {
            System.out.println("ID " + taskNumber + ", задача - " + listTask.get(taskNumber).taskName);
        }
    }

    public void getlistEpicTask() { // метод вывода списка эпиков
        for (int epicTaskNumber : listEpicTask.keySet()) {
            System.out.println("ID " + epicTaskNumber + ", эпик - " + listEpicTask.get(epicTaskNumber).taskName);
        }
    }

    public void getlistSubTask() { // метод вывода списка подзадач
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
            listEpicTask.get(epicTaskNumber).setSubTaskIdentificator(null);
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
        task.taskId = ++identificator;
        listTask.put(task.taskId, task);
        System.out.println(listTask.get(task.taskId));
    }

    public void createEpicTask(EpicTask epicTask) {  // метод создания эпика
        epicTask.taskId = ++identificator;
        listEpicTask.put(epicTask.taskId, epicTask);
        System.out.println(listEpicTask.get(epicTask.taskId));
    }

    public void createSubTask(SubTask subTask) {        // метод создания подзадачи
        subTask.taskId = ++identificator;
        listSubTask.put(subTask.taskId, subTask);
        System.out.println(listSubTask.get(subTask.taskId));
        refreshTask(listEpicTask.get(subTask.getEpikTaskIdentificator()));
    }

    public void refreshTask(Task task) {                    // метод обновление задачи
        listTask.put(task.taskId, task);
        System.out.println("Задача обновлена");
    }

    public void refreshTask(EpicTask epicTask) {                // метод обновление эпика
        ArrayList<Integer> subTaskNumberTemporary = new ArrayList<>();
        ArrayList<String> subTaskStatusTemporary = new ArrayList<>();
        for (int subTaskNumber : listSubTask.keySet()) {
            if (listSubTask.get(subTaskNumber).getEpikTaskIdentificator() == epicTask.taskId) {
                subTaskNumberTemporary.add(subTaskNumber);
                subTaskStatusTemporary.add(listSubTask.get(subTaskNumber).taskStatus);
            }
        }
        if (subTaskStatusTemporary.size() == 0) {
            listEpicTask.get(epicTask.taskId).taskStatus = "new";
        } else {
            listEpicTask.get(epicTask.taskId).taskStatus = checkEpicStatus(subTaskStatusTemporary);
            ;
        }
        listEpicTask.get(epicTask.taskId).setSubTaskIdentificator(subTaskNumberTemporary);
        listEpicTask.put(epicTask.taskId, epicTask);
        System.out.println("Эпик обновлен");
    }

    public void refreshTask(SubTask subTask) {       // метод обновление подзадачи
        listSubTask.put(subTask.taskId, subTask);
        System.out.println("Подзадача обновлена");
        refreshTask(listEpicTask.get(listSubTask.get(subTask.taskId).getEpikTaskIdentificator()));
    }

    public void clearTaskByNumber(int taskIdentificator) {  // метод удаление задачи по идентификатору
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
                if (listEpicTask.get(epicTaskNumber).getSubTaskIdentificator() != null) {
                    subTaskNumberTemporary = listEpicTask.get(epicTaskNumber).getSubTaskIdentificator();
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
                int epikTaskIdentificatorTemporary = listSubTask.get(subTaskNumber).getEpikTaskIdentificator();
                listSubTask.remove(subTaskNumber);
                System.out.println("Подзадача ID - " + subTaskNumber + " удалена");
                refreshTask(listEpicTask.get(epikTaskIdentificatorTemporary));
                return;
            }
        }
    }

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

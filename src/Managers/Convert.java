package Managers;

import Tasks.EpicTask;
import Tasks.StatusTask;
import Tasks.SubTask;
import Tasks.Task;

import java.util.List;

public class Convert {

    protected String toString(Task task) {                         // метод перевода данных для файла TASK и EPICTASK
        return Integer.toString(task.getTaskId()) +
                "," + task.getTaskType() +
                "," + task.getTaskName() +
                "," + task.getTaskStatus() +
                "," + task.getTaskDescription() + "\n";
    }

    protected Task fromStringTask(String[] line) {                      // метод восстновления TASK из файла
        Task task = new Task(Integer.parseInt(line[0].trim()), line[2].trim(),
                StatusTask.valueOf(line[3].trim()), line[4].trim());
        return task;
    }

    protected EpicTask fromStringEpic(String[] line) {                      // метод восстновления EPICTASK из файла
        EpicTask epic = new EpicTask(Integer.parseInt(line[0].trim()), line[2].trim(),
                StatusTask.valueOf(line[3].trim()), line[4].trim());
        return epic;
    }

    protected String toString(SubTask subTask) {                    // метод перевода данных для файла SUBTASK
        return Integer.toString(subTask.getTaskId()) +
                "," + subTask.getTaskType() +
                "," + subTask.getTaskName() +
                "," + subTask.getTaskStatus() +
                "," + subTask.getTaskDescription() +
                "," + subTask.getEpikTaskIdentificator() + "\n";
    }

    protected SubTask fromStringSub(String[] line) {                      // метод восстновления SUBTASK из файла
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        SubTask subTask = new SubTask(Integer.parseInt(line[0].trim()), line[2].trim(),
                StatusTask.valueOf(line[3].trim()), line[4].trim(), Integer.parseInt(line[5].trim()));
        taskManager.refreshTask(subTask);
        return subTask;
    }

    protected static String toString(HistoryManager manager) {    // метод перевода данных истории просмотра для файла
        StringBuilder saveStr = new StringBuilder();
        try {
            for (Task taskHistory : manager.getHistory()) {
                saveStr.append(Integer.toString(taskHistory.getTaskId()) + ",");
            }
        } catch (NullPointerException exception) {
        }
        return saveStr.toString();
    }

    protected static void fromString(Integer number) {              // метод восстановления истории просмотров из файла
        InMemoryHistoryManager historyManager = Managers.getDefaultHistory();
        InMemoryTaskManager taskManager = Managers.getDefault();

        for (Task task : taskManager.getlistTask().values()) {
            if (task.getTaskId() == number) {
                historyManager.add(task);
            }
        }
        for (EpicTask epicTask : taskManager.getlistEpicTask().values()) {
            if (epicTask.getTaskId() == number) {
                historyManager.add(epicTask);
            }
        }
        for (SubTask subTask : taskManager.getlistSubTask().values()) {
            if (subTask.getTaskId() == number) {
                historyManager.add(subTask);
            }
        }
    }
}

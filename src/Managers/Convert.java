package Managers;

import Tasks.SubTask;
import Tasks.Task;

import java.util.Arrays;

public class Convert {
    protected String toString(Task task) {                            // метод перевода данных для файла TASK и EPICTASK
        return Integer.toString(task.getTaskId()) +
                "," + task.getTaskType() +
                "," + task.getTaskName() +
                "," + task.getTaskStatus() +
                "," + task.getTaskDescription() + "\n";
    }
    protected String toString(SubTask subTask) {                      // метод перевода данных для файла SUBTASK
        return Integer.toString(subTask.getTaskId()) +
                "," + subTask.getTaskType() +
                "," + subTask.getTaskName() +
                "," + subTask.getTaskStatus() +
                "," + subTask.getTaskDescription() +
                "," + subTask.getEpikTaskIdentificator() + "\n";
    }

    protected static String toString(HistoryManager manager) {      // метод перевода данных истории просмотра для файла
        StringBuilder saveStr = new StringBuilder();
        try {
            for (Task taskHistory : manager.getHistory()) {
                saveStr.append(Integer.toString(taskHistory.getTaskId()) + ",");
            }
        } catch (NullPointerException exception) {
        }
        return saveStr.toString();
    }
}

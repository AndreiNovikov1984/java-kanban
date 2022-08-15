package Managers;

import Tasks.EpicTask;
import Tasks.StatusTask;
import Tasks.SubTask;
import Tasks.Task;

public class Convert<T extends Task> {

    protected static String toString(Task task) {                // метод перевода данных для файла TASK и EPICTASK
        return Integer.toString(task.getTaskId()) +
                "," + task.getTaskType() +
                "," + task.getTaskName() +
                "," + task.getTaskStatus() +
                "," + task.getTaskDescription() +
                "," + task.getTaskStartTime() +
                "," + (task.getTaskDuration() == null ? task.getTaskDuration() :
                task.getTaskDuration().toMinutes()) + "\n";
    }

    protected static Task fromStringTask(String[] line) {                      // метод восстановления TASK из файла
        Task task = new Task(Integer.parseInt(line[0].trim()), line[2].trim(),
                StatusTask.valueOf(line[3].trim()), line[4].trim(), line[5].trim(), line[6].trim());
        return task;
    }

    protected static EpicTask fromStringEpic(String[] line) {            // метод восстановления EPICTASK из файла
        EpicTask epic = new EpicTask(Integer.parseInt(line[0].trim()), line[2].trim(),
                StatusTask.valueOf(line[3].trim()), line[4].trim(), line[5].trim(), line[6].trim());
        return epic;
    }

    protected static String toString(SubTask subTask) {                    // метод перевода данных для файла SUBTASK
        return Integer.toString(subTask.getTaskId()) +
                "," + subTask.getTaskType() +
                "," + subTask.getTaskName() +
                "," + subTask.getTaskStatus() +
                "," + subTask.getTaskDescription() +
                "," + subTask.getTaskStartTime() +
                "," + subTask.getTaskDuration().toMinutes() +
                "," + subTask.getEpicTaskIdentificator() + "\n";
    }

    protected static SubTask fromStringSub(String[] line) {               // метод восстановления SUBTASK из файла
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        SubTask subTask = new SubTask(Integer.parseInt(line[0].trim()), line[2].trim(),
                StatusTask.valueOf(line[3].trim()), line[4].trim(), line[5].trim(), line[6].trim(),
                Integer.parseInt(line[7].trim()));
        return subTask;
    }

    protected static String toString(HistoryManager manager) {   // метод перевода данных истории просмотра для файла
        StringBuilder saveStr = new StringBuilder();
        try {
            for (Task taskHistory : manager.getHistory()) {
                saveStr.append(Integer.toString(taskHistory.getTaskId()) + ",");
            }
        } catch (NullPointerException exception) {
        }
        return saveStr.toString();
    }

    protected static void fromString(Integer number,                // метод восстановления истории просмотров из файла
                                     InMemoryHistoryManager historyManager) {
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

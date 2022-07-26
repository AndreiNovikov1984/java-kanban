package Managers;

import Tasks.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    Convert convert = new Convert();
    Path backUpFilePath;

    public FileBackedTasksManager(Path backUpFilePath) {
        this.backUpFilePath = backUpFilePath;
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
        save();
    }

    @Override
    public void createTask(SubTask subTask) {        // метод создания подзадачи
        super.createTask(subTask);
        save();

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

    @Override
    public void save() {
        try {
            Path backUpFile = Files.createFile(backUpFilePath);
        } catch (IOException exception) {
        }
        StringBuilder saveString = new StringBuilder("id,type,name,status,description,epic\n");

        for (Task task : getlistTask().values()) {
            saveString.append(convert.toString(task));
        }
        for (EpicTask epicTask : getlistEpicTask().values()) {
            saveString.append(convert.toString(epicTask));
        }
        for (SubTask subTask : getlistSubTask().values()) {
            saveString.append(convert.toString(subTask));
        }
        saveString.append("History," + Convert.toString(super.getHistoryManager()));
        try (Writer fileWriter = new FileWriter(backUpFilePath.toString(), false)) {
            fileWriter.write(saveString.toString());
        } catch (IOException exception) {
            System.out.println("Данные не сохранены");
        }
    }

    public static void loadFromFile(Path backUpFilePath) {
        FileBackedTasksManager taskManager = new FileBackedTasksManager(backUpFilePath);
        try {
            String value = Files.readString(backUpFilePath);
            taskManager.recovery(value);
        } catch (IOException exception) {
            System.out.println("Файл не читабелен, невозможно восстановить данные");
        }
    }

     public void recovery(String value) {  // метод восстановления информации из файла
        String[] lines = value.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String[] line = lines[i].split(",");
            if (line[0].equals("History")) {
                for (int j = 1; j < line.length; j++) {
                    getTask(Integer.parseInt(line[j].trim()));
                }
            } else if (TypeTask.valueOf(line[1].trim()) == TypeTask.TASK) {
                super.createTask(new Task(Integer.parseInt(line[0].trim()), line[2].trim(),
                        StatusTask.valueOf(line[3].trim()), line[4].trim()));
            } else if (TypeTask.valueOf(line[1].trim()) == TypeTask.EPICTASK) {
                super.createTask(new EpicTask(Integer.parseInt(line[0].trim()), line[2].trim(),
                        StatusTask.valueOf(line[3].trim()), line[4].trim()));
            } else if (TypeTask.valueOf(line[1].trim()).equals(TypeTask.SUBTASK)) {
                super.createTask(new SubTask(Integer.parseInt(line[0].trim()), line[2].trim(),
                        StatusTask.valueOf(line[3].trim()), line[4].trim(), Integer.parseInt(line[5].trim())));
            }
        }
    }

    public void getTask(int number) {            // метод восстановления задач в истории
        for (Task task : getlistTask().values()) {
            if (task.getTaskId() == number) {
                super.getHistoryManager().add(task);
            }
        }
        for (EpicTask epicTask : getlistEpicTask().values()) {
            if (epicTask.getTaskId() == number) {
                super.getHistoryManager().add(epicTask);
            }
        }
        for (SubTask subTask : getlistSubTask().values()) {
            if (subTask.getTaskId() == number) {
                super.getHistoryManager().add(subTask);
            }
        }
    }
}
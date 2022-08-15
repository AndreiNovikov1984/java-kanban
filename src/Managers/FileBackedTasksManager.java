package Managers;

import Tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final File backUpFile;

    public FileBackedTasksManager(File backUpFile) {
        this.backUpFile = backUpFile;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

    public static void main(String[] args) {
        File backUpFile = new File("backup.csv");
        FileBackedTasksManager taskManager = new FileBackedTasksManager(backUpFile);
        taskManager.createTask(new Task(0, "Переезд", "Собрать вещи перевезти разобрать",
                "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "Обучение Java",
                "выбрать курс пройти курс усвоить весь материал"));
        taskManager.createTask(new EpicTask(0, "Выучить уроки", "усвоить весь материал"));
        taskManager.createTask(new SubTask(0, "Выбрать курс",
                "изучить всех поставщиков курсов", "13.08.2022, 14:00", 120, 3));
        taskManager.createTask(new SubTask(0, "Пройти курс", "выполнить все задания",
                "13.08.2022, 15:00", 60, 3));
        taskManager.createTask(new SubTask(0, "Найти учебник", "открыть учебник/закрыть учебник",
                "13.08.2022, 15:50", 15, 3));
        taskManager.createTask(new Task(0, "Покупка стола", "Выбрать стол купить привезти",
                "13.08.2022, 10:10", 120));
        taskManager.getTaskByNumber(1);
        taskManager.getTaskByNumber(2);
        taskManager.getTaskByNumber(3);
        taskManager.getTaskByNumber(4);
        taskManager.getTaskByNumber(5);
        taskManager.getTaskByNumber(6);
        taskManager.getPrioritizedTasks();

        FileBackedTasksManager taskManager1 = loadFromFile(backUpFile);
        System.out.println(taskManager1.getlistTask());
        System.out.println(taskManager1.getlistEpicTask());
        System.out.println(taskManager1.getlistSubTask());
        taskManager1.getHistory();
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

 /*   @Override
    public Task getTaskByNumber(int taskIdentificator) { // метод получения данных о задаче по идентификатору
        Task taskByNum = super.getTaskByNumber(taskIdentificator);
    }*/

    @Override
    public void createTask(Task task) {      // метод создания задачи
        super.createTask(task);
        save();
        validateTaskTime(task);
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
        validateTaskTime(subTask);
    }

    @Override
    public void refreshTask(Task task) {                    // метод обновление задачи
        super.refreshTask(task);
        save();
        validateTaskTime(task);
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
        validateTaskTime(subTask);
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
    public void getHistory() {           // метод получения истории просмотров
        super.getHistory();
        save();
    }

    public void save() {
        StringBuilder saveString = new StringBuilder("id,type,name,status,description,taskStartTime, taskDuration, epic\n");
        Sort.sort(getlistTask(), getlistSubTask());
        for (Task task : getlistTask().values()) {
            saveString.append(Convert.toString(task));
        }
        for (EpicTask epicTask : getlistEpicTask().values()) {
            saveString.append(Convert.toString(epicTask));
        }
        for (SubTask subTask : getlistSubTask().values()) {
            saveString.append(Convert.toString(subTask));
        }
        saveString.append("\n" + Convert.toString(super.getHistoryManager()));
        try (Writer fileWriter = new FileWriter(backUpFile.toString(), false)) {
            fileWriter.write(saveString.toString());
        } catch (IOException exception) {
            System.out.println("Данные не сохранены");
        }
    }

    public void getPrioritizedTasks() {
        Sort.getSortedTaskTree();
        System.out.println("Cписок в сорт виде: " + Sort.getSortedTaskTree());
    }

    protected void validateTaskTime(Task task) {
        List<Integer> collected = Sort.getSortedTaskTree().stream()
                .filter(t -> t.getTaskId() != task.getTaskId())
                .filter(t -> ((t.getTaskStartTime().isBefore(task.getTaskStartTime()) && (t.getTaskEndTime().isAfter(task.getTaskStartTime())))) ||
                        (t.getTaskStartTime().isBefore(task.getTaskEndTime()) && (t.getTaskEndTime().isAfter(task.getTaskEndTime()))) ||
                        (t.getTaskStartTime().isBefore(task.getTaskStartTime()) && (t.getTaskEndTime().isAfter(task.getTaskEndTime()))) ||
                        (t.getTaskStartTime().isAfter(task.getTaskStartTime()) && (t.getTaskEndTime().isBefore(task.getTaskEndTime()))))
                .map(t -> t.getTaskId())
                .collect(Collectors.toList());
        if (collected.size() != 0) {
            System.out.println("У задачи ID " + task.getTaskId() + " выявлено пересечение со следующими задачами - ID: "
                    + collected + ". Пожалуйста, внесите изменения.");
        }
    }

    public static FileBackedTasksManager loadFromFile(File backUpFile) {      // метод загрузки данных из файла
        FileBackedTasksManager taskManager = new FileBackedTasksManager(backUpFile);
        try {
            String value = Files.readString(backUpFile.toPath());
            String[] lines = value.split("\n");
            Integer maxID = 0;
            for (int i = 1; i < lines.length; i++) {
                String[] line = lines[i].split(",");
                if (line[0].equals("")) {
                    line = lines[i + 1].split(",");
                    for (int j = 0; j < line.length; j++) {
                        Convert.fromString(Integer.parseInt(line[j].trim()), taskManager.getHistoryManager());
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
        } catch (IOException exception) {
            System.out.println("Файл не читабелен, невозможно восстановить данные");
        }
        return taskManager;
    }
}
import Managers.Managers;
import Managers.InMemoryTaskManager;
import Managers.FileBackedTasksManager;
import Tasks.Task;
import Tasks.EpicTask;
import Tasks.SubTask;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = Managers.getDefault();
        FileBackedTasksManager.loadFromFile(Paths.get("backup.csv"));
      //  taskManager.save();

      /*  taskManager.createTask(new Task(0, "Переезд", "Собрать вещи перевезти разобрать"));
        taskManager.createTask(new EpicTask(0, "Обучение Java",
                "выбрать курс пройти курс усвоить весь материал"));
        taskManager.createTask(new EpicTask(0, "Выучить уроки", "усвоить весь материал"));
        taskManager.createTask(new SubTask(0, "Выбрать курс",
                "изучить всех поставщиков курсов", 3));
        taskManager.createTask(new SubTask(0, "Пройти курс", "выполнить все задания",
                3));
        taskManager.createTask(new SubTask(0, "Найти учебник",
                "открыть учебник/закрыть учебник", 3));
        taskManager.createTask(new Task(0, "Покупка стола", "Выбрать стол купить привезти"));
        taskManager.getlistTask();
        taskManager.save();

        taskManager.getTaskByNumber(1);
        taskManager.getTaskByNumber(2);
        taskManager.getTaskByNumber(3);
        taskManager.getTaskByNumber(7);
        taskManager.getTaskByNumber(5);
        taskManager.getTaskByNumber(6);
        taskManager.getTaskByNumber(2);
        taskManager.getHistory();
     /*   taskManager.getTaskByNumber(5);
        taskManager.getHistory();
        taskManager.getTaskByNumber(4);
        taskManager.getHistory();
        taskManager.clearTaskByNumber(1);
        taskManager.getHistory();
        taskManager.clearTaskByNumber(3);
        taskManager.getHistory(); */
    }
}

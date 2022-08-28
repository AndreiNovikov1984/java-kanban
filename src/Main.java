import Managers.Managers;
import Managers.HttpTaskServer;
import Managers.HttpTaskManager;

import Server.KVServer;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        KVServer serverKV = new KVServer();
        serverKV.start();
        HttpTaskManager taskManager = Managers.getDefault("http://localhost:", "Andrei");

        taskManager.createTask(new Task(0, "Переезд", "Собрать вещи перевезти разобрать",
                "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "Обучение Java",
                "выбрать курс пройти курс усвоить весь материал"));
        EpicTask epic = new EpicTask(7, "Обучение Java",
                "выбрать курс пройти курс усвоить весь материал");
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
        taskManager.save();
        HttpTaskManager taskManager1 = HttpTaskManager.loadOnline("Andrei");
        taskManager1.getHistory();
        serverKV.stop();
        httpTaskServer.stop();
    }
}

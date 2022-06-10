import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        Task task = new Task(0, "Переезд", "Собрать вещи, перевезти, разобрать");
        taskManager.createTask(task);
        EpicTask epicTask = new EpicTask(0, "Обучение Java",
                "выбрать курс, пройти курс, усвоить весь материал");
        taskManager.createEpicTask(epicTask);
        epicTask = new EpicTask(0, "Выучить уроки", "усвоить весь материал");
        taskManager.createEpicTask(epicTask);
        SubTask subTask = new SubTask(0, "Выбрать курс", "изучить всех поставщиков курсов",
                3);
        taskManager.createSubTask(subTask);
        subTask = new SubTask(0, "Пройти курс", "выполнить все задания", 3);
        taskManager.createSubTask(subTask);
        subTask = new SubTask(0, "Найти учебник", "открыть учебник/закрыть учебник",
                2);
        taskManager.createSubTask(subTask);

        while (true) {
            printMenu();
            String command = scanner.next();
            if (Integer.parseInt(command) == 1) {
                printSubMenu();
                command = scanner.next();
                switch (Integer.parseInt(command)) {
                    case 1:
                        taskManager.getlistTask();
                        break;
                    case 2:
                        taskManager.getlistEpicTask();
                        break;
                    case 3:
                        taskManager.getlistSubTask();
                        break;
                    default:
                        break;
                }
            } else if (Integer.parseInt(command) == 2) {
                printSubMenu();
                command = scanner.next();
                switch (Integer.parseInt(command)) {
                    case 1:
                        taskManager.clearTaskList();
                        break;
                    case 2:
                        taskManager.clearEpicTaskList();
                        break;
                    case 3:
                        taskManager.clearSubTaskList();
                        break;
                    default:
                        break;
                }
            } else if (Integer.parseInt(command) == 3) {
                System.out.println("Введите идентификатор задачи");
                command = scanner.next();
                int taskIdentificator = Integer.parseInt(command);
                taskManager.getTaskByNumber(taskIdentificator);
            } else if (Integer.parseInt(command) == 4) {
            } else if (Integer.parseInt(command) == 5) {
                System.out.println("Выберите задачу, которую необходимо обновить");
                taskManager.refreshTask(epicTask);
            } else if (Integer.parseInt(command) == 6) {
                System.out.println("Введите идентификатор задачи");
                command = scanner.next();
                int taskIdentificator = Integer.parseInt(command);
                taskManager.clearTaskByNumber(taskIdentificator);
            } else if (Integer.parseInt(command) == 7) {
                System.out.println("Введите идентификатор эпика");
                command = scanner.next();
                int taskIdentificator = Integer.parseInt(command);
                taskManager.getSubTaskByEpicNumber(taskIdentificator);
            } else if (Integer.parseInt(command) == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() {   // метод печати меню
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Получение списка всех задач");
        System.out.println("2 - Удаление всех задач");
        System.out.println("3 - Получение данных о задаче по идентификатору");
        System.out.println("4 - Создание задачи");
        System.out.println("5 - Обновление задачи");
        System.out.println("6 - Удаление задачи по идентификатору");
        System.out.println("7 - Вывод списка подзадач");
        System.out.println("0 - Выход");
    }

    public static void printSubMenu() {   // метод печати подменю
        System.out.println("Выберите тип нужной задачи");
        System.out.println("1 - Задача");
        System.out.println("2 - Эпик");
        System.out.println("3 - Подзадача");
        System.out.println("Чтобы вернуться назад, нажмите любую другую цифру");
    }
}

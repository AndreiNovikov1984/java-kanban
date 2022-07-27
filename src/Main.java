import Managers.Managers;
import Managers.InMemoryTaskManager;
import Managers.FileBackedTasksManager;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = Managers.getDefault();
        FileBackedTasksManager.loadFromFile(Paths.get("backup.csv"));
    }
}

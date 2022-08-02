import Managers.Managers;
import Managers.InMemoryTaskManager;
import Managers.FileBackedTasksManager;

import java.io.File;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = FileBackedTasksManager.loadFromFile(new File("backup.csv"));
    }
}

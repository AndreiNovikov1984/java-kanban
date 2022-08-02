package Managers;

import java.io.File;
import java.nio.file.Paths;

public class Managers {

    public static InMemoryTaskManager getDefault() {
        return new FileBackedTasksManager(new File("backup.csv"));
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

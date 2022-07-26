package Managers;

import java.nio.file.Paths;

public class Managers {

    public static InMemoryTaskManager getDefault() {
        return new FileBackedTasksManager(Paths.get("backup.csv"));
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

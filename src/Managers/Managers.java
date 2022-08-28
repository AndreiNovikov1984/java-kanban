package Managers;

public class Managers {

    public static HttpTaskManager getDefault(String host, String userName) {
        return new HttpTaskManager(host, userName);
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}

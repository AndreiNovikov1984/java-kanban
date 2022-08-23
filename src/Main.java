import Managers.Managers;
import Managers.InMemoryTaskManager;
import Managers.FileBackedTasksManager;
import Managers.HttpTaskServer;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpTaskServer httpServer = new HttpTaskServer();
        httpServer.httpTaskServer();
      //  new KVServer().start();
    }
}

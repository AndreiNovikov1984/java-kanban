package Tests;

import Managers.FileBackedTasksManager;
import Managers.HttpTaskServer;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    private static Gson gson = new Gson();
    private static HttpTaskServer httpTaskServer;
    private FileBackedTasksManager taskManager;
    private HttpClient client;
    private String uri = "http://localhost:8080/tasks/";
    private HttpResponse.BodyHandler<String> handler;

    @BeforeEach
    public void beforeEach() throws IOException {
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        taskManager = new FileBackedTasksManager();
        taskManager.createTask(new Task(0, "Переезд", "Собрать вещи перевезти разобрать",
                "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "Обучение Java",
                "выбрать курс пройти курс усвоить весь материал"));
        taskManager.createTask(new EpicTask(0, "Выучить уроки", "усвоить весь материал"));
        taskManager.createTask(new SubTask(0, "Выбрать курс",
                "изучить всех поставщиков курсов", "13.08.2022, 14:00", 120, 2));
        client = HttpClient.newHttpClient();
        handler = HttpResponse.BodyHandlers.ofString();
    }

    @AfterEach
    public void afterEach() {
        httpTaskServer.stop();
    }

    @Test
    public void getAllTask() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertNotNull(response, "Данные не получены");
    }

    @Test
    public void getOnlyTask() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "task/"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertNotNull(response, "Данные не получены");
    }

    @Test
    public void getOnlyEpic() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "epic/"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertNotNull(response, "Данные не получены");
    }

    @Test
    public void getOnlySub() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "subtask/"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertNotNull(response, "Данные не получены");
    }

    @Test
    public void getTaskByNumber() throws IOException, InterruptedException {
        String test = gson.toJson(taskManager.getTaskByNumber(1));
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "task/?id=1"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals(test, response.body());
        assertNotNull(response, "Данные не получены");
    }

    @Test
    public void getSubByEpicNumber() throws IOException, InterruptedException {
        String test = gson.toJson(taskManager.getTaskByNumber(4));
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "subtask/epic?id=2"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = null;
        response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("[" + test + "]", response.body());
        assertNotNull(response, "Данные не получены");
    }

    @Test
    public void getErrorNumber() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "task/?id=15"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(404, response.statusCode());
        assertEquals("Данная задача отсутствует или ранее удалена.", response.body(), "Данные не получены");
    }

    @Test
    public void getErrorPath() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasksk/"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(404, response.statusCode());
        assertEquals("Error! Ошибка в URI! Введите корректный адрес", response.body(), "Данные не получены");
    }

    @Test
    public void getHistory() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "history"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertNotNull(response, "Данные не получены");
    }

    @Test
    public void postTask() throws IOException, InterruptedException {
        String test = gson.toJson(taskManager.getTaskByNumber(1));
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(test))
                .uri(URI.create(uri + "task"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals("Загрузка успешно завершена", response.body(), "Данные не получены");
    }

    @Test
    public void postEpic() throws IOException, InterruptedException {
        String test = gson.toJson(taskManager.getTaskByNumber(2));
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(test))
                .uri(URI.create(uri + "epic"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals("Загрузка успешно завершена", response.body(), "Данные не получены");
    }

    @Test
    public void postSub() throws IOException, InterruptedException {
        String test = gson.toJson(taskManager.getTaskByNumber(4));
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(test))
                .uri(URI.create(uri + "subtask"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals("Загрузка успешно завершена", response.body(), "Данные не получены");
    }

    @Test
    public void postError() throws IOException, InterruptedException {
        String test = "Ошибка";
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(test))
                .uri(URI.create(uri + "task"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(400, response.statusCode());
        assertEquals("Непредвиденная ошибка! Проверьте корректность запроса", response.body(), "Данные не получены");
    }

    @Test
    public void deleteTaskByNum() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri + "task?id=1"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("Delete! Удалена задача под номером - 1", response.body(), "Данные не получены");
    }

    @Test
    public void deleteAllTask() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri + "task"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("Delete! Удалены все задачи", response.body(), "Данные не получены");
    }

    @Test
    public void deleteAllEpic() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri + "epic"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("Delete! Удалены все эпики и подзадачи", response.body(), "Данные не получены");
    }

    @Test
    public void deleteAllSubtask() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri + "subtask"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals("Delete! Удалены все подзадачи", response.body(), "Данные не получены");
    }

    @Test
    public void deleteError() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri + "taksa"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, handler);
        assertEquals(404, response.statusCode());
        assertEquals("Error! Ошибка в URI! Введите корректный адрес", response.body(), "Данные не получены");
    }
}
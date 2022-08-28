package Managers;

import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final HttpServer httpTaskServer;
    private static Gson gson = new Gson();

    public HttpTaskServer() throws IOException {
        FileBackedTasksManager taskManager = FileBackedTasksManager.loadFromFile(Paths.get("backupOnline.csv"));
        httpTaskServer = HttpServer.create();
        httpTaskServer.bind(new InetSocketAddress(PORT), 0);
        httpTaskServer.createContext("/tasks", new TaskHandler(taskManager));
    }

    static class TaskHandler implements HttpHandler {
        InMemoryTaskManager taskManager;

        TaskHandler(InMemoryTaskManager taskManager) {
            this.taskManager = taskManager;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {    // метод обработки запросов httpTaskServer
            String response = null;
            String method = httpExchange.getRequestMethod();
            URI requestURI = httpExchange.getRequestURI();
            int taskNum = getTaskNumber(requestURI);
            String path = requestURI.getPath();
            String[] splitPath = path.split("/");

            switch (method) {
                case "GET":
                    if ((splitPath.length == 2) && (splitPath[1].equals("tasks"))) {
                        ArrayList<Task> listTask = new ArrayList<>();
                        for (Task task : taskManager.getlistTask().values()) {
                            listTask.add(task);
                        }
                        for (Task task : taskManager.getlistEpicTask().values()) {
                            listTask.add(task);
                        }
                        for (Task task : taskManager.getlistSubTask().values()) {
                            listTask.add(task);
                        }
                        response = gson.toJson(listTask);
                        break;
                    }
                    if (splitPath[2].equals("task")) {
                        if ((taskNum != 0)) {
                            response = gson.toJson(taskManager.getTaskByNumber(taskNum));
                            break;
                        }
                        if (splitPath.length == 3) {
                            response = gson.toJson(taskManager.getlistTask());
                            break;
                        } else {
                            response = "Error! Ошибка в URI! Введите корректный адрес";
                            break;
                        }
                    }
                    if (splitPath[2].equals("epic")) {
                        response = gson.toJson(taskManager.getlistEpicTask());
                        break;
                    }
                    if (splitPath[2].equals("subtask")) {
                        if (splitPath.length == 3) {
                            response = gson.toJson(taskManager.getlistSubTask());
                            break;
                        }
                        if (splitPath[3].equals("epic")) {
                            if ((taskNum != 0) && (taskManager.getlistEpicTask().containsKey(taskNum))) {
                                ArrayList<Task> listTask = new ArrayList<>();
                                for (int num : taskManager.getSubTaskByEpicNumber(taskNum)) {
                                    listTask.add(taskManager.getTaskByNumber(num));
                                }
                                response = gson.toJson(listTask);
                                break;
                            } else {
                                response = "Error! Ошибка в URI! Введите корректный адрес";
                                break;
                            }
                        }
                    }
                    if (splitPath[2].equals("history")) {
                        response = gson.toJson(taskManager.getHistory());
                        break;
                    } else {
                        response = "Error! Ошибка в URI! Введите корректный адрес";
                    }
                    break;
                case "POST":
                    InputStream inputStream = httpExchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                    if (body.contains("EPICTASK")) {
                        EpicTask task = gson.fromJson(body, EpicTask.class);
                        response = "POST";
                        if (taskManager.getlistEpicTask().containsKey(task.getTaskId())) {
                            taskManager.refreshTask(task);
                        } else {
                            taskManager.createTask(task);
                        }
                    } else if (body.contains("SUBTASK")) {
                        SubTask task = gson.fromJson(body, SubTask.class);
                        response = "POST";
                        if (taskManager.getlistSubTask().containsKey(task.getTaskId())) {
                            taskManager.refreshTask(task);
                        } else {
                            taskManager.createTask(task);
                        }
                    } else if (body.contains("TASK")) {
                        Task task = gson.fromJson(body, Task.class);
                        response = "POST";
                        if (taskManager.getlistTask().containsKey(task.getTaskId())) {
                            taskManager.refreshTask(task);
                        } else {
                            taskManager.createTask(task);
                        }
                    } else {
                        response = null;
                    }
                    break;
                case "DELETE":
                    if (splitPath[2].equals("task")) {
                        if (taskNum != 0) {
                            taskManager.clearTaskByNumber(taskNum);
                            response = "Delete! Удалена задача под номером - " + taskNum;
                            break;
                        }
                        if (splitPath.length == 3) {
                            taskManager.clearTaskList();
                            response = "Delete! Удалены все задачи";
                            break;
                        } else {
                            response = "Error! Ошибка в URI! Введите корректный адрес";
                            break;
                        }
                    }
                    if (splitPath[2].equals("epic")) {
                        taskManager.clearEpicTaskList();
                        response = "Delete! Удалены все эпики и подзадачи";
                        break;
                    }
                    if (splitPath[2].equals("subtask")) {
                        taskManager.clearSubTaskList();
                        response = "Delete! Удалены все подзадачи";
                        break;
                    } else {
                        response = "Error! Ошибка в URI! Введите корректный адрес";
                        break;
                    }
            }
            if (response == null) {
                httpExchange.sendResponseHeaders(400, 0);
                response = "Непредвиденная ошибка! Проверьте корректность запроса";
            } else if (response.equals("null") || response.equals("{}")) {
                httpExchange.sendResponseHeaders(404, 0);
                response = "Данная задача отсутствует или ранее удалена.";
            } else if (response.contains("Error")) {
                httpExchange.sendResponseHeaders(404, 0);
            } else if (response.contains("Delete")) {
                httpExchange.sendResponseHeaders(200, 0);
            } else if (response.equals("POST")) {
                httpExchange.sendResponseHeaders(201, 0);
                response = "Загрузка успешно завершена";
            } else {
                httpExchange.sendResponseHeaders(200, 0);
            }

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private int getTaskNumber(URI requestURI) {
            int taskNum = 0;
            String line = requestURI.getRawQuery();
            if (line != null) {
                String subURI = line.substring(3);
                try {
                    taskNum = Integer.parseInt(subURI);
                } catch (NumberFormatException e) {
                    System.out.println("Error! Ошибка в URI! Введите корректный адрес");
                }
            }
            return taskNum;
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        httpTaskServer.start();
    }

    public void stop() {
        System.out.println("Останавливаем сервер на порту " + PORT);
        httpTaskServer.stop(0);
    }

}
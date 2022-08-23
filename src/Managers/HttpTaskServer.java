package Managers;

import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static Gson gson = new Gson();
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    public void httpTaskServer() throws IOException {       //метод запуска httpTaskServer
        InMemoryTaskManager taskManager = Managers.getDefault();
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

        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(taskManager));
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");


    }

    static class TaskHandler implements HttpHandler {
        InMemoryTaskManager taskManager;

        TaskHandler(InMemoryTaskManager taskManager) {
            this.taskManager = taskManager;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {    // метод обработки запросов httpTaskServer
            String response = null;
            Integer taskNum = null;
            String method = httpExchange.getRequestMethod();
            URI requestURI = httpExchange.getRequestURI();
            if (requestURI.toString().contains("=")) {
                String subURI = requestURI.toString().substring(requestURI.toString().indexOf('=') + 1
                );
                try {
                    taskNum = Integer.parseInt(subURI);
                } catch (NumberFormatException e) {
                    response = "Error! Ошибка в URI! Введите корректный адрес";
                }
            }
            String path = requestURI.getPath();
            String[] splitPath = path.split("/");

            switch (method) {
                case "GET":
                    if (splitPath.length == 2) {
                        ArrayList<Task> listTask = new ArrayList<>();
                        for (Task task : taskManager.getlistTask().values()) {
                            listTask.add(task);
                        } for (Task task : taskManager.getlistEpicTask().values()) {
                            listTask.add(task);
                        } for (Task task : taskManager.getlistSubTask().values()) {
                            listTask.add(task);
                        }
                        response = gson.toJson(listTask);
                        break;
                    }
                    if (splitPath[2].equals("task")) {
                        if (taskNum != null) {
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
                            if (taskNum != null) {
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
                    }
                case "POST":
                    InputStream inputStream = httpExchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                    if (body.contains("taskType")) {
                        taskManager.createTask(gson.fromJson(body, Task.class));
                } if (body.contains("epicType")) {
                    taskManager.createTask(gson.fromJson(body, EpicTask.class));
                } if (body.contains("subTaskType")) {
                    taskManager.createTask(gson.fromJson(body, SubTask.class));
                }
                            break;
                case "DELETE":
                    if (splitPath[2].equals("task")) {
                        if (taskNum != null) {
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
                    }
            }

            httpExchange.sendResponseHeaders(200, 0);

            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
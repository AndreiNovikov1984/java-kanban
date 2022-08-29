package Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private static final int PORT = 8078;
    public String host;
    private final String token;
    private final HttpClient client = HttpClient.newHttpClient();
    private final HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

    public KVTaskClient(String host) {
        this.host = host;
        this.token = authorization();
    }

    public void saveOnServer(String key, String backUpOnline) {  //метод сохранения данных на сервере
        URI uri = URI.create(host + PORT + "/save/" + key + "?API_TOKEN=" + token);
        HttpRequest request = null;

        request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(backUpOnline))
                .uri(uri)
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .header("Accept", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        try {
            HttpResponse<String> response = client.send(request, handler);
            System.out.println("Код статуса: " + response.statusCode());
        } catch (IOException | InterruptedException e) { // обработка ошибки отправки запроса
            System.out.println("Во время выполнения запроса сохранения данных возникла ошибка.\n" +
                    "Проверьте, пожалуйста, данные и повторите попытку.");
        }
    }

    public String loadFromServer(String key) {  //метод загрузки данных с сервера
        URI uri = URI.create(host + PORT + "/load/" + key + "?API_TOKEN=" + token);
        String backUpOnline = null;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .header("Accept", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        try {
            HttpResponse<String> response = client.send(request, handler);
            System.out.println("Код статуса: " + response.statusCode());
            backUpOnline = response.body();
        } catch (IOException | InterruptedException e) { // обработка ошибки отправки запроса
            System.out.println("Во время выполнения запроса загрузки данных возникла ошибка.\n" +
                    "Проверьте, пожалуйста, данные и повторите попытку.");
        }
        return backUpOnline;
    }

    private String authorization() {
        URI uri = URI.create(host + PORT + "/register/");
        String token = null;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .header("Accept", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        try {
            HttpResponse<String> response = client.send(request, handler);
            System.out.println("Код статуса: " + response.statusCode());
            token = response.body();
            System.out.println("Ответ: " + response.body());
        } catch (IOException | InterruptedException e) { // обработка ошибки отправки запроса
            System.out.println("Во время выполнения запроса загрузки данных возникла ошибка.\n" +
                    "Проверьте, пожалуйста, данные и повторите попытку.");
        }
        return token;
    }
}

package main.httpServer;
import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import main.taskManager.FileBackedTasksManager;
import main.taskPackage.Epic;
import main.taskPackage.Subtask;
import main.taskPackage.Task;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer implements HttpHandler{
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    static FileBackedTasksManager manager = new FileBackedTasksManager();
    String response;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateSerializer())
            .create();

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new HttpTaskServer());
        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        httpServer.stop(1);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();

        String[] splitStrings = path.split("/");
        String endPointAll = splitStrings[splitStrings.length-1];
        String endPointById = splitStrings[splitStrings.length-2];
        String endPointEpicSubtasks = splitStrings[splitStrings.length-3] + splitStrings[splitStrings.length-2];
        String[] splitId = splitStrings[splitStrings.length-1].split("=");
        int id = Integer.parseInt(splitId[splitId.length -1]);
        String method = httpExchange.getRequestMethod();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
        String body = bufferedReader.readLine();

        switch(method){
            case "GET":
                switch(endPointAll){
                    case "task":
                        response += gson.toJson(manager.getAllTask());
                        break;
                    case "epic":
                        response += gson.toJson(manager.getAllEpic());
                        break;
                    case "subtask":
                        response += gson.toJson(manager.getAllSubtusk());
                        break;
                    case "history":
                        response += gson.toJson(manager.getDefaultHistory());
                        break;
                    case "tasks":
                        response += gson.toJson(manager.getPrioritizedTasks());
                        break;
                }
                switch(endPointById){
                    case "task":
                        response += gson.toJson(manager.getTaskById(id));
                        break;
                    case "epic":
                        response += gson.toJson(manager.getEpicById(id));
                        break;
                    case "subtask":
                        response += gson.toJson(manager.getSubtaskById(id));
                        break;
                }
                switch(endPointEpicSubtasks){
                    case "subtaskepic":
                        response += gson.toJson(manager.getEpicById(id).getStorageSubtaskId());
                        break;
                }

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                break;
            case "POST":
                switch(endPointAll) {
                    case "task":
                        manager.createTask(gson.fromJson(body, Task.class));
                        break;
                    case "epic":
                        manager.createEpic(gson.fromJson(body, Epic.class));
                        break;
                }
                switch(endPointById) {
                    case "subtask":
                        manager.createSubTask(gson.fromJson(body, Subtask.class), manager.getEpicById(id));
                        break;
                }

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_CREATED, -1);
                break;
            case "DELETE":
                switch(endPointAll) {
                    case "task":
                        manager.removeAllTask();
                        break;
                    case "epic":
                        manager.removeAllEpic();
                        break;
                    case "subtask":
                        manager.removeAllSubtask();
                        break;
                }
                switch(endPointById) {
                    case "task":
                        manager.removeTask(id);
                        break;
                    case "epic":
                        manager.removeEpic(id);
                        break;
                    case "subtask":
                        manager.removeSubtask(id);
                        break;
                }

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NO_CONTENT, -1);
                break;
            default:
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                break;
        }
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes(DEFAULT_CHARSET));
        }
    }
}

class LocalDateSerializer implements JsonSerializer <LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

    @Override
    public JsonElement serialize(LocalDateTime localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate));
    }
}
package main.httpServer;
import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import main.taskManager.FileBackedTasksManager;
import main.taskManager.InMemoryTaskManager;
import main.taskPackage.Epic;
import main.taskPackage.Subtask;
import main.taskPackage.Task;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
   // static FileBackedTasksManager manager = new FileBackedTasksManager();
    static InMemoryTaskManager manager = new InMemoryTaskManager();


    public static void main(String[] args) throws IOException, InterruptedException {

        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new PostsHandler());

        httpServer.start();

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        //httpServer.stop(1);
    }

    static class PostsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "";
            String path = httpExchange.getRequestURI().getPath();

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateSerializer())
                    .create();

            String[] splitStrings = path.split("/");

            String method = httpExchange.getRequestMethod();

            switch(method){
                case "GET":
                    if(splitStrings[splitStrings.length-1].equals("task")){
                        response += gson.toJson(manager.getAllTask());
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-1].equals("epic")){
                        response += gson.toJson(manager.getAllEpic());
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-1].equals("subtask")){
                        response += gson.toJson(manager.getAllSubtusk());
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-2].equals("task")) {
                        String[] splitId = splitStrings[splitStrings.length-1].split("=");
                        response += gson.toJson(manager.getTaskById(Integer.parseInt(splitId[splitId.length-1])));
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-2].equals("epic")) {
                        String[] splitId = splitStrings[splitStrings.length-1].split("=");
                        response += gson.toJson(manager.getEpicById(Integer.parseInt(splitId[splitId.length-1])));
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-2].equals("subtask")) {
                        String[] splitId = splitStrings[splitStrings.length-1].split("=");
                        response += gson.toJson(manager.getSubtaskById(Integer.parseInt(splitId[splitId.length-1])));
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-3].equals("subtask") & splitStrings[splitStrings.length-2].equals("epic")) {
                        String[] splitEpicId = splitStrings[splitStrings.length-1].split("=");
                        response += gson.toJson(manager.getEpicById(manager.getSubtaskById(Integer.parseInt(splitEpicId[splitEpicId.length-1])).getEpicId()));
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-1].equals("history")) {
                        response += gson.toJson(manager.getDefaultHistory());
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-1].equals("tasks")) {
                       // response += gson.toJson(manager.getPrioritizedTasks());
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    } else {
                        httpExchange.sendResponseHeaders(404, 0);
                        break;
                    }
                case "POST":
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
                    String body = bufferedReader.readLine();

                    if(splitStrings[splitStrings.length-1].equals("task")){
                        Task test = gson.fromJson(body, Task.class);
                        manager.createTask(test);
                        httpExchange.sendResponseHeaders(201, -1);
                        break;
                    } else if(splitStrings[splitStrings.length-1].equals("epic")) {
                        Task task = gson.fromJson(body, Task.class);
                        Epic epic = new Epic(task.getTaskName(), task.getDescription());
                        manager.createEpic(epic);
                        httpExchange.sendResponseHeaders(201, -1);
                        break;
                    } else if(splitStrings[splitStrings.length-2].equals("subtask")) {
                        String[] splitEpicId = splitStrings[splitStrings.length-1].split("=");
                        Task task = gson.fromJson(body, Task.class);
                        Subtask subtask = new Subtask(task.getTaskName(), task.getDescription());
                        manager.createSubTask(subtask, manager.getEpicById(Integer.parseInt(splitEpicId[splitEpicId.length-1])));
                        httpExchange.sendResponseHeaders(201, -1);
                        break;
                    } else {
                        httpExchange.sendResponseHeaders(404, 0);
                        break;
                    }
                case "DELETE":
                    if(splitStrings[splitStrings.length-1].equals("task")){
                        manager.removeAllTask();
                        httpExchange.sendResponseHeaders(204, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-1].equals("epic")){
                        manager.removeAllEpic();
                        httpExchange.sendResponseHeaders(204, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-1].equals("subtask")){
                        manager.removeAllSubtask();
                        httpExchange.sendResponseHeaders(204, 0);
                        break;
                    } else if(splitStrings[splitStrings.length-2].equals("task")) {
                        String[] splitId = splitStrings[splitStrings.length-1].split("=");
                        manager.removeTask(Integer.parseInt(splitId[splitId.length-1]));
                        httpExchange.sendResponseHeaders(204, -1);
                        break;
                    } else if(splitStrings[splitStrings.length-2].equals("epic")) {
                        String[] splitId = splitStrings[splitStrings.length-1].split("=");
                        manager.removeEpic(Integer.parseInt(splitId[splitId.length-1]));
                        httpExchange.sendResponseHeaders(204, -1);
                        break;
                    } else if(splitStrings[splitStrings.length-2].equals("subtask")) {
                        String[] splitId = splitStrings[splitStrings.length-1].split("=");
                        manager.removeSubtask(Integer.parseInt(splitId[splitId.length-1]));
                        httpExchange.sendResponseHeaders(204, -1);
                        break;
                    } else {
                        httpExchange.sendResponseHeaders(404, 0);
                        break;
                    }
            }
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes(DEFAULT_CHARSET));
            }

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
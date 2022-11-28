package tests;

import com.sun.net.httpserver.HttpServer;
import main.httpClient.KVTaskClient;
import main.httpServer.KVServer;
import main.taskManager.HttpTaskManager;
import main.taskManager.InMemoryTaskManager;
import main.taskPackage.Task;
import main.taskPackage.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest {
    private static HttpTaskManager manager;
    private static KVServer server;

    static {
        try {
            server = new KVServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void startServer() {
        server.start();
        manager = new HttpTaskManager("http://localhost:8078");
    }

    @AfterEach
    void stopServer() {
        server.stop();
    }

    @Test
    void getClient() {
        final KVTaskClient testGetClient = manager.getClient();

        assertNotNull(testGetClient, "Client не задан");
    }

    @Test
    void save() {
        Task product = new Task("Поход в магазин", "Купить продуктов на ужин", TaskStatus.NEW, 1000, LocalDateTime.now());

        manager.storageTask.put(111111, product);
        manager.save();

        assertNotNull(manager.getClient().load("111111"), "Save ничего не сохранил");
    }
}
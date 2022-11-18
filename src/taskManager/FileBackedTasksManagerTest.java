package taskManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskPackage.Epic;
import taskPackage.Subtask;
import taskPackage.Task;
import taskPackage.TaskStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest {
    private static Path testFile;
    private static FileBackedTasksManager test;
    @AfterEach
    void removeFile() throws IOException {
        Files.delete(Paths.get("resources", "testFile.csv"));
    }

    @BeforeEach
    void beforeEach() throws IOException {
        testFile = Files.createFile(Paths.get("resources", "testFile.csv"));
        test = new FileBackedTasksManager(testFile);
    }

    @Test
    void testEmptyListTask() throws IOException {
        Task product = new Task("Поход в магазин", "Купить продуктов на ужин", TaskStatus.NEW, 100, LocalDateTime.now());

        test.createTask(product);
        test.removeTask(100001);

        Reader fileReader = new FileReader("resources/testFile.csv");
        BufferedReader br = new BufferedReader(fileReader);
        List<String> line = new ArrayList<>();
        while (br.ready()) {
            line.add(br.readLine());
            System.out.println(line);
        }
        br.close();

        assertEquals(1, line.size(), "Список задач не пуст");
        System.out.println(test.getDefaultHistory().toString());

        FileBackedTasksManager test2 = FileBackedTasksManager.loadFromFile(testFile.toFile());

        System.out.println("--------------\nВывод истории из нового менеджера");
        System.out.println(test2.getDefaultHistory().toString());

        assertEquals(0, test2.getDefaultHistory().size(), "История не пуста");
    }

    @Test
    void testEpicWIthOutSubtask() throws IOException {
        Epic product = new Epic("Поход в магазин", "Купить продуктов на ужин");

        test.createEpic(product);

        Reader fileReader = new FileReader("resources/testFile.csv");
        BufferedReader br = new BufferedReader(fileReader);
        List<String> line = new ArrayList<>();
        while (br.ready()) {
            line.add(br.readLine());
            System.out.println(line);
        }
        br.close();

        assertEquals(2, line.size(), "Список не только из эпика");
        System.out.println(test.getDefaultHistory().toString());

        FileBackedTasksManager test2 = FileBackedTasksManager.loadFromFile(testFile.toFile());

        System.out.println("--------------\nВывод истории из нового менеджера");
        System.out.println(test2.getDefaultHistory().toString());

        assertEquals(0, test2.getDefaultHistory().size(), "История не пуста");
    }
}
package tests;

import main.taskManager.InMemoryTaskManager;
import main.taskPackage.Task;
import main.taskPackage.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    @Test
    void add() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Test add", "description", TaskStatus.NEW);
        manager.add(task);

        final List<Task> history = manager.getDefaultHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void addDuplication() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Test addDublication", "description", TaskStatus.NEW);
        manager.add(task);
        manager.add(task);
        manager.add(task);

        final List<Task> history = manager.getDefaultHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не дублируется.");
    }

    @Test
    void remove() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Task task1 = new Task("Test 1", "description", TaskStatus.NEW);
        Task task2 = new Task("Test 2", "description", TaskStatus.NEW);
        Task task3 = new Task("Test 3", "description", TaskStatus.NEW);
        Task task4 = new Task("Test 4", "description", TaskStatus.NEW);
        Task task5 = new Task("Test 5", "description", TaskStatus.NEW);
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(task3);
        manager.createTask(task4);
        manager.createTask(task5);

        manager.getTaskById(100001);
        manager.getTaskById(100002);
        manager.getTaskById(100003);
        manager.getTaskById(100004);
        manager.getTaskById(100005);

        manager.removeTask(100001);
        manager.removeTask(100003);
        manager.removeTask(100005);

        final List<Task> history = manager.getDefaultHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(100002, history.get(0).getId(), "История удалена, а не должна была.");
        assertEquals(100004, history.get(1).getId(), "История удалена, а не должна была.");
    }

    @Test
    void getHistory() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Task task1 = new Task("Test 1", "description", TaskStatus.NEW);
        Task task2 = new Task("Test 2", "description", TaskStatus.NEW);
        manager.createTask(task1);
        manager.createTask(task2);

        manager.getTaskById(100001);
        manager.getTaskById(100002);
        manager.getTaskById(100001);
        manager.getTaskById(100002);

        final List<Task> history = manager.getDefaultHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(2, history.size(), "История содержит дубли.");
    }
}
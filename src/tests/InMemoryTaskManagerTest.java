package tests;

import main.taskManager.InMemoryTaskManager;
import main.taskPackage.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.taskPackage.Epic;
import main.taskPackage.Subtask;
import main.taskPackage.TaskStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static InMemoryTaskManager manager;

    @BeforeEach
    void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void setId() {
        final int idAfterGenerate = 100001;
        final int id = manager.setId();

        assertNotNull(id, "Id не задан");
        assertEquals(idAfterGenerate, id, "Id не совпадают");
    }

    @Test
    void getAllTask() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        manager.createTask(task);

        final List<Task> listTask = manager.getAllTask();

        assertNotNull(listTask, "List пустой");
        assertEquals(1, listTask.size(), "Размеры не совпадают");
    }

    @Test
    void getAllEpic() {
        Epic task = new Epic("Test setId", "description");
        manager.createEpic(task);

        final List<Epic> listTask = manager.getAllEpic();

        assertNotNull(listTask, "List пустой");
        assertEquals(1, listTask.size(), "Размеры не совпадают");
    }

    @Test
    void getAllSubtusk() {
        Subtask task = new Subtask("Test setId", "description", TaskStatus.NEW);
        Epic task2 = new Epic("Test setId", "description");
        manager.createEpic(task2);
        manager.createSubTask(task, task2);

        final List<Subtask> listTask = manager.getAllSubtusk();

        assertNotNull(listTask, "List пустой");
        assertEquals(1, listTask.size(), "Размеры не совпадают");
    }

    @Test
    void removeAllTask() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        manager.createTask(task);
        manager.removeAllTask();

        final List<Task> listTask = manager.getAllTask();

        assertNull(listTask, "List не пустой");
    }

    @Test
    void removeAllEpic() {
        Epic task = new Epic("Test setId", "description");
        manager.createEpic(task);
        manager.removeAllEpic();

        final List<Epic> listTask = manager.getAllEpic();

        assertNull(listTask, "List не пустой");
    }

    @Test
    void removeAllSubtask() {
        Subtask task = new Subtask("Test setId", "description", TaskStatus.NEW);
        Epic task2 = new Epic("Test setId", "description");
        manager.createEpic(task2);
        manager.createSubTask(task, task2);
        manager.removeAllSubtask();

        final List<Subtask> listTask = manager.getAllSubtusk();

        assertNull(listTask, "List не пустой");
    }

    @Test
    void createTask() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        manager.createTask(task);

        final List<Task> listTask = manager.getAllTask();

        assertNotNull(listTask, "List пустой");
        assertEquals(1, listTask.size(), "Размеры не совпадают");
    }

    @Test
    void createEpic() {
        Epic task = new Epic("Test setId", "description");
        manager.createEpic(task);

        final List<Epic> listTask = manager.getAllEpic();

        assertNotNull(listTask, "List пустой");
        assertEquals(1, listTask.size(), "Размеры не совпадают");
    }

    @Test
    void createSubTask() {
        Subtask task = new Subtask("Test setId", "description", TaskStatus.NEW);
        Epic task2 = new Epic("Test setId", "description");
        manager.createEpic(task2);
        manager.createSubTask(task, task2);

        final List<Subtask> listTask = manager.getAllSubtusk();

        assertNotNull(listTask, "List пустой");
        assertEquals(1, listTask.size(), "Размеры не совпадают");
    }

    @Test
    void removeTask() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        manager.createTask(task);
        manager.removeTask(100001);

        final List<Task> listTask = manager.getAllTask();

        assertNull(listTask, "List не пустой");
    }

    @Test
    void removeEpic() {
        Epic task = new Epic("Test setId", "description");
        manager.createEpic(task);
        manager.removeEpic(100001);

        final List<Epic> listTask = manager.getAllEpic();

        assertNull(listTask, "List не пустой");
    }

    @Test
    void removeSubtask() {
        Subtask task = new Subtask("Test setId", "description", TaskStatus.NEW);
        Subtask task2 = new Subtask("Test 2", "description", TaskStatus.NEW);
        Epic task3 = new Epic("Test setId", "description");
        manager.createEpic(task3);
        manager.createSubTask(task, task3);
        manager.createSubTask(task2, task3);
        manager.removeSubtask(100002);

        final List<Subtask> listTask = manager.getAllSubtusk();

        assertNotNull(listTask, "List пустой");
        assertEquals(1, listTask.size(), "Размеры не совпадают");
    }

    @Test
    void getTaskById() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        manager.createTask(task);

        final Task testTask = manager.getTaskById(100001);

        assertNotNull(testTask, "Задачи не существует");
        assertEquals(task, testTask, "Задачи не совпадают");
    }

    @Test
    void getEpicById() {
        Epic task = new Epic("Test setId", "description");
        manager.createEpic(task);

        final Epic testTask = manager.getEpicById(100001);

        assertNotNull(testTask, "Задачи не существует");
        assertEquals(task, testTask, "Задачи не совпадают");
    }

    @Test
    void getSubtaskById() {
        Subtask task = new Subtask("Test setId", "description", TaskStatus.NEW);
        Epic task2 = new Epic("Test setId", "description");
        manager.createEpic(task2);
        manager.createSubTask(task, task2);

        final Subtask testTask = manager.getSubtaskById(100002);

        assertNotNull(testTask, "Задачи не существует");
        assertEquals(task, testTask, "Задачи не совпадают");
    }

    @Test
    void updateTask() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        manager.createTask(task);
        manager.updateTask(task, TaskStatus.IN_PROGRESS);
        final Task testTask = manager.getTaskById(100001);

        assertNotNull(testTask, "Задачи не существует");
        assertEquals(TaskStatus.IN_PROGRESS, testTask.getStatus(), "Статусы не совпадают");
    }

    @Test
    void updateSubtask() {
        Subtask task = new Subtask("Test setId", "description", TaskStatus.NEW);
        Epic task2 = new Epic("Test setId", "description");
        manager.createEpic(task2);
        manager.createSubTask(task, task2);
        manager.updateSubtask(task, TaskStatus.IN_PROGRESS);
        final Subtask testTask = manager.getSubtaskById(100002);

        assertNotNull(testTask, "Задачи не существует");
        assertEquals(TaskStatus.IN_PROGRESS, testTask.getStatus(), "Статусы не совпадают");
    }

    @Test
    void getAllTaskForEpic() {
        Subtask task = new Subtask("Test setId", "description", TaskStatus.NEW);
        Epic task2 = new Epic("Test setId", "description");
        manager.createEpic(task2);
        manager.createSubTask(task, task2);

        final ArrayList<Subtask> testTask = manager.getAllTaskForEpic(task2);

        assertNotNull(testTask, "Задачи не существует");
        assertEquals(1, testTask.size(), "Размеры не совпадают");
    }

    @Test
    void add() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        manager.createTask(task);
        manager.add(task);
        final Integer testTask = manager.historyMap.size();

        assertNotNull(testTask, "Задачи не существует");
        assertEquals(1, testTask, "Задача не добавлена");
    }

    @Test
    void getDefaultHistory() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        Task task2 = new Task("Test setId", "description", TaskStatus.NEW);
        manager.createTask(task);
        manager.createTask(task2);
        manager.getTaskById(100001);
        manager.getTaskById(100002);
        manager.getTaskById(100001);

        final ArrayList<Task> testTask = manager.getDefaultHistory();

        assertNotNull(testTask, "Задачи не существует");
        assertEquals(2, testTask.size(), "Задачи не совпадают");
    }
}
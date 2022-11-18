package taskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskPackage.Epic;
import taskPackage.Subtask;
import taskPackage.Task;
import taskPackage.TaskStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private static InMemoryHistoryManager historyManager;
    private static Epic childGoSchool;

    @BeforeEach
    void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        childGoSchool = new Epic("Собрать ребенка в школу", "Подготовить ребенка к учебному году");
        historyManager.linkLast(childGoSchool);
    }

    @Test
    void linkLast() {
        final Task testLinkEpic = historyManager.getTasks().get(0);

        assertNotNull(testLinkEpic, "Задача не добавлена");
        assertEquals(childGoSchool, testLinkEpic, "Задачи не совпадают");
    }

    @Test
    void getTasks() {
        final Task testLinkEpic = historyManager.getTasks().get(0);

        assertNotNull(testLinkEpic, "Задача не возвращается");
        assertEquals(childGoSchool, testLinkEpic, "Задачи не совпадают");
    }

    @Test
    void removeNode() {
        Epic childGoHome = new Epic("Собрать ребенка домой", "Подготовить ребенка к домашнему году");

        final List<InMemoryHistoryManager.Node<Task>> testLinkEpic = new ArrayList<>();
        testLinkEpic.add(historyManager.getTail());

        historyManager.linkLast(childGoHome);
        historyManager.removeNode(historyManager.getTail());

        assertEquals(1, testLinkEpic.size(), "Node не удален");
        assertEquals(historyManager.getTail(), testLinkEpic.get(0), "Удален не тот Node");
    }

    @Test
    void getTail() {
        Epic childGoHome = new Epic("Собрать ребенка домой", "Подготовить ребенка к домашнему году");

        historyManager.linkLast(childGoHome);
        final InMemoryHistoryManager.Node<Task> testLinkEpic = historyManager.getTail();

        assertNotNull(testLinkEpic, "Tail не возвращается");
        assertEquals(historyManager.getTail(), testLinkEpic, "Tail не совпадает");
    }
}
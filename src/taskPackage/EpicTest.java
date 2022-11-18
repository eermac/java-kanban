package taskPackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManager.InMemoryTaskManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private static InMemoryTaskManager inMemoryTaskManager;
    private static Epic childGoSchool;
    private static Subtask schoolBag;
    private static Subtask schoolPlace;
    private static int testSubtaskId;
    private static ArrayList<Integer> testId;

    @BeforeEach
    void beforeEach() {
        inMemoryTaskManager = new InMemoryTaskManager();
        childGoSchool = new Epic("Собрать ребенка в школу", "Подготовить ребенка к учебному году");
        schoolBag = new Subtask("Купить рюкзак", "Купить школьный рюкзак");
        schoolPlace = new Subtask("Показать школу", "Показать ребенку где школа");
        testSubtaskId = 100001;
        testId = new ArrayList<>();

        inMemoryTaskManager.createEpic(childGoSchool);
        inMemoryTaskManager.createSubTask(schoolBag, childGoSchool);
        inMemoryTaskManager.createSubTask(schoolPlace, childGoSchool);
        testId.add(testSubtaskId);
    }

    @Test
    void setStorageSubtaskId() {
        childGoSchool.setStorageSubtaskId(testId);

        assertEquals(childGoSchool.getStorageSubtaskId().get(0), testId.get(0), "Id subtask не совпадают");
    }

    @Test
    void getStorageSubtaskId() {
        childGoSchool.setStorageSubtaskId(testId);

        assertEquals(childGoSchool.getStorageSubtaskId().get(0), testId.get(0), "Id subtask не совпадают");
    }

    @Test
    void testEmptyListSubtask() {
        assertEquals(childGoSchool.getStatus(), TaskStatus.NEW, "Статусы не совпадают");
    }

    @Test
    void testOnlyNewStatusListSubtask() {
        final TaskStatus schoolBagStatus = schoolBag.getStatus();
        final TaskStatus schoolPlaceStatus= schoolPlace.getStatus();

        assertEquals(schoolBagStatus, TaskStatus.NEW, "Статусы не совпадают");
        assertEquals(schoolPlaceStatus, TaskStatus.NEW, "Статусы не совпадают");
        assertEquals(childGoSchool.getStatus(), TaskStatus.NEW, "Статусы не совпадают");
    }

    @Test
    void testOnlyDoneStatusListSubtask() {
        inMemoryTaskManager.updateSubtask(schoolBag, TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(schoolPlace, TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(schoolBag, TaskStatus.DONE);
        inMemoryTaskManager.updateSubtask(schoolPlace, TaskStatus.DONE);

        final TaskStatus schoolBagStatus = inMemoryTaskManager.getSubtaskById(100002).getStatus();
        final TaskStatus schoolPlaceStatus= inMemoryTaskManager.getSubtaskById(100003).getStatus();
        final TaskStatus childGoSchoolStatus = inMemoryTaskManager.getEpicById(100001).getStatus();

        assertEquals(schoolBagStatus, TaskStatus.DONE, "Статусы не совпадают");
        assertEquals(schoolPlaceStatus, TaskStatus.DONE, "Статусы не совпадают");
        assertEquals(childGoSchoolStatus, TaskStatus.DONE, "Статусы не совпадают");
    }

    @Test
    void testNewAndDoneStatusListSubtask() {
        inMemoryTaskManager.updateSubtask(schoolBag, TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(schoolBag, TaskStatus.DONE);

        final TaskStatus schoolBagStatus = inMemoryTaskManager.getSubtaskById(100002).getStatus();
        final TaskStatus schoolPlaceStatus= inMemoryTaskManager.getSubtaskById(100003).getStatus();
        final TaskStatus childGoSchoolStatus = inMemoryTaskManager.getEpicById(100001).getStatus();

        assertEquals(schoolBagStatus, TaskStatus.DONE, "Статусы не совпадают");
        assertEquals(schoolPlaceStatus, TaskStatus.NEW, "Статусы не совпадают");
        assertEquals(childGoSchoolStatus, TaskStatus.IN_PROGRESS, "Статусы не совпадают");
    }

    @Test
    void testOnlyProgressStatusListSubtask() {
        inMemoryTaskManager.updateSubtask(schoolBag, TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(schoolPlace, TaskStatus.IN_PROGRESS);

        final TaskStatus schoolBagStatus = inMemoryTaskManager.getSubtaskById(100002).getStatus();
        final TaskStatus schoolPlaceStatus= inMemoryTaskManager.getSubtaskById(100003).getStatus();
        final TaskStatus childGoSchoolStatus = inMemoryTaskManager.getEpicById(100001).getStatus();

        assertEquals(schoolBagStatus, TaskStatus.IN_PROGRESS, "Статусы не совпадают");
        assertEquals(schoolPlaceStatus, TaskStatus.IN_PROGRESS, "Статусы не совпадают");
        assertEquals(childGoSchoolStatus, TaskStatus.IN_PROGRESS, "Статусы не совпадают");
    }
}
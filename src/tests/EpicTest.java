package tests;

import main.taskManager.InMemoryTaskManager;
import main.taskPackage.Epic;
import main.taskPackage.Subtask;
import main.taskPackage.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        //a. Пустой список подзадач.
        Assertions.assertEquals(childGoSchool.getStatus(), TaskStatus.NEW, "Статусы не совпадают");
    }

    @Test
    void testOnlyNewStatusListSubtask() {
        //b. Все подзадачи со статусом NEW.
        final TaskStatus schoolBagStatus = schoolBag.getStatus();
        final TaskStatus schoolPlaceStatus= schoolPlace.getStatus();

        assertEquals(schoolBagStatus, TaskStatus.NEW, "Статусы не совпадают");
        assertEquals(schoolPlaceStatus, TaskStatus.NEW, "Статусы не совпадают");
        assertEquals(childGoSchool.getStatus(), TaskStatus.NEW, "Статусы не совпадают");
    }

    @Test
    void testOnlyDoneStatusListSubtask() {
        //c. Все подзадачи со статусом DONE.
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
        //d. Подзадачи со статусами NEW и DONE.
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
        //e. Подзадачи со статусом IN_PROGRESS.
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
package tests;

import main.taskPackage.Task;
import main.taskPackage.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void getId() {
        Task task = new Task("Test getId", "description", TaskStatus.NEW);
        final int testId = 5;

        task.setId(testId);

        final int taskId = task.getId();

        assertNotNull(taskId, "Id задачи не задан");
        assertEquals(testId, taskId, "Id не совпадают");
    }

    @Test
    void setId() {
        Task task = new Task("Test setId", "description", TaskStatus.NEW);
        final int testId = 5;

        task.setId(testId);

        final int taskId = task.getId();

        assertNotNull(taskId, "Id задачи не задан");
        assertEquals(testId, taskId, "Id не совпадают");
    }

    @Test
    void getTaskName() {
        Task task = new Task("Test getTaskName", "description", TaskStatus.NEW);
        final String testTaskName = task.getTaskName();

        assertNotNull(testTaskName, "Name задачи не задан");
        assertEquals("Test getTaskName", testTaskName, "Name не совпадают");
    }

    @Test
    void getDescription() {
        Task task = new Task("Test getTaskDescription", "description", TaskStatus.NEW);
        final String testTaskDescription = task.getDescription();

        assertNotNull(testTaskDescription, "Description задачи не задан");
        assertEquals("description", testTaskDescription, "Description не совпадают");
    }

    @Test
    void getStatus() {
        Task taskStatusNew = new Task("Test getStatus NEW", "description", TaskStatus.NEW);
        Task taskStatusProgress = new Task("Test getStatus IN_PROGRESS", "description", TaskStatus.IN_PROGRESS);
        Task taskStatusDone = new Task("Test getStatus DONE", "description", TaskStatus.DONE);

        final TaskStatus testTaskStatusNew = taskStatusNew.getStatus();

        assertNotNull(testTaskStatusNew, "Status задачи не задан");
        assertEquals(TaskStatus.NEW, testTaskStatusNew, "Status не совпадают");

        final TaskStatus testTaskStatusProgress = taskStatusProgress.getStatus();

        assertNotNull(testTaskStatusProgress, "Status задачи не задан");
        assertEquals(TaskStatus.IN_PROGRESS, testTaskStatusProgress, "Status не совпадают");

        final TaskStatus testTaskStatusDone = taskStatusDone.getStatus();

        assertNotNull(testTaskStatusDone, "Status задачи не задан");
        assertEquals(TaskStatus.DONE, testTaskStatusDone, "Status не совпадают");
    }

    @Test
    void getDuration(){
        Task taskStatusNew = new Task("Test getStatus NEW", "description", TaskStatus.NEW, 100, LocalDateTime.now());

        final long testDuration = taskStatusNew.getDuration();

        assertNotNull(testDuration, "Duration задачи не задан");
        assertEquals(100, testDuration, "Duration не совпадают");
    }

    @Test
    void getStartTime(){
        final LocalDateTime testTimeNow = LocalDateTime.now();
        Task taskStatusNew = new Task("Test getStatus NEW", "description", TaskStatus.NEW, 100, testTimeNow);

        final LocalDateTime testStartTime = taskStatusNew.getStartTime();

        assertNotNull(testStartTime, "StartTime задачи не задан");
        assertEquals(testTimeNow, testStartTime, "StartTime не совпадают");
    }

    @Test
    void getEndTime(){
        final LocalDateTime testTimeNow = LocalDateTime.now();
        Task taskStatusNew = new Task("Test getStatus NEW", "description", TaskStatus.NEW, 100, testTimeNow);

        final Duration testTime = taskStatusNew.getEndTime();
        final Duration testEndTime = Duration.between(testTimeNow, testTimeNow.plusMinutes(100));

        assertNotNull(testTime, "EndTime задачи не задан");
        assertEquals(testEndTime, testTime, "EndTime не совпадают");
    }
}
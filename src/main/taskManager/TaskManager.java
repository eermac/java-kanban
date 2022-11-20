package main.taskManager;

import main.taskPackage.Epic;
import main.taskPackage.Subtask;
import main.taskPackage.Task;
import main.taskPackage.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    public Integer setId();

    public ArrayList<Task> getAllTask();

    public ArrayList<Epic> getAllEpic();

    public ArrayList<Subtask> getAllSubtusk();

    public void removeAllTask();

    public void removeAllEpic();

    public void removeAllSubtask();

    public void createTask(Task object);

    public void createEpic(Epic object);

    public void createSubTask(Subtask object, Epic storageSubtask);

    public void removeTask(Integer Id);

    public void removeEpic(Integer Id);

    public void removeSubtask(Integer Id);

    public Task getTaskById(Integer Id);

    public Epic getEpicById(Integer Id);

    public Subtask getSubtaskById(Integer Id);

    public void updateTask(Task object, TaskStatus status);

    public void updateSubtask(Subtask object, TaskStatus status);

    public ArrayList<Subtask> getAllTaskForEpic(Epic object);

    public List<Task> getHistory();
}

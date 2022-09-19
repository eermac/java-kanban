package taskManager;

import taskPackage.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> taskHistory;

    public InMemoryHistoryManager(){
        this.taskHistory = new ArrayList<>();
    }
    public InMemoryHistoryManager(List<Task> taskHistory){
        this.taskHistory = taskHistory;
    }

    public void add(Task task){
        if(taskHistory.size() >= 10) {
            taskHistory.remove(0);
        }

        taskHistory.add(task);
    }

    public List<Task> getHistory() {
        return taskHistory;
    }

    @Override
    public String toString(){
        return "******\nСписок просмотренных задач - " + getHistory();
    }
}

package main.util;

import main.taskManager.HistoryManager;
import main.taskManager.InMemoryHistoryManager;
import main.taskManager.InMemoryTaskManager;
import main.taskManager.TaskManager;

public class Managers {
     public TaskManager getDefault(){
        TaskManager manager = new InMemoryTaskManager();
        return manager;
    }

    public HistoryManager getDefaultHistory(){
        InMemoryHistoryManager historyList = new InMemoryHistoryManager();
        return historyList;
    }

}

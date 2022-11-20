package main.util;

import main.taskManager.HistoryManager;
import main.taskManager.InMemoryHistoryManager;
import main.taskManager.InMemoryTaskManager;
import main.taskManager.TaskManager;

public final class Managers {
    static TaskManager getDefault(){
        TaskManager manager = new InMemoryTaskManager();
        return manager;
    }

    static HistoryManager getDefaultHistory(){
        InMemoryHistoryManager historyList = new InMemoryHistoryManager();
        return historyList;
    }

}

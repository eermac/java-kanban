package taskManager.Utils;

import taskManager.HistoryManager;
import taskManager.InMemoryHistoryManager;
import taskManager.InMemoryTaskManager;
import taskManager.TaskManager;

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

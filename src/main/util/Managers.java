package main.util;

import main.taskManager.*;
import main.taskPackage.Task;

public class Managers {
     public static HttpTaskManager getDefault(){
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078");
        return manager;
    }

    public HistoryManager getDefaultHistory(){
        InMemoryHistoryManager historyList = new InMemoryHistoryManager();
        return historyList;
    }

}

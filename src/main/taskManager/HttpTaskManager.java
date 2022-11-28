package main.taskManager;
import com.google.gson.Gson;
import main.httpClient.KVTaskClient;
import main.taskPackage.Task;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    private String address;
    private KVTaskClient client;

    public HttpTaskManager(String address){
        this.address = address;
        URI url =  URI.create(this.address + "/register");
        this.client = new KVTaskClient(url);
    }

    public KVTaskClient getClient(){
        return this.client;
    }

    @Override
    public void save(){
        List<Task> test = new ArrayList<>();
        Gson gson = new Gson();

        for(Task next: super.storageTask.values()){
            test.add(next);
        }

        for(Task next: super.storageEpic.values()){
            test.add(next);
        }

        for(Task next: super.storageSubtask.values()){
            test.add(next);
        }

        try {
            if(test.isEmpty()){
                throw new ManagerSaveException("Данных для записи нет");
            }
        } catch (ManagerSaveException exception){
            System.out.println(exception.getMessage());
        }

        for(Task next: test){
            client.put(next.getId() + "", gson.toJson(next));
        }
    }
}

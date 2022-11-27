package main.taskManager;
import com.google.gson.Gson;

import main.HttpClient.KVTaskClient;
import main.httpServer.KVServer;
import main.taskPackage.Task;
import main.taskPackage.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    private String address;
    private KVTaskClient client;

    HttpTaskManager(String address){
        this.address = address;
        URI url =  URI.create(this.address + "/register");
        this.client = new KVTaskClient(url);
    }

    public KVTaskClient getClient(){
        return this.client;
    }

    public String getAddress(){
        return this.address;
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


        public static void main(String[] args) throws IOException {
            new KVServer().start();

            HttpTaskManager manager = new HttpTaskManager("http://localhost:8078");

            Task product = new Task("Поход в магазин", "Купить продуктов на ужин", TaskStatus.NEW, 1000, LocalDateTime.now());
            manager.createTask(product);

            System.out.println(manager.getClient().load("100001"));
        }
}

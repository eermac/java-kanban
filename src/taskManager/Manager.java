package taskManager;

import taskPackage.Epic;
import taskPackage.Subtask;
import taskPackage.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> storageTask = new HashMap<>();
    HashMap<Integer, Epic> storageEpic = new HashMap<>();
    HashMap<Integer, Subtask> storageSubtask = new HashMap<>();

    int idGenerate = 100000;

    Integer setId(){
        idGenerate++;
        return this.idGenerate;
    }

    public ArrayList<Task> getAllTask(){
        ArrayList<Task> allTask = new ArrayList<>();

        if(storageTask.size() != 0) {
            for (Task next : storageTask.values()) {
                allTask.add(next);
            }
            return allTask;
        } else {
            return null;
        }
    }

    public ArrayList<Epic> getAllEpic(){
        ArrayList<Epic> allEpic = new ArrayList<>();

        if(storageEpic.size() != 0) {
            for (Epic next : storageEpic.values()) {
                allEpic.add(next);
            }
            return allEpic;
        } else {
            return null;
        }
    }

    public ArrayList<Subtask> getAllSubtusk(){
        ArrayList<Subtask> allSubtask = new ArrayList<>();

        if(storageSubtask.size() != 0) {
            for (Subtask next : storageSubtask.values()) {
                allSubtask.add(next);
            }
            return allSubtask;
        } else {
            return null;
        }
    }

    public void removeAllTask(){
        this.storageTask.clear();
    }

    public void removeAllEpic(){
        this.storageEpic.clear();
    }

    public void removeAllSubtask(){
        for(Epic next: storageEpic.values()){
            next.setStorageSubtaskId(null);

            Object objectEpic = next.changeStatus(storageSubtask);

            if(!objectEpic.equals(null)){
                storageEpic.put(next.getId(), (Epic) objectEpic);
            }
        }

        this.storageSubtask.clear();
    }

    public void createTask(Task object){
        int id = setId();

        object.setId(id);
        storageTask.put(id, object);
    }

    public void createEpic(Epic object){
        int id = setId();

        object.setId(id);
        storageEpic.put(id, object);
    }

    public void createSubTask(Subtask object, Epic storageSubtask){
        int id = setId();

        object.setId(id);
        object.setEpicId(storageSubtask.getId());
        this.storageSubtask.put(id, object);
        if(storageSubtask.getStorageSubtaskId().isEmpty()){
            ArrayList<Integer> subtask = new ArrayList<>();
            subtask.add(id);
            storageSubtask.setStorageSubtaskId(subtask);
        } else{
            ArrayList<Integer> subtask = storageSubtask.getStorageSubtaskId();
            subtask.add(id);
            storageSubtask.setStorageSubtaskId(subtask);
        }
    }

    public void removeTask(Integer Id){
        this.storageTask.remove(Id);
    }

    public void removeEpic(Integer Id){
        this.storageEpic.remove(Id);
    }

    public void removeSubtask(Integer Id){
        for(Epic next: storageEpic.values()){
            for(Integer next2: next.getStorageSubtaskId()){
                if(Id.equals(next2)){
                    ArrayList<Integer> newStorageSubtask = next.getStorageSubtaskId();
                    newStorageSubtask.remove(Id);
                    next.setStorageSubtaskId(newStorageSubtask);

                    Object objectEpic = next.changeStatus(this.storageSubtask);

                    if(!objectEpic.equals(null)){
                        storageEpic.put(next.getId(), (Epic) objectEpic);
                    }
                }
            }
        }

        this.storageSubtask.remove(Id);
    }

    public Task getTaskById(Integer Id){
        return storageTask.get(Id);
    }

    public Epic getEpicById(Integer Id){
        return storageEpic.get(Id);
    }

    public Subtask getSubtaskById(Integer Id){
        return storageSubtask.get(Id);
    }

    public void updateTask(Task object, String status){
        Task updatedTask = new Task(object.getTaskName(), object.getDescription(), status);
        updatedTask.setId(object.getId());
        this.storageTask.put(object.getId(), updatedTask);
    }

    public void updateSubtask(Subtask object, String status){
        Subtask updatedSubtask = new Subtask(object.getTaskName(), object.getDescription(), status);
        updatedSubtask.setId(object.getId());
        this.storageSubtask.put(object.getId(), updatedSubtask);

        for(Integer next: storageEpic.keySet()){
            for(Integer next2: storageEpic.get(next).getStorageSubtaskId()){
                if(next2.equals(object.getId())){
                    Epic updatedEpic = storageEpic.get(next).changeStatus(storageSubtask);
                    updatedEpic.setId(storageEpic.get(next).getId());
                    storageEpic.put(updatedEpic.getId(), updatedEpic);
                }
            }
        }
    }

    public ArrayList<Subtask> getAllTaskForEpic(Epic object){
        ArrayList<Subtask> allSubtask = new ArrayList<>();

        if(!object.getStorageSubtaskId().isEmpty()) {
            for (Integer next : object.getStorageSubtaskId()) {
                for (Integer next2 : storageSubtask.keySet()) {
                    if (next.equals(next2)) {
                        allSubtask.add(storageSubtask.get(next2));
                    }
                }
            }
            return allSubtask;
        } else {
            return null;
        }
    }
}

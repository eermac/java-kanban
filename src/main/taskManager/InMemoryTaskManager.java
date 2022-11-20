package main.taskManager;

import main.taskPackage.Epic;
import main.taskPackage.Subtask;
import main.taskPackage.Task;
import main.taskPackage.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager, HistoryManager{
    public HashMap<Integer, Task> storageTask = new HashMap<>();
    public HashMap<Integer, Epic> storageEpic = new HashMap<>();
    public HashMap<Integer, Subtask> storageSubtask = new HashMap<>();
    public HashMap<Integer, InMemoryHistoryManager.Node<Task>> historyMap = new HashMap<>();
    public InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private int idGenerate = 100000;

    @Override
    public Integer setId(){
        idGenerate++;
        return this.idGenerate;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void removeAllTask(){
        this.storageTask.clear();
    }

    @Override
    public void removeAllEpic(){
        this.storageEpic.clear();
    }

    @Override
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

    @Override
    public void createTask(Task object)  {
        if(null != object){
            int id = setId();

            object.setId(id);
            storageTask.put(id, object);
        }
    }

    @Override
    public void createEpic(Epic object){
        if(null != object){
            int id = setId();

            object.setId(id);
            storageEpic.put(id, object);
        }
    }

    @Override
    public void createSubTask(Subtask object, Epic storageSubtask){
        if(null != object & null != storageSubtask){
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
    }

    @Override
    public void removeTask(Integer Id){
        if(null != Id){
            historyManager.removeNode(historyMap.get(Id));
            historyMap.remove(Id);
            this.storageTask.remove(Id);
        }
    }

    @Override
    public void removeEpic(Integer Id){
        if(null != Id){
            historyManager.removeNode(historyMap.get(Id));
            historyMap.remove(Id);

            for(Integer next: this.storageEpic.get(Id).getStorageSubtaskId()){
                if(historyMap.containsKey(next)){
                    historyManager.removeNode(historyMap.get(next));
                    historyMap.remove(next);
                }
            }

            this.storageEpic.remove(Id);
        }
    }

    @Override
    public void removeSubtask(Integer Id){
        if(null != Id){
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
    }

    @Override
    public Task getTaskById(Integer Id){
        if(null != Id){
            add(storageTask.get(Id));
            return storageTask.get(Id);
        } else return null;
    }

    @Override
    public Epic getEpicById(Integer Id){
        if(null != Id){
            add(storageEpic.get(Id));
            return storageEpic.get(Id);
        } else return null;
    }

    @Override
    public Subtask getSubtaskById(Integer Id){
        if(null != Id){
            add(storageSubtask.get(Id));
            return storageSubtask.get(Id);
        } else return null;
    }

    @Override
    public void updateTask(Task object, TaskStatus status){
        if(null != object & null != status){
            Task updatedTask = new Task(object.getTaskName(), object.getDescription(), status);
            updatedTask.setId(object.getId());
            this.storageTask.put(object.getId(), updatedTask);
        }
    }

    @Override
    public void updateSubtask(Subtask object, TaskStatus status){
        if(null != object & null != status){
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
    }

    @Override
    public ArrayList<Subtask> getAllTaskForEpic(Epic object){
        if(null != object){
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
        } else return null;
    }

    @Override
    public List<Task> getHistory(){
        return null;
    }

    @Override
    public void add(Task task){
        if(null != task){
            if(historyMap.containsKey(task.getId())){
                historyManager.linkLast(task);
                historyManager.removeNode(historyMap.get(task.getId()));
                historyMap.put(task.getId(), historyManager.getTail());
            } else {
                historyManager.linkLast(task);
                historyMap.put(task.getId(), historyManager.getTail());
            }
        }
    }

    public ArrayList<Task> getDefaultHistory() {
        return historyManager.getTasks();
    }

    public void remove(int id){
    }
}
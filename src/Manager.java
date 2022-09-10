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

    String getTask(){
        String result = "";

        if(storageTask.size() != 0) {
            for (Task next : storageTask.values()) {
                result += next.toString(); //поправить next для получения задачи и переопределить toString()
            }
            return result;
        } else {
            result += "Нет задач для получения";
            return result;
        }
    }

    String getEpic(){
        String result = "";

        if(storageEpic.size() != 0) {
            for (Epic next : storageEpic.values()) {
                result += next.toString(); //поправить next для получения задачи и переопределить toString()
            }
            return result;
        } else {
            result += "Нет эпиков для получения";
            return result;
        }
    }

    String getSubtusk(){
        String result = "";

        if(storageSubtask.size() != 0) {
            for (Subtask next : storageSubtask.values()) {
                result += next.toString();
            }
            return result;
        } else {
            result += "Нет подзадач для получения";
            return result;
        }
    }

    void removeAllTask(){
        this.storageTask.clear();
    }

    void removeAllEpic(){
        this.storageEpic.clear();
    }

    void removeAllSubtask(){
        for(Epic next: storageEpic.values()){
            next.removeSubtask();
        }

        this.storageSubtask.clear();
    }

    void createTask(Task object){
        int id = setId();

        object.setId(id);
        object.setStatus("NEW");
        storageTask.put(id, object);
    }

    void createEpic(Epic object){
        int id = setId();

        object.setId(id);
        object.setStatus("NEW");
        storageEpic.put(id, object);
    }

    void createSubTask(Subtask object, Epic storageSubtask){
        int id = setId();

        object.setId(id);
        object.setStatus("NEW");
        this.storageSubtask.put(id, object);
        storageSubtask.addSubtask(id);
    }

    void removeTask(Integer identifier){
        this.storageTask.remove(identifier);
    }

    void removeEpic(Integer identifier){
        this.storageEpic.remove(identifier);
    }

    void removeSubtask(Integer identifier){
        for(Epic next: storageEpic.values()){
            for(Integer next2: next.storageSubtaskId){
                if(identifier.equals(next2)){
                    next.storageSubtaskId.remove(identifier);
                    next.changeStatus(this.storageSubtask);
                }
            }
        }
        this.storageSubtask.remove(identifier);
    }

    String getTaskById(Integer identifier){
        return storageTask.get(identifier).toString();
    }

    String getEpicById(Integer identifier){
        return storageEpic.get(identifier).toString();
    }

    String getSubtaskById(Integer identifier){
        return storageSubtask.get(identifier).toString();
    }

    void updateTask(Task object, String status){
        object.status = status;
        this.storageTask.put(object.identifier, object);
    }

    void updateSubtask(Subtask object, String status){
        object.status = status;
        this.storageSubtask.put(object.identifier, object);

        for(Integer next: storageEpic.keySet()){
            for(Integer next2: storageEpic.get(next).storageSubtaskId){
                if(next2.equals(object.identifier)){
                    storageEpic.get(next).changeStatus(storageSubtask);
                }
            }
        }
    }

    String getAllTaskForEpic(Epic object){
        String result = "";

        if(object.storageSubtaskId.size() > 0) {
            result = "Список всех подзадач эпика - " + object.taskName + "\n";

            for (Integer next : object.storageSubtaskId) {
                for (Integer next2 : storageSubtask.keySet()) {
                    if (next.equals(next2)) {
                        result += storageSubtask.get(next2).toString();
                    }
                }
            }
            return result;
        } else {
            result = "У эпика нет задач";

            return result;
        }
    }
}

package taskPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> storageSubtaskId;

    public Epic(String taskName, String description){
        super(taskName, description);
        this.storageSubtaskId = new ArrayList<>();
    }

    public Epic(String taskName, String description, TaskStatus status, ArrayList<Integer> storageSubtaskId){
        super(taskName, description, status);
        this.storageSubtaskId = storageSubtaskId;
    }

    public void setStorageSubtaskId(ArrayList<Integer> newStorage){
        this.storageSubtaskId = newStorage;
    }

    public ArrayList<Integer> getStorageSubtaskId(){
        return this.storageSubtaskId;
    }

    public Epic changeStatus(HashMap<Integer, Subtask> storageSubtask){
        if(storageSubtask.equals(null)){
            return null;
        } else {
            int size = storageSubtaskId.size();
            int countDoneStatus = 0;
            int countProgressStatus = 0;

            for(int i = 0; i < storageSubtaskId.size(); i++){
                for(Integer next: storageSubtask.keySet()){
                    if(storageSubtaskId.get(i).equals(next)){
                        if(storageSubtask.get(next).getStatus().equals(TaskStatus.DONE)){
                            countDoneStatus++;
                        } else if(storageSubtask.get(next).getStatus().equals(TaskStatus.IN_PROGRESS)){
                            countProgressStatus++;
                        }
                    }
                }
            }

            if(size == countDoneStatus){
                Epic updatedEpic = new Epic(getTaskName(), getDescription(), TaskStatus.DONE, getStorageSubtaskId());
                return updatedEpic;
            } else if(size > 0 && countProgressStatus > 0 && size != countDoneStatus){
                Epic updatedEpic = new Epic(getTaskName(), getDescription(), TaskStatus.IN_PROGRESS, getStorageSubtaskId());
                return updatedEpic;
            } else{
                return null;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Epic otherBook = (Epic) obj;
        return Objects.equals(getTaskName(), otherBook.getTaskName()) &&
                Objects.equals(getDescription(), otherBook.getDescription()) &&
                Objects.equals(getStatus(), otherBook.getStatus()) &&
                (getId() == otherBook.getId());
    }
    @Override
    public String toString(){
        return "******\nНазвание задачи - " + getTaskName() +
                "\nОписание - " + getDescription() +
                "\nИдентификатор - " + getId() +
                "\nСтатус - " + getStatus() + "\n";
    }
}

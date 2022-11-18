package taskPackage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> storageSubtaskId;
    private long duration;
    private LocalDateTime startTime;
    private Duration endTime;

    public Epic(String taskName, String description){
        super(taskName, description);
        this.storageSubtaskId = new ArrayList<>();
    }

    public Epic(String taskName, String description, TaskStatus status, ArrayList<Integer> storageSubtaskId){
        super(taskName, description, status);
        this.storageSubtaskId = storageSubtaskId;
    }

    public void setDuration(HashMap<Integer, Subtask> subtasks){
        long sumSubtaskDuration = 0;
        for(Subtask next: subtasks.values()){
            sumSubtaskDuration += next.getDuration();
        }
        this.duration = sumSubtaskDuration;
    }

    public void setStartTime(Subtask subtask){
        this.startTime = subtask.getStartTime();
    }

    public void setEndTime(Subtask subtask){
        if(this.endTime != null){
            if(this.endTime.toMinutes() < subtask.getEndTime().toMinutes()){
                this.endTime = subtask.getEndTime();
            }
        } else {
            this.endTime = subtask.getEndTime();
        }
    }

    public LocalDateTime getStartTime(){
        return this.startTime;
    }

    @Override
    public Duration getEndTime(){
        if(this.startTime == null){
            return Duration.between(LocalDateTime.now(), LocalDateTime.now().plusNanos(1));
        }
        return Duration.between(this.startTime, this.startTime.plusMinutes(this.duration));
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
        } else if (getStorageSubtaskId() == null){
            Epic updatedEpic = new Epic(getTaskName(), getDescription(), TaskStatus.NEW, getStorageSubtaskId());
            return updatedEpic;
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
            } else {
                Epic updatedEpic = new Epic(getTaskName(), getDescription(), TaskStatus.IN_PROGRESS, getStorageSubtaskId());
                return updatedEpic;
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

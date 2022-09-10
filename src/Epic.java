import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task{
    ArrayList<Integer> storageSubtaskId = new ArrayList<>();

    Epic(String taskName, String description){
        super(taskName, description);
    }

    void addSubtask(Integer idSubtask){
        this.storageSubtaskId.add(idSubtask);
    }

    void removeSubtask(){
        this.storageSubtaskId.clear();
        this.status = "NEW";
    }

    void changeStatus(HashMap<Integer, Subtask> storageSubtask){
        int size = storageSubtaskId.size();
        int countDoneStatus = 0;
        int countProgressStatus = 0;

        for(int i = 0; i < storageSubtaskId.size(); i++){
            for(Integer next: storageSubtask.keySet()){
                if(storageSubtaskId.get(i).equals(next)){
                    if(storageSubtask.get(next).getStatus().equals("DONE")){
                        countDoneStatus++;
                    } else if(storageSubtask.get(next).getStatus().equals("IN_PROGRESS")){
                        countProgressStatus++;
                    }
                }
            }
        }

        if(size == countDoneStatus){
            this.status = "DONE";
        } else if(size > 0 && countProgressStatus > 0 && size != countDoneStatus){
            this.status = "IN_PROGRESS";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Epic otherBook = (Epic) obj;
        return Objects.equals(taskName, otherBook.taskName) &&
                Objects.equals(description, otherBook.description) &&
                Objects.equals(status, otherBook.status) &&
                (identifier == otherBook.identifier);
    }

}

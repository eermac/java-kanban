package taskPackage;

import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String taskName, String description){
        super(taskName, description);
    }

    public Subtask(String taskName, String description, TaskStatus status){
        super(taskName, description, status);
    }

    public void setEpicId(int id){
        this.epicId = id;
    }

    public int getEpicId(){
        return this.epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subtask otherBook = (Subtask) obj;
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

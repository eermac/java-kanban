package main.taskPackage;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String taskName, String description){
        super(taskName, description);
    }

    public Subtask(String taskName, String description, TaskStatus status){
        super(taskName, description, status);
    }

    public Subtask(String taskName, String description, TaskStatus status, long duration, LocalDateTime startTime){
        super(taskName, description, status, duration, startTime);
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

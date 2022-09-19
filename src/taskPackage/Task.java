package taskPackage;

import java.util.Objects;

public class Task {
    private int Id;
    private String taskName;
    private String description;
    private final TaskStatus status;

    public Task(String taskName, String description){
        this.taskName = taskName;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public Task(String taskName, String description, TaskStatus status){
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    public int getId(){
        return this.Id;
    }
    public void setId(int Id){
        this.Id = Id;
    }

    public String getTaskName(){
        return this.taskName;
    }

    public void setTaskName(String taskName){
        this.taskName = taskName;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public TaskStatus getStatus(){
        return this.status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task otherBook = (Task) obj;
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

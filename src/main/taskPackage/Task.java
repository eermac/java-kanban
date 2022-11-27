package main.taskPackage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int Id;
    private String taskName;
    private String description;
    private final TaskStatus status;
    private long duration;
    private LocalDateTime startTime;

    public Task(String taskName, String description){
        this.taskName = taskName;
        this.description = description;
        this.status = TaskStatus.NEW;
    }

    public Task(){
        this.status = TaskStatus.NEW;
    }

    public Task(String taskName, String description, TaskStatus status){
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    public Task(String taskName, String description, TaskStatus status, long duration, LocalDateTime startTime){
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public long getDuration(){
        return this.duration;
    }

    public LocalDateTime getStartTime(){
        return this.startTime;
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

    public Duration getEndTime() {
        return Duration.between(this.startTime, this.startTime.plusMinutes(this.duration));
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

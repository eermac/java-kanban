public class Task {
    String taskName;
    String description;
    int identifier;
    String status;

    Task(String taskName, String description){
        this.taskName = taskName;
        this.description = description;
    }

    void setId(int identifier){
        this.identifier = identifier;
    }

    void setStatus(String status){
        this.status = status;
    }

    int getId(){
        return this.identifier;
    }

    String getStatus(){
        return this.status;
    }

    @Override
    public String toString(){
        return "******\nНазвание задачи - " + this.taskName +
                    "\nОписание - " + this.description +
                        "\nИдентификатор - " + this.identifier +
                            "\nСтатус - " + this.status + "\n";
    }

}

package taskManager;

import taskPackage.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> taskHistory = new ArrayList<>();
    public Node<Task> head;
    public Node<Task> tail;
    private int size = 0;


    public void linkLast(Task task){
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(null, task, oldTail);
        head = newNode;

        if (oldTail == null)
            head = newNode;
        else
            oldTail.prev = newNode;
        size++;
    }

    public void getTasks(){
         Node<Task> pop = head;
            while(pop.next != null){
                taskHistory.add(pop.data);
                pop = pop.next;
            }
    }

    public void removeNode(Node node){
        final Node<Task> qoq = node;
        qoq.prev = qoq.next;
        qoq.next = qoq.prev;
        node.prev = null;
        node.next = null;
    }

    public void add(Task task){
        linkLast(task);
    }

    public void remove(int id){

    }

    public List<Task> getHistory() {
        return taskHistory;
    }

    @Override
    public String toString(){
        return "******\nСписок просмотренных задач - " + getHistory();
    }
}

class Node <Task> {
    public Task data;
    public Node<Task> next;
    public Node<Task> prev;

    public Node(Node<Task> prev, Task data, Node<Task> next){
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
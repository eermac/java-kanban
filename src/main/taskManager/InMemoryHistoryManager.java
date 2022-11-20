package main.taskManager;

import main.taskPackage.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

    public void linkLast(Task task) {
        if(null != task){
            if(size == 0){
                head = new Node<>(null, task, null);
                tail = head;
                size++;
            } else if(size == 1){
                tail = new Node<>(head, task, null);
                head.next = tail;
                size++;
            } else {
                final Node<Task> oldTail = tail;
                final Node<Task> newTail = new Node<>(oldTail, task, null);

                tail = newTail;
                oldTail.next = tail;
                size++;
            }
        }
    }

    public ArrayList<Task> getTasks(){
        ArrayList<Task> test2 = new ArrayList<>();

        if(head == null){
            return test2;
        } else {
            Node<Task> testHead = head;

            if (testHead.next == null) {
                test2.add(testHead.data);
                return test2;
            }

            while (true) {
                test2.add(testHead.data);
                testHead = testHead.next;

                if (testHead.next == null) {
                    test2.add(testHead.data);
                    break;
                }
            }
            return test2;
        }
    }

    public void removeNode(Node node) {
        if(null != node){
            if (node.prev == null) {
                head = node.next;
                head.prev = null;
            } else if (node.next == null){
                tail = node.prev;
                tail.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                node.next = null;
                node.prev = null;
            }

            size--;
        }
    }

    public Node<Task> getTail() {
        return tail;
    }

    public void remove(int id){

    }

    public void add(Task task){
    }

    public List<Task> getHistory() {
        return null;
    }

    @Override
    public String toString(){
        return "******\nСписок просмотренных задач - " + getHistory();
    }

    public class Node<Task> {
        private Task data;
        private Node<Task> next;
        private Node<Task> prev;

        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
package Managers;

import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private HashMap<Integer, Node> mapHistory = new HashMap<>();
    private List<Task> listHistory = new ArrayList<>();
    private static Integer historySize = 0;
    private Node<Task> tail;
    private Node<Task> head;


    public void add(Task task) {            // метод добавления задачи в историю просмотра
        if (mapHistory.containsKey(task.getTaskId())) {
            remove(task.getTaskId());
        }
            Node<Task> node = new Node<>(null, task, null);
            linkLast(node);
            mapHistory.put(task.getTaskId(), node);
        }

    public List<Task> getHistory() {        // метод получения истории просмотра
        if (listHistory.size() == 0) {
            System.out.println("Не было просмотрено ни одной задачи");
        } else {
            getTasks();
            return listHistory;
        }
        return listHistory;         // решить проблему
    }

    public void remove(int id) {  //метод удаления задачи из истории просмотра
        Node.removeNode(mapHistory.get(id));
        mapHistory.remove(id);
    }

    public void getTasks() { // перенос задач из узлов в список
        Node<Task> startElement = tail;
        int size = historySize;
        while (size > 0){         //проверка на 0!!!!
            listHistory.add(startElement.data);
            startElement = startElement.prev;
        }
    }

    public void linkLast(Node<Task> element) { // добавление узла в "конец"
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, element.data, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
            historySize++;
        }
    }


    private static class Node<Task> {

        public Task data;
        public Node<Task> next;
        public Node<Task> prev;

        private Node(Node<Task> prev, Task data, Node<Task> next) {
            this.next = next;
            this.data = data;
            this.prev = prev;
        }

        static private void removeNode(Node node) { // метод удаления узла

        }
    }
}
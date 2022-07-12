package Managers;

import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private HashMap<Integer, Node> mapHistory = new HashMap<>();
    private List<Task> listHistory = new ArrayList<>();
    private static Integer historySize = 0;
    static private Node tail;
    static private Node head;


    public void add(Task task) {                                // метод добавления задачи в историю просмотра
        if (mapHistory.containsKey(task.getTaskId())) {
            remove(task.getTaskId());
        }
        Node node = linkLast(new Node(null, task, null));
        //    linkLast(node);
        mapHistory.put(task.getTaskId(), node);
    }

    public List<Task> getHistory() {                                // метод получения истории просмотра
        if (mapHistory.size() == 0) {
            return null;
        } else {
            getTasks();
            return listHistory;
        }
    }

    public void remove(int id) {                             //метод удаления задачи из истории просмотра
        if (mapHistory.containsKey(id)) {
            Node.removeNode(mapHistory.get(id));
            historySize--;
            mapHistory.remove(id);
        } else {
            return;
        }
    }

    public void getTasks() {                                     // метод перенос задач из узлов в список
        listHistory.clear();
        Node<Task> startElement = tail;
        int size = historySize;
        while (size > 0) {
            listHistory.add(startElement.data);
            startElement = startElement.prev;
            size--;
        }
    }

    public Node linkLast(Node element) {                         // метод добавление узла в "конец"
        final Node oldTail = tail;
        final Node newNode = new Node(oldTail, element.data, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        historySize++;
        return newNode;
    }


    static class Node<Task> {

        public Task data;
        public Node<Task> next;
        public Node<Task> prev;

        Node(Node<Task> prev, Task data, Node<Task> next) {
            this.next = next;
            this.data = data;
            this.prev = prev;
        }

        static void removeNode(Node element) {                          // метод удаления узла
            if ((element.prev == null) && (element.next == null)) {
                element = null;
            } else if (element.prev == null) {
                Node nextEl = element.next;
                nextEl.prev = null;
                head = element.next;
            } else if (element.next == null) {
                Node prevEl = element.prev;
                prevEl.next = null;
                tail = element.prev;
            } else {
                Node prevEl = element.prev;
                Node nextEl = element.next;
                prevEl.next = nextEl;
                nextEl.prev = prevEl;
            }
        }
    }
}
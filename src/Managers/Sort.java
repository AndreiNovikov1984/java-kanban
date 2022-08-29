package Managers;

import Tasks.SubTask;
import Tasks.Task;

import java.util.*;

public class Sort {
    static Comparator<Task> taskComparator = new Comparator<>() {
        @Override
        public int compare(Task task1, Task task2) {
            if (task1.getTaskStartTime() == null) {
                return 5;
            }
            if (task2.getTaskStartTime() == null) {
                return -5;
            }
            if (task1.getTaskStartTime().isAfter(task2.getTaskStartTime())) {
                return 5;
            } else if (task1.getTaskStartTime().equals(task2.getTaskStartTime())) {
                return 0;
            } else {
                return -5;
            }
        }
    };

    private static final Set<Task> sortedTaskTree = new TreeSet<>(taskComparator);

    protected static void sort(Map<Integer, Task> listTask, Map<Integer, SubTask> listSubTask) {
        sortedTaskTree.clear();
        sortedTaskTree.addAll(listTask.values());
        sortedTaskTree.addAll(listSubTask.values());
    }

    public static Set<Task> getSortedTaskTree() {
        return sortedTaskTree;
    }
}

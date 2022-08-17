package Managers;

import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager historyManager;
    InMemoryTaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        taskManager = new InMemoryTaskManager();
        taskManager.createTask(new Task(0, "1", "Таск", "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "2", "Эпик"));
        taskManager.createTask(new SubTask(0, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2));
    }

    @AfterEach
    private void afterEach() {
        try {
            for (Task task : historyManager.getHistory()) {
                historyManager.remove(task.getTaskId());
            }
        } catch (NullPointerException exep) {
        }
    }

    @Test
    public void addTask() {
        final Task testTask = new Task(1, "1", "Таск", "13.08.2022, 10:00", 20);
        historyManager.add(taskManager.getTaskByNumber(1));
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        assertEquals(history.size(), 1, "История пустая.");
    }

    @Test
    public void getHistoryWIthClearHistory() {
        final List<Task> history = historyManager.getHistory();
        assertNull(history, "История не пустая.");
    }

    @Test
    public void getHistoryWIthDoubleHistory() {
        historyManager.add(taskManager.getTaskByNumber(3));
        historyManager.add(taskManager.getTaskByNumber(2));
        historyManager.add(taskManager.getTaskByNumber(1));
        final List<Task> testHistory = historyManager.getHistory();
        taskManager.getTaskByNumber(1);
        final List<Task> savedHistory = historyManager.getHistory();
        assertEquals(savedHistory, testHistory);
    }

    @Test
    public void RemoveFromHistoryOnStart() {
        final List<Task> testHistory = new ArrayList<>();
        testHistory.add(taskManager.getTaskByNumber(1));
        testHistory.add(taskManager.getTaskByNumber(2));
        historyManager.add(taskManager.getTaskByNumber(3));
        historyManager.add(taskManager.getTaskByNumber(2));
        historyManager.add(taskManager.getTaskByNumber(1));
        historyManager.remove(3);
        List<Task> savedHistory = historyManager.getHistory();
        assertEquals(savedHistory, testHistory);
    }

    @Test
    public void RemoveFromHistoryOnMiddle() {
        final List<Task> testHistory = new ArrayList<>();
        testHistory.add(taskManager.getTaskByNumber(1));
        testHistory.add(taskManager.getTaskByNumber(3));
        historyManager.add(taskManager.getTaskByNumber(3));
        historyManager.add(taskManager.getTaskByNumber(2));
        historyManager.add(taskManager.getTaskByNumber(1));
        historyManager.remove(2);
        List<Task> savedHistory = historyManager.getHistory();
        assertEquals(savedHistory, testHistory);
    }

    @Test
    public void RemoveFromHistoryOnFinish() {
        final List<Task> testHistory = new ArrayList<>();
        testHistory.add(taskManager.getTaskByNumber(2));
        testHistory.add(taskManager.getTaskByNumber(3));
        historyManager.add(taskManager.getTaskByNumber(3));
        historyManager.add(taskManager.getTaskByNumber(2));
        historyManager.add(taskManager.getTaskByNumber(1));
        historyManager.remove(1);
        List<Task> savedHistory = historyManager.getHistory();
        assertEquals(savedHistory, testHistory);
    }

}
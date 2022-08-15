package Managers;

import Tasks.EpicTask;
import Tasks.StatusTask;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract public class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    @BeforeEach
    public void beforeEach(){
        taskManager.createTask(new Task(0, "1", "Таск", "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "2", "Эпик"));
        taskManager.createTask(new SubTask(0, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2));
        }


    @Test
    public void getlistTaskWithNormalTaskList() {
        Assertions.assertEquals(taskManager.getlistTask().size(), 1);
    }

    @Test
    public void getlistEpicTaskWithNormalTaskList() {
        Assertions.assertEquals(taskManager.getlistEpicTask().size(), 1);
    }

    @Test
    public void getlistSubTaskWithNormalTaskList() {
        Assertions.assertEquals(taskManager.getlistSubTask().size(), 1);
    }

    @Test
    public void clearTaskListWithNormalTaskList() {
        taskManager.clearTaskList();
        Assertions.assertEquals(taskManager.getlistTask().size(), 0);
    }

    @Test
    public void clearEpicTaskListWithNormalEpicTaskList() {
        taskManager.clearEpicTaskList();
        Assertions.assertEquals(taskManager.getlistEpicTask().size(), 0);
    }

    @Test
    public void clearEpicTaskListWithNormalEpicTaskListWithEmptySubTask() {
        taskManager.getlistSubTask().clear();
        taskManager.clearEpicTaskList();
        Assertions.assertEquals(taskManager.getlistEpicTask().size(), 0);
    }

    @Test
    public void clearSubTaskListWithNormalSubTaskList() {
        taskManager.clearSubTaskList();
        Assertions.assertEquals(taskManager.getlistSubTask().size(), 0);
    }

    @Test
    public void clearSubTaskListWithClearEpicTaskList() {
        taskManager.clearEpicTaskList();
        Assertions.assertEquals(taskManager.getlistSubTask().size(), 0);
    }
    @Test
    public void getTaskByNumberWithTaskNumber(){
        Task task = new Task(1, "1", "Таск", "13.08.2022, 10:00", 20);
        final Task savedTask = taskManager.getTaskByNumber(1);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
    }

    @Test
    public void getEpicTaskByNumberWithTaskNumber(){
        Task task = new EpicTask(2, "2", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        final Task savedTask = taskManager.getTaskByNumber(2);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
    }

    @Test
    public void getSubTaskByNumberWithTaskNumber(){
        Task task = new SubTask(3, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2);
        final Task savedTask = taskManager.getTaskByNumber(3);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
    }

    @Test
    public abstract void checkEpicStatusWithEmptySubTaskList();

    @Test
    public abstract void checkEpicStatusWithAllNewSubTaskList();

    @Test
    public abstract void checkEpicStatusWithAllDoneSubTaskList();
}
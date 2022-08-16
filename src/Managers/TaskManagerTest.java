package Managers;

import Tasks.EpicTask;
import Tasks.StatusTask;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract public class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager.createTask(new Task(0, "1", "Таск", "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "2", "Эпик"));
        taskManager.createTask(new SubTask(0, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2));
    }


    @Test
    public void getlistTaskWithNormalTaskList() {
        final Task testTask = new Task(1, "1", "Таск", "13.08.2022, 10:00", 20);
        Map<Integer, Task> testListTask = taskManager.getlistTask();
        assertNotNull(testListTask, "Задачи на возвращаются.");
        assertEquals(1, testListTask.size(), "Неверное количество задач.");
        assertEquals(testTask, testListTask.get(1), "Задачи не совпадают.");
    }

    @Test
    public void getlistEpicTaskWithNormalTaskList() {
        final EpicTask testEpic = new EpicTask(2, "2", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        Map<Integer, EpicTask> testListEpic = taskManager.getlistEpicTask();
        assertNotNull(testListEpic, "Задачи на возвращаются.");
        assertEquals(1, testListEpic.size(), "Неверное количество задач.");
        assertEquals(testEpic, testListEpic.get(2), "Задачи не совпадают.");
    }

    @Test
    public void getlistSubTaskWithNormalTaskList() {
        final SubTask testSub = new SubTask(3, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2);
        Map<Integer, SubTask> testListSub = taskManager.getlistSubTask();
        assertNotNull(testListSub, "Задачи на возвращаются.");
        assertEquals(1, testListSub.size(), "Неверное количество задач.");
        assertEquals(testSub, testListSub.get(3), "Задачи не совпадают.");
    }

    @Test
    public void clearTaskListWithNormalTaskList() {
        taskManager.clearTaskList();
        assertEquals(taskManager.getlistTask().size(), 0);
    }

    @Test
    public void clearEpicTaskListWithNormalEpicTaskList() {
        taskManager.clearEpicTaskList();
        assertEquals(taskManager.getlistEpicTask().size(), 0);
    }

    @Test
    public void clearEpicTaskListWithNormalEpicTaskListWithEmptySubTask() {
        taskManager.getlistSubTask().clear();
        taskManager.clearEpicTaskList();
        assertEquals(taskManager.getlistEpicTask().size(), 0);
    }

    @Test
    public void clearSubTaskListWithNormalSubTaskList() {
        taskManager.clearSubTaskList();
        assertEquals(taskManager.getlistSubTask().size(), 0);
    }

    @Test
    public void clearSubTaskListWithClearEpicTaskList() {
        taskManager.clearEpicTaskList();
        assertEquals(taskManager.getlistSubTask().size(), 0);
    }

    @Test
    public void getTaskByNumberWithTaskNumber() {
        final Task testTask = new Task(1, "1", "Таск", "13.08.2022, 10:00", 20);
        final Task savedTask = taskManager.getTaskByNumber(1);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testTask, savedTask, "Задачи не совпадают.");
    }

    @Test
    public void getTaskByNumberWithoutTaskNumber() {
        final Task testTask = new Task(1, "1", "Таск", "13.08.2022, 10:00", 20);
        final Task savedTask = taskManager.getTaskByNumber(5);
        assertNull(savedTask, "Задача не найдена.");
    }

    @Test
    public void getEpicTaskByNumberWithTaskNumber() {
        final Task testEpic = new EpicTask(2, "2", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        final Task savedEpic = taskManager.getTaskByNumber(2);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    public void getSubTaskByNumberWithTaskNumber() {
        final Task testSub = new SubTask(3, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2);
        final Task savedSub = taskManager.getTaskByNumber(3);
        assertNotNull(savedSub, "Задача не найдена.");
        assertEquals(testSub, savedSub, "Задачи не совпадают.");
    }

    @Test
    public void createTask() {
        final Task testTask = new Task(4, "1", "Таск", "13.08.2022, 10:00", 20);
        taskManager.createTask(new Task(0, "1", "Таск", "13.08.2022, 10:00", 20));
        final Task savedTask = taskManager.getlistTask().get(4);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testTask, savedTask, "Задачи не совпадают.");
    }

    @Test
    public void createEpic() {
        final EpicTask testEpic = new EpicTask(4, "2", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        taskManager.createTask(new EpicTask(0, "2", StatusTask.NEW, "Эпик",
                "2022-08-13T14:00", "120"));
        final EpicTask savedEpic = taskManager.getlistEpicTask().get(4);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    public void createSub() {
        final SubTask testSub = new SubTask(4, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2);
        taskManager.createTask(new SubTask(0, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2));
        final SubTask savedSub = taskManager.getlistSubTask().get(4);
        assertNotNull(savedSub, "Задача не найдена.");
        assertEquals(testSub, savedSub, "Задачи не совпадают.");
    }

    @Test
    public void refreshTask() {
        final Task testTask = new Task(1, "1_Update", "Таск", "13.08.2022, 10:00", 20);
        Task task = new Task(1, "1_Update", "Таск", "13.08.2022, 10:00", 20);
        taskManager.refreshTask(task);
        final Task savedTask = taskManager.getlistTask().get(1);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testTask, savedTask, "Задачи не совпадают.");
    }

    @Test
    public void refreshEpic() {
        EpicTask testEpic = new EpicTask(2, "2_Update", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        EpicTask epic = new EpicTask(2, "2_Update", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        testEpic.setSubTaskIdentificator(epic.getSubTaskIdentificator());
        taskManager.refreshTask(epic);
        final EpicTask savedEpic = taskManager.getlistEpicTask().get(2);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    public void refreshEpicWithoutSub() {
        EpicTask testEpic = new EpicTask(2, "2_Update", "Эпик");
        EpicTask epic = new EpicTask(2, "2_Update", "Эпик");
        taskManager.clearTaskByNumber(3);
        taskManager.refreshTask(epic);
        final EpicTask savedEpic = taskManager.getlistEpicTask().get(2);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");
    }


    @Test
    public void refreshSub() {
        final SubTask testSub = new SubTask(3, "3_Update", "Сабтаск", "13.08.2022, 14:00",
                120, 2);
        SubTask sub = new SubTask(3, "3_Update", "Сабтаск", "13.08.2022, 14:00",
                120, 2);
        taskManager.refreshTask(sub);
        final SubTask savedSub = taskManager.getlistSubTask().get(3);
        assertNotNull(savedSub, "Задача не найдена.");
        assertEquals(testSub, savedSub, "Задачи не совпадают.");
    }

    @Test
    public void refreshSubWithoutEpic() {
        final SubTask testSub = new SubTask(3, "3_Update", "Сабтаск", "13.08.2022, 14:00",
                120, 2);
        SubTask sub = new SubTask(3, "3_Update", "Сабтаск", "13.08.2022, 14:00",
                120, 2);
        taskManager.clearTaskByNumber(2);
        taskManager.refreshTask(sub);
        final SubTask savedSub = taskManager.getlistSubTask().get(3);
        assertNotNull(savedSub, "Задача не найдена.");
        assertEquals(testSub, savedSub, "Задачи не совпадают.");
    }

    @Test
    public void clearTaskByNumber() {
        taskManager.clearTaskByNumber(1);
        assertNull(taskManager.getlistTask().get(1));
        assertEquals(taskManager.getlistTask().size(), 0);
    }

    @Test
    public void clearTaskByNumberWithoutTask() {
        taskManager.clearTaskList();
        taskManager.clearTaskByNumber(1);
        assertNull(taskManager.getlistTask().get(1));
        assertEquals(taskManager.getlistTask().size(), 0);
    }

    @Test
    public void clearTaskByNumberWithIncorrectNumber() {
        taskManager.clearTaskByNumber(5);
        assertNotNull(taskManager.getlistTask());
        assertEquals(taskManager.getlistTask().size(), 1);
    }

    @Test
    public void clearEpicByNumber() {
        taskManager.clearTaskByNumber(2);
        assertNull(taskManager.getlistEpicTask().get(2));
        assertEquals(taskManager.getlistEpicTask().size(), 0);
    }

    @Test
    public void clearEpicByNumberWithoutTask() {
        taskManager.clearEpicTaskList();
        taskManager.clearTaskByNumber(1);
        assertNull(taskManager.getlistEpicTask().get(1));
        assertEquals(taskManager.getlistEpicTask().size(), 0);
    }

    @Test
    public void clearSubByNumber() {
        taskManager.clearTaskByNumber(3);
        assertNull(taskManager.getlistSubTask().get(3));
        assertEquals(taskManager.getlistSubTask().size(), 0);
    }

    @Test
    public void clearSubByNumberWithoutTask() {
        taskManager.clearSubTaskList();
        taskManager.clearTaskByNumber(1);
        assertNull(taskManager.getlistSubTask().get(1));
        assertEquals(taskManager.getlistSubTask().size(), 0);
    }

    @Test
    public void getSubTaskByEpicNumber() {
        final ArrayList<Integer> testSubList = new ArrayList<>();
        testSubList.add(3);
        final ArrayList<Integer> saveSubList = taskManager.getSubTaskByEpicNumber(2);
        assertEquals(saveSubList, testSubList);
    }

    @Test
    public void getSubTaskByEpicNumberWithoutSub() {
        final ArrayList<Integer> testSubList = new ArrayList<>();
        taskManager.clearSubTaskList();
        taskManager.refreshTask(taskManager.getlistEpicTask().get(2));
        final ArrayList<Integer> saveSubList = taskManager.getSubTaskByEpicNumber(2);
        assertEquals(saveSubList, testSubList);
    }

    @Test
    public void getSubTaskByEpicNumberWithIncorrectNumber() {
        final ArrayList<Integer> testSubList = null;
        final ArrayList<Integer> saveSubList = taskManager.getSubTaskByEpicNumber(5);
        assertEquals(saveSubList, testSubList);
    }

    @Test
    public void checkEpicStatusWithSubAllNEW() {
        final EpicTask testEpic = new EpicTask(2, "2_Update", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        EpicTask epic = new EpicTask(2, "2_Update", StatusTask.IN_PROGRESS, "Эпик", "2022-08-13T14:00",
                "120");
        taskManager.createTask(new SubTask(4, "4", "Сабтаск", "13.08.2022, 14:00",
                120, 2));
        taskManager.refreshTask(epic);
        final EpicTask savedEpic = taskManager.getlistEpicTask().get(2);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    public void checkEpicStatusWithSubAllDONE() {
        final EpicTask testEpic = new EpicTask(2, "2_Update", StatusTask.DONE, "Эпик", "2022-08-13T14:00",
                "120");
        EpicTask epic = new EpicTask(2, "2_Update", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        taskManager.getlistSubTask().get(3).setTaskStatus(StatusTask.DONE);
        taskManager.createTask(new SubTask(4, "4", StatusTask.DONE, "Сабтаск", "2022-08-13T14:00",
                "120", 2));
        taskManager.refreshTask(epic);
        final EpicTask savedEpic = taskManager.getlistEpicTask().get(2);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    public void checkEpicStatusWithSubNEWAndDONE() {
        final EpicTask testEpic = new EpicTask(2, "2_Update", StatusTask.IN_PROGRESS, "Эпик", "2022-08-13T14:00",
                "120");
        EpicTask epic = new EpicTask(2, "2_Update", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        taskManager.createTask(new SubTask(4, "4", StatusTask.DONE, "Сабтаск", "2022-08-13T14:00",
                "120", 2));
        taskManager.refreshTask(epic);
        final EpicTask savedEpic = taskManager.getlistEpicTask().get(2);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");
    }

    @Test
    public void checkEpicStatusWithSubAllIN_PROGRESS() {
        final EpicTask testEpic = new EpicTask(2, "2_Update", StatusTask.IN_PROGRESS, "Эпик", "2022-08-13T14:00",
                "120");
        EpicTask epic = new EpicTask(2, "2_Update", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        taskManager.getlistSubTask().get(3).setTaskStatus(StatusTask.IN_PROGRESS);
        taskManager.createTask(new SubTask(4, "4", StatusTask.IN_PROGRESS, "Сабтаск", "2022-08-13T14:00",
                "120", 2));
        taskManager.refreshTask(epic);
        final EpicTask savedEpic = taskManager.getlistEpicTask().get(2);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");
    }


    @Test
    public abstract void checkEpicStatusWithEmptySubTaskList();

    @Test
    public abstract void checkEpicStatusWithAllNewSubTaskList();

    @Test
    public abstract void checkEpicStatusWithAllDoneSubTaskList();
}
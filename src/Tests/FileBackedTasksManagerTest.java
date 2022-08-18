package Tests;

import Managers.FileBackedTasksManager;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @Override
    @BeforeEach
    public void beforeEach() {
        File backUpFile = new File("testfile.csv");
        taskManager = new FileBackedTasksManager(backUpFile);
        taskManager.createTask(new Task(0, "1", "Таск", "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "2", "Эпик"));
        taskManager.createTask(new SubTask(0, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2));
    }

    @Test
    public void saveInFileStandart() {
        File savedbackUpFile = new File("testFile.csv");
        taskManager.save();
        assertNotNull(savedbackUpFile, "Файл не создается");
    }

    @Test
    public void saveInFileClearTask() {
        File savedbackUpFile = new File("testFile.csv");
        taskManager.clearTaskList();
        taskManager.save();
        assertNotNull(savedbackUpFile, "Файл не создается");
    }

    @Test
    public void saveInFileClearEpicTask() {
        File savedbackUpFile = new File("testFile.csv");
        taskManager.clearEpicTaskList();
        taskManager.save();
        assertNotNull(savedbackUpFile, "Файл не создается");
    }

    @Test
    public void saveInFileClearSubTask() {
        File savedbackUpFile = new File("testFile.csv");
        taskManager.clearSubTaskList();
        taskManager.save();
        assertNotNull(savedbackUpFile, "Файл не создается");
    }

    @Test
    public void getPrioritizedTasks() {
        final Set<Task> setTask = taskManager.getPrioritizedTasks();
        int k = 0;
        for (Task task : setTask) {
            assertEquals(task, taskManager.getTaskByNumber((1 + k) % 4));
            k = k + 2;
        }
    }

    @Test
    public void validateTaskTime() {
        List<Integer> testValid = new ArrayList<>();
        testValid.add(1);
        testValid.add(3);
        taskManager.createTask(new Task(0, "4", "Таск", "13.08.2022, 10:10", 300));
        List<Integer> valid = taskManager.validateTaskTime(taskManager.getTaskByNumber(4));
        assertEquals(valid, testValid);
    }

    @Test
    public void loadfromFile() {
        File backUpFile = new File("testfile.csv");
        taskManager.getTaskByNumber(1);
        FileBackedTasksManager taskManager1 = FileBackedTasksManager.loadFromFile(backUpFile);
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getTaskByNumber(1), taskManager.getTaskByNumber(1));
        assertEquals(taskManager1.getTaskByNumber(2), taskManager.getTaskByNumber(2));
        assertEquals(taskManager1.getTaskByNumber(3), taskManager.getTaskByNumber(3));
    }

    @Test
    public void loadfromFileEmptyHistory() {
        File backUpFile = new File("testfile.csv");
        FileBackedTasksManager taskManager1 = FileBackedTasksManager.loadFromFile(backUpFile);
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getTaskByNumber(1), taskManager.getTaskByNumber(1));
        assertEquals(taskManager1.getTaskByNumber(2), taskManager.getTaskByNumber(2));
        assertEquals(taskManager1.getTaskByNumber(3), taskManager.getTaskByNumber(3));
    }

    @Test
    public void loadfromFileEmptyTask() {
        File backUpFile = new File("testfile.csv");
        taskManager.clearTaskList();
        taskManager.clearEpicTaskList();
        FileBackedTasksManager taskManager1 = FileBackedTasksManager.loadFromFile(backUpFile);
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getlistTask(), taskManager.getlistTask());
        assertEquals(taskManager1.getlistEpicTask(), taskManager.getlistEpicTask());
        assertEquals(taskManager1.getlistSubTask(), taskManager.getlistSubTask());
    }

    @Test
    public void loadfromFileEmptySub() {
        File backUpFile = new File("testfile.csv");
        taskManager.clearSubTaskList();
        FileBackedTasksManager taskManager1 = FileBackedTasksManager.loadFromFile(backUpFile);
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getlistTask(), taskManager.getlistTask());
        assertEquals(taskManager1.getlistEpicTask(), taskManager.getlistEpicTask());
        assertEquals(taskManager1.getlistSubTask(), taskManager.getlistSubTask());
    }
}
package Tests;

import Managers.FileBackedTasksManager;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @Override
    @BeforeEach
    public void beforeEach() throws IOException {
        Path backUpFile = Paths.get("testfile.csv");
        taskManager = new FileBackedTasksManager();
        taskManager.setBackUpFile(backUpFile);
        taskManager.createTask(new Task(0, "1", "Таск", "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "2", "Эпик"));
        taskManager.createTask(new SubTask(0, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2));
    }

    @Test
    public void saveStandart() {
        Path savedbackUpFile = Paths.get("testFile.csv");
        taskManager.save();
        assertNotNull(savedbackUpFile, "Файл не создается");
    }

    @Test
    public void saveWithClearTask() {
        Path savedbackUpFile = Paths.get("testFile.csv");
        taskManager.clearTaskList();
        taskManager.save();
        assertNotNull(savedbackUpFile, "Файл не создается");
    }

    @Test
    public void saveWithClearEpicTask() {
        Path savedbackUpFile = Paths.get("testFile.csv");
        taskManager.clearEpicTaskList();
        taskManager.save();
        assertNotNull(savedbackUpFile, "Файл не создается");
    }

    @Test
    public void saveWithClearSubTask() {
        Path savedbackUpFile = Paths.get("testFile.csv");
        taskManager.clearSubTaskList();
        taskManager.save();
        assertNotNull(savedbackUpFile, "Файл не создается");
    }

    @Test
    public void load() {
        Path backUpFile = Paths.get("testfile.csv");
        taskManager.getTaskByNumber(1);
        FileBackedTasksManager taskManager1 = FileBackedTasksManager.loadFromFile(backUpFile);
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getTaskByNumber(1), taskManager.getTaskByNumber(1));
        assertEquals(taskManager1.getTaskByNumber(2), taskManager.getTaskByNumber(2));
        assertEquals(taskManager1.getTaskByNumber(3), taskManager.getTaskByNumber(3));
    }

    @Test
    public void loadWithEmptyHistory() {
        Path backUpFile = Paths.get("testfile.csv");
        FileBackedTasksManager taskManager1 = FileBackedTasksManager.loadFromFile(backUpFile);
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getTaskByNumber(1), taskManager.getTaskByNumber(1));
        assertEquals(taskManager1.getTaskByNumber(2), taskManager.getTaskByNumber(2));
        assertEquals(taskManager1.getTaskByNumber(3), taskManager.getTaskByNumber(3));
    }

    @Test
    public void loadWithEmptyTask() {
        Path backUpFile = Paths.get("testfile.csv");
        taskManager.clearTaskList();
        taskManager.clearEpicTaskList();
        FileBackedTasksManager taskManager1 = FileBackedTasksManager.loadFromFile(backUpFile);
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getlistTask(), taskManager.getlistTask());
        assertEquals(taskManager1.getlistEpicTask(), taskManager.getlistEpicTask());
        assertEquals(taskManager1.getlistSubTask(), taskManager.getlistSubTask());
    }

    @Test
    public void loadWithEmptySub() {
        Path backUpFile = Paths.get("testfile.csv");
        taskManager.clearSubTaskList();
        FileBackedTasksManager taskManager1 = FileBackedTasksManager.loadFromFile(backUpFile);
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getlistTask(), taskManager.getlistTask());
        assertEquals(taskManager1.getlistEpicTask(), taskManager.getlistEpicTask());
        assertEquals(taskManager1.getlistSubTask(), taskManager.getlistSubTask());
    }
}
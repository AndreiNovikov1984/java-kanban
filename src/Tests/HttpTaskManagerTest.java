package Tests;

import Managers.HttpTaskManager;
import Server.KVServer;
import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class HttpTaskManagerTest extends FileBackedTasksManagerTest {
    private KVServer serverKV;
    private String savedbackUp;

    @Override
    @BeforeEach
    public void beforeEach() throws IOException {
        serverKV = new KVServer();
        serverKV.start();
        taskManager = new HttpTaskManager("http://localhost:", "Andrei");
        taskManager.createTask(new Task(0, "1", "Таск", "13.08.2022, 10:00", 20));
        taskManager.createTask(new EpicTask(0, "2", "Эпик"));
        taskManager.createTask(new SubTask(0, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2));
    }

    @AfterEach
    public void afterEach() {
        serverKV.stop();
    }

    @Override
    @Test
    public void saveStandart() {
        savedbackUp = "id,type,name,status,description,taskStartTime, taskDuration, epic\n" +
                "1,TASK,1,NEW,Таск,2022-08-13T10:00,20\n" +
                "2,EPICTASK,2,NEW,Эпик,2022-08-13T14:00,120\n" +
                "3,SUBTASK,3,NEW,Сабтаск,2022-08-13T14:00,120,2\n\n";
        String backUpOnline = taskManager.save();
        assertNotNull(backUpOnline, "Данные не сохраняются");
        assertEquals(savedbackUp, backUpOnline);
    }

    @Override
    @Test
    public void saveWithClearTask() {
        savedbackUp = "id,type,name,status,description,taskStartTime, taskDuration, epic\n" +
                "2,EPICTASK,2,NEW,Эпик,2022-08-13T14:00,120\n" +
                "3,SUBTASK,3,NEW,Сабтаск,2022-08-13T14:00,120,2\n\n";
        taskManager.clearTaskList();
        String backUpOnline = taskManager.save();
        assertNotNull(backUpOnline, "Данные не сохраняются");
        assertEquals(savedbackUp, backUpOnline);

    }

    @Override
    @Test
    public void saveWithClearEpicTask() {
        savedbackUp = "id,type,name,status,description,taskStartTime, taskDuration, epic\n" +
                "1,TASK,1,NEW,Таск,2022-08-13T10:00,20" + "\n\n";
        taskManager.clearEpicTaskList();
        String backUpOnline = taskManager.save();
        assertNotNull(backUpOnline, "Данные не сохраняются");
        assertEquals(savedbackUp, backUpOnline);
    }

    @Override
    @Test
    public void saveWithClearSubTask() {
        savedbackUp = "id,type,name,status,description,taskStartTime, taskDuration, epic\n" +
                "1,TASK,1,NEW,Таск,2022-08-13T10:00,20\n" +
                "2,EPICTASK,2,NEW,Эпик,2022-08-13T14:00,120\n\n";
        taskManager.clearSubTaskList();
        String backUpOnline = taskManager.save();
        assertNotNull(backUpOnline, "Данные не сохраняются");
        assertEquals(savedbackUp, backUpOnline);
    }

    @Override
    @Test
    public void load() {
        taskManager.getTaskByNumber(1);
        HttpTaskManager taskManager1 = HttpTaskManager.loadOnline("Andrei");
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getTaskByNumber(1), taskManager.getTaskByNumber(1));
        assertEquals(taskManager1.getTaskByNumber(2), taskManager.getTaskByNumber(2));
        assertEquals(taskManager1.getTaskByNumber(3), taskManager.getTaskByNumber(3));
    }

    @Override
    @Test
    public void loadWithEmptyHistory() {
        HttpTaskManager taskManager1 = HttpTaskManager.loadOnline("Andrei");
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertNull(taskManager1.getHistory(), "История не пустая");
        assertEquals(taskManager1.getTaskByNumber(1), taskManager.getTaskByNumber(1));
        assertEquals(taskManager1.getTaskByNumber(2), taskManager.getTaskByNumber(2));
        assertEquals(taskManager1.getTaskByNumber(3), taskManager.getTaskByNumber(3));
    }

    @Override
    @Test
    public void loadWithEmptyTask() {
        taskManager.clearTaskList();
        taskManager.clearEpicTaskList();
        HttpTaskManager taskManager1 = HttpTaskManager.loadOnline("Andrei");
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getlistTask(), taskManager.getlistTask());
        assertEquals(taskManager1.getlistEpicTask(), taskManager.getlistEpicTask());
        assertEquals(taskManager1.getlistSubTask(), taskManager.getlistSubTask());
    }

    @Override
    @Test
    public void loadWithEmptySub() {
        taskManager.clearSubTaskList();
        HttpTaskManager taskManager1 = HttpTaskManager.loadOnline("Andrei");
        assertEquals(taskManager1.getHistory(), taskManager.getHistory());
        assertEquals(taskManager1.getlistTask(), taskManager.getlistTask());
        assertEquals(taskManager1.getlistEpicTask(), taskManager.getlistEpicTask());
        assertEquals(taskManager1.getlistSubTask(), taskManager.getlistSubTask());
    }

}
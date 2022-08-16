package Managers;

import Tasks.EpicTask;
import Tasks.StatusTask;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;


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
        final Task testTask = new Task(1, "1", "Таск", "13.08.2022, 10:00", 20);
        final EpicTask testEpic = new EpicTask(2, "2", StatusTask.NEW, "Эпик", "2022-08-13T14:00",
                "120");
        final SubTask testSub = new SubTask(3, "3", "Сабтаск", "13.08.2022, 14:00",
                120, 2);

    }

    @Override
    @Test
    public void checkEpicStatusWithEmptySubTaskList() {
    }

    @Override
    @Test
    public void checkEpicStatusWithAllNewSubTaskList() {
    }

    @Override
    @Test
    public void checkEpicStatusWithAllDoneSubTaskList() {
    }

}
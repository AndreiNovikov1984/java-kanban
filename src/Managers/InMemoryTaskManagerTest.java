package Managers;

import Tasks.EpicTask;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

 class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{


     @Override
     @BeforeEach
     public void beforeEach(){
         taskManager = new InMemoryTaskManager();
         taskManager.createTask(new Task(0, "1", "Таск", "13.08.2022, 10:00",20));
         taskManager.createTask(new EpicTask(0, "2", "Эпик"));
         taskManager.createTask(new SubTask(0, "3", "Сабтаск", "13.08.2022, 14:00",
                 120, 2));
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
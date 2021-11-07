package todo.app.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todo.app.main.FileHandler;
import todo.app.main.ToDoList;


class FileHandlerTest {
    ToDoList toDoList = new ToDoList();
    FileHandler fileHandler = new FileHandler(toDoList);
    @BeforeEach
    void setUp() {
        toDoList.addTask("1,Testing,02-11-2021,in-progress,Tests");
    }

    @Test
    @DisplayName("Save tasks to a txt file")
    void saveToFile() {
        Assertions.assertTrue(fileHandler.saveToFile("C:\\Users\\Arbeit\\OneDrive - TBZ\\Dokumente\\GitHub\\Project-M226a\\ToDoApp\\tasks.txt"));
    }
    @Test
    @DisplayName("Save tasks to a folder directory")
    void saveToPath() {
        Assertions.assertFalse(fileHandler.saveToFile("C:\\Users\\Arbeit\\OneDrive - TBZ\\Dokumente\\GitHub\\Project-M226a\\ToDoApp"));
    }

    @Test
    @DisplayName("Read file from txt file")
    void readFromFile() {
        Assertions.assertTrue(fileHandler.readFile("C:\\Users\\Arbeit\\OneDrive - TBZ\\Dokumente\\GitHub\\Project-M226a\\ToDoApp\\tasks.txt"));
    }

    @Test
    @DisplayName("Read from a folder directory")
    void readFromPath() {
        Assertions.assertFalse(fileHandler.readFile("C:\\Users\\Arbeit\\OneDrive - TBZ\\Dokumente\\GitHub\\Project-M226a\\ToDoApp"));
    }

}
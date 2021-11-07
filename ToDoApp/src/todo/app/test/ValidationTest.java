package todo.app.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todo.app.main.ToDoList;
import todo.app.main.Validation;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    private ToDoList toDoList = new ToDoList();
    private Validation validation;
    @BeforeEach
    void setUp() {
        toDoList.addTask("1,VALIDAITION,26-10-2021,in-progress,TBZ");
        validation = new Validation(toDoList.getTasks());
    }

    @Test
    @DisplayName("Id 1 exist already")
    void isIdInUse() {
        assertFalse(validation.isIdAvailable(1));
    }
    @Test
    @DisplayName("Id 2 is available")
    void isIdAvailable() {
        assertTrue(validation.isIdAvailable(2));
    }

    @Test
    @DisplayName("A task with title, date, status and project name is complete")
    void isTaskComplete() {
        String rawTask = "1,VALIDAITION,26-10-2021,in-progress,TBZ";
        String[] parts = rawTask.split(",");
        assertTrue(validation.isTaskComplete(parts));
    }

    @Test
    @DisplayName("Task without title is incomplete")
    void isTaskIncomplete() {
        String rawTask = "1,26-10-2021,in-progress,TBZ";
        String[] parts = rawTask.split(",");
        assertFalse(validation.isTaskComplete(parts));
    }

    @Test
    @DisplayName("Testing invalid (36-02-2021) dates, that should not work and valid dates")
    void isDateValid() {
        assertTrue( validation.isDateValid("10-11-2021"));
        assertFalse(validation.isDateValid("36-02-2021"));
        assertFalse(validation.isDateValid("I guess, we can try"));
    }

    @Test
    @DisplayName("Task with Id 1 without date is invalid")
    void isEditedTaskValid() {
        String rawTask = "1,,in-progress,TBZ";
        String[] parts = rawTask.split(",");
        assertFalse(validation.isEditedTaskValid(parts));
    }

    @Test
    @DisplayName("Task.txt is a valid path")
    void isPathValid() {
        validation.isPathValid("task.txt");
    }
}
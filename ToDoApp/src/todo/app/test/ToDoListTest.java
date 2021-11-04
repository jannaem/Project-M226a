package todo.app.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todo.app.main.Task;
import todo.app.main.ToDoList;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {
    private final ToDoList toDoList = new ToDoList();
    private String expected;

    @BeforeEach
    void setUp() {
        toDoList.addTask("2,test todolist,02-11-2021,in-progress,project-226a");
    }

    @Test
    @DisplayName("Task added to the lists of tasks")
    void addTask() {
        toDoList.addTask("1,test todolist,02-11-2021,in-progress,project-226a");
        Assertions.assertEquals(2, toDoList.getTasks().size());

    }

    @Test
    @DisplayName("Task status edited to working on it")
    void editTask() {
        toDoList.editTask("2,-,-,working on it,-");
        expected = "working on it";
        Assertions.assertEquals(expected, toDoList.getTasks().get(2).getStatus());
    }

    @Test
    @DisplayName("Check if id 2 exists")
    void checkId() {
        assertTrue(toDoList.checkId(2));
    }

    @Test
    @DisplayName("Id 4 doesn't exist")
    void checkNonExistenceId() {
        assertFalse(toDoList.checkId(4));
    }

    @Test
    @DisplayName("Task 3 deleted ")
    void deleteTask() {
        toDoList.deleteTask(2);
        assertNull(toDoList.getTasks().get(3));
    }

    @Test
    @DisplayName("Change task status to done")
    void markTaskAsDone() {
        toDoList.markTaskAsDone(2);
        expected = "Done";
        Assertions.assertEquals(expected, toDoList.getTasks().get(2).getStatus());
    }

    @Test
    @DisplayName("All tasks sorted by date")
    void sortByDateTasks() {
        toDoList.addTask("3,test todolist,02-11-2021,in-progress,project-226a");
        toDoList.addTask("1,test todolist,01-11-2021,in-progress,project-226a");
        toDoList.addTask("4,test todolist,28-10-2021,in-progress,project-226a");

        toDoList.sortByDateTasks();

        String[] dateSortExpected = {"28-10-2021","01-11-2021","02-11-2021","02-11-2021"};
        String[] dateSort = new String[toDoList.getTasks().size()];

        int index = 0;
        for (Map.Entry<Integer, Task> entry : toDoList.getTasks().entrySet()) {
            Task task = entry.getValue();
            dateSort[index] = toDoList.convertDateToString(task.getDueDate());
            ++index;
        }
        assertArrayEquals(dateSortExpected, dateSort);
    }

    @Test
    @DisplayName("Invalid string of date")
    public void isDateinValid() {
        String date = "c";

        assertFalse(toDoList.isDateValid( date));


    }
    @Test
    @DisplayName("Is date a Valid string")
    public void isDateValid() {
        String date = "12-12-2021";

        assertTrue(toDoList.isDateValid( date));


    }

    @Test
    @DisplayName("Localdate into string")
    public void convertDateToString() {
        LocalDate dueDate = LocalDate.of(2021, 10, 13);


        String expected = "13-10-2021";

        Assertions.assertEquals(expected, toDoList.convertDateToString(dueDate));
    }

    @Test
    @DisplayName("Parse date input by -")
    public void parseDate() {

        LocalDate expected = LocalDate.of(2020, 10, 10);
        String userInput = "10-10-2020";

        Assertions.assertEquals(expected, toDoList.parseDate(userInput));
    }

    @Test
    @DisplayName("All tasks sorted by project")
    void sortByProjectTasks() {
        toDoList.addTask("3,test todolist,02-11-2021,in-progress,project-226a");
        toDoList.addTask("8,test todolist,01-11-2021,in-progress,testing");
        toDoList.addTask("5,test todolist,28-10-2021,in-progress,project-226a");
        toDoList.addTask("9,test todolist,01-11-2021,in-progress,testing");

        toDoList.sortByProjectTasks();

        String[] projectSortedExpected = {"project-226a","project-226a","project-226a","testing", "testing"};
        String[] projectSorted = new String[toDoList.getTasks().size()];

        int index = 0;
        for (Map.Entry<Integer, Task> entry : toDoList.getTasks().entrySet()) {
            Task task = entry.getValue();
            projectSorted[index] = task.getProjectName();
            ++index;
        }
        assertArrayEquals(projectSortedExpected, projectSorted);
    }

    @Test
    @DisplayName("ToDo List isn't empty")
    void isToDoListEmpty() {
        assertFalse(toDoList.isToDoListEmpty());
    }

    @Test
    @DisplayName("Get all tasks")
    void getTasks() {
        assertEquals(1, toDoList.getTasks().size());
    }
}
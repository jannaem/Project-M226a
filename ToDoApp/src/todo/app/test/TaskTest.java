package todo.app.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import todo.app.main.Task;
import todo.app.main.ToDoList;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task = new Task();
    ToDoList toDoList = new ToDoList();

    @BeforeEach
    void setUp() {
        task.setId(1);
        task.setTitle("Testing");
        task.setDueDate(LocalDate.of(2021,11, 2));
        task.setStatus("in-progress");
        task.setProjectName("testing");
    }

    @Test
    @DisplayName("Task builder equals task")
    void buildTask() {
        assertEquals(task.taskToString(toDoList),
                Task.buildTask("1", "Testing", LocalDate.of(2021,11, 2), "in-progress", "testing").taskToString(toDoList));
    }

    @Test
    @DisplayName("Set task to type String")
    void testToString() {
        String taskString = task.taskToString(toDoList);
        String expected = "1,Testing,02-11-2021,in-progress,testing";
        assertEquals(expected, taskString);
    }
    @Test
    @DisplayName("Get id 1")
    public void getId() {
        int expected = 1;
        assertSame(expected, task.getId());

    }
    @Test
    @DisplayName("Get title testing")
    public void getTitle() {
        String expected = "Testing";
        assertEquals(expected, task.getTitle());
    }

    @Test
    @DisplayName("Get date in localdate value")
    public void getDueDate() {

        LocalDate dueDate = LocalDate.of(2020, 12, 12);
        task.setDueDate(dueDate);

        LocalDate expected = LocalDate.of(2020, 12, 12);

        assertEquals(expected, task.getDueDate());

    }


    @Test
    @DisplayName("Get status working")
    public void getStatus() {

        task.setTitle("Working");

        String expected = "Working";
        assertEquals(expected, task.getTitle());

    }

    @Test
    @DisplayName("Get status coding")
    public void getProjectName() {

        task.setProjectName("Coding");

        String expected = "Coding";
        assertEquals(expected, task.getProjectName());

    }

    @Test
    @DisplayName("Set id and check value 1122")
    public void setId() {

        task.setId(1122);

        int expected = 1122;
        assertEquals(expected, task.getId());
    }

    @Test
    @DisplayName("Set title clear-cache and check value")
    public void setTitle() {

        task.setTitle("Clear-Cache");

        String expected = "Clear-Cache";
        assertEquals(expected, task.getTitle());

    }

    @Test
    @DisplayName("Set date and check value")
    public void setDueDate() {

        LocalDate dueDate = LocalDate.of(2021, 10, 13);

        task.setDueDate(dueDate);

        LocalDate expected = LocalDate.of(2021, 10, 13);

        assertEquals(expected, task.getDueDate());

    }

    @Test
    @DisplayName("Set status and check value")
    public void setStatus() {
        task.setStatus("in-progress");

        String expected = "in-progress";
        assertEquals(expected, task.getStatus());

    }

    @Test
    @DisplayName("Set project name and check value")
    public void setProjectName() {
        task.setProjectName("Monthly-Supply");

        String expected = "Monthly-Supply";
        assertEquals(expected, task.getProjectName());
    }
}
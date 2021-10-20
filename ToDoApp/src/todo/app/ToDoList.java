package todo.app;

import java.time.LocalDate;
import java.util.*;

public class ToDoList {
    private OutputUtils outputUtils = new OutputUtils();
    private Map<String, Task> tasks = new LinkedHashMap<>();

    /**
     * In this method reside the implementation of
     * how adding a task method is executed and will later be used
     * in the switch statement that runs the program.
     * @param rawTask the input task of the user
     */
    public void addTask(String rawTask) {

    }

    /**
     * In this  method reside the implementation of
     * of how editing a task method is executed and will later be used
     * in the switch statement that runs the program
     * <p>
     * and it will check against all components
     * to see if a change is applied or not
     *
     * @param task the task that should be updated/edited
     */
    public void editTask(String task) {

    }

    public void sortByProjectTasks() {

    }

    public void sortByDateTasks() {

    }



    public void DeleteTask(String id) {
    }

    /**
     * In this method reside the implementation of
     * how marking a task as done method is executed and will later be used
     * in the switch statement that runs the program
     * <p>
     * its main purpose is to set a task's status with a given ID to Done
     *
     * @param id is the id of the task that you want to mark as done
     */
    public void markTaskAsDone(String id) {

    }

    public boolean isToDoListEmpty() {
        return tasks.isEmpty();
    }

    public String convertDateToString(LocalDate dueDate, String s) {
        return null;
        //TODO return value
    }

    public boolean isDateValid(String s, String part) {
        return true;
        //TODO return value
    }

    public LocalDate parseDate(String s, String part) {
        return null;
        //TODO return value
    }

    public Map<String, Task> getTasks() {
        return tasks;
    }
}

package todo.app.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * This class has all methods to make
 * changes to each task from the to-do list
 *
 * @author Janna Esteban
 */

public class ToDoList {
    private final Map<Integer, Task> tasks = new LinkedHashMap<>();
    private final String DATE_PATTERN = "dd-MM-yyyy";

    /**
     * In this method the task object is added to
     * the map tasks, after the raw task is split into
     * the needed parts, which are used to create a task object.
     * @param rawTask the input task of the user
     */
    public void addTask(String rawTask) {
        String[] parts = rawTask.split(",");
        Task task = Task.buildTask(parts[0], parts[1], parseDate(parts[2]),
                parts[3], parts[4]);
        tasks.put(task.getId(), task);
    }

    /**
     * This method checks which parts need to be edited, edits parts that need
     * updating and sets the boolean isTaskEdited to true, after the rawTask
     * was split into the parts defined by the ",".
     *
     * @param rawTask the task that should be updated/edited
     * @return a true value when a change was applied
     */

    public boolean editTask(String rawTask) {
        String[] parts = rawTask.split(",");
        int id = Integer.parseInt(parts[0]);

        boolean isTaskEdited = false;
        if (!parts[1].equals("-")) {
            tasks.get(id).setTitle(parts[1]);
            isTaskEdited = true;
        }

        if (!parts[2].equals("-")) {
            LocalDate date = parseDate(parts[2]);
            if(date != null)
            tasks.get(id).setDueDate(date);
            isTaskEdited = true;
        }

        if (!parts[3].equals("-")) {
            tasks.get(id).setStatus(parts[3]);
            isTaskEdited = true;
        }
        if (!parts[4].equals("-")) {
            tasks.get(id).setProjectName(parts[4]);
            isTaskEdited = true;
        }
        return isTaskEdited;
    }


    /**
     *This method removes the wanted task.
     *
     * @param id is the id of the task that should be deleted
     */

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    /**
     * This method sets a task's status with a given ID to Done.
     *
     * @param id is the id of the task that you want to mark as done
     */
    public void markTaskAsDone(int id) {
        tasks.get(id).setStatus("Done");
    }

    /**
     * In this method resides the implementation
     * that compares our tasks and sorts them according to their date value
     * <p>
     * then our map will be cleared and sorted data will be inserted
     */
    public void sortByDateTasks() {
        List<Map.Entry<Integer, Task>> entries = new ArrayList<>(tasks.entrySet());
        entries.sort((task1, task2) -> {
            LocalDate dueDateFirstTask = task1.getValue().getDueDate();
            LocalDate dueDateSecondTask = task2.getValue().getDueDate();
            return dueDateFirstTask.compareTo(dueDateSecondTask);
        });
        tasks.clear();
        entries.forEach((entry) -> tasks.put(entry.getKey(), entry.getValue()));
    }

    /**
     * This method returns a string format of the LocalDate date
     * to be added and returned.
     *
     * @param date   due date
     * @return date as a string
     */
    public String convertDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        return date.format(formatter);
    }

    /**
     * parseDate parses a string into correct date format representation of the given date
     *
     * @param value  value that equals the component in the task format
     * @return localDate  parsed of the given String date
     */
    public LocalDate parseDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        try {
            return LocalDate.parse(value, formatter);
        }catch(DateTimeParseException e){
            return null;
        }
    }

    /**
     * In this method resides the implementation
     * that compares our tasks and sorts them according to their project name value
     * <p>
     * then our map will be cleared and sorted data will be inserted
     */
    public void sortByProjectTasks() {
        List<Map.Entry<Integer, Task>> entries = new ArrayList<>(tasks.entrySet());
        entries.sort((firstTask, secondTask) -> {
            String firstProject = firstTask.getValue().getProjectName();
            String secondProject = secondTask.getValue().getProjectName();

            return firstProject.compareTo(secondProject);
        });

        tasks.clear();
        entries.forEach(entry -> tasks.put(entry.getKey(), entry.getValue()));
    }

    /**
     * This method makes it easier and shorter
     * to find out if the to-do list is empty
     *
     * @return a true value when the list is empty
     */
    public boolean isToDoListEmpty() {
        return tasks.isEmpty();
    }

    /**
     * This method gives us access to all tasks and returns them.
     *
     * @return all tasks, that appear in the to-do list
     */
    public Map<Integer, Task> getTasks() {
        return tasks;
    }
}

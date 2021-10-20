package todo.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class has all methods to make
 * changes to each task from the to-do list
 *
 * @author Janna Esteban
 */

public class ToDoList {
    private Map<Integer, Task> tasks = new LinkedHashMap<>();

    private static int compare(Map.Entry<String, Task> task1, Map.Entry<String, Task> task2) {
        LocalDate dueDateFirstTask = task1.getValue().getDueDate();
        LocalDate dueDateSecondTask = task2.getValue().getDueDate();
        int result = dueDateFirstTask.compareTo(dueDateSecondTask);
        return result;
    }

    /**
     * In this method reside the implementation of
     * how adding a task method is executed and will later be used
     * in the switch statement that runs the program.
     *
     * @param rawTask the input task of the user
     */
    public void addTask(String rawTask) {
        String[] parts = rawTask.split(",");
        Task task = Task.buildTask(parts[0], parts[1], parseDate("dd-MM-yyyy", parts[2]),
                parts[3], parts[4]);
        tasks.put(task.getId(), task);
        //TODO output for the user, so ta the he knows it worked
    }

    /**
     * In this  method reside the implementation of
     * of how editing a task method is executed and will later be used
     * in the switch statement that runs the program
     * <p>
     * and it will check against all components
     * to see if a change is applied or not
     *
     * @param rawTask the task that should be updated/edited
     * @return a true value when a change was applied
     */

    public boolean editTask(String rawTask) {
        String[] parts = rawTask.split(",");

        boolean isTaskEdited = false;
        if (!parts[1].equals("-")) {
            tasks.get(parts[0]).setTitle(parts[1]);
            isTaskEdited = true;
        }

        if (!parts[2].equals("-")) {
            tasks.get(parts[0]).setDueDate(parseDate("dd-mm-yyyy", parts[2]));
            isTaskEdited = true;
        }

        if (!parts[3].equals("-")) {
            tasks.get(parts[0]).setStatus(parts[3]);
            isTaskEdited = true;
        }
        if (!parts[4].equals("-")) {
            tasks.get(parts[0]).setProjectName(parts[4]);
            isTaskEdited = true;
        }
        return isTaskEdited;
    }

    /**
     * This method tells us if an id exist
     *
     * @param id is the id of the task you want
     *           to prove his existence
     * @return a true value when the task exist
     */
    public boolean checkId(int id) {
        boolean hasId = false;
        if (tasks.get(id) != null) {
            hasId = true;
        }
        return hasId;
    }

    /**
     * In this  method reside the implementation of how removing
     * an existing task is done and how the method is executed and will later be used
     * in the switch statement that runs the program
     * <p>
     * its main purpose is to remove the wanted task.
     *
     * @param id is the id of the task that should be deleted
     */

    public void deleteTask(int id) {
        tasks.remove(id);
        //TODO output for the user, so ta the he knows it worked
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
    public void markTaskAsDone(int id) {
        tasks.get(id).setStatus("Done");
        //TODO output for the user, so ta the he knows it worked
    }

    /**
     * In this method resides the implementation
     * to compare our tasks and sort them according to their date value
     * <p>
     * then our map will be cleared, sorted data will be inserted
     * and a message confirming the completion of task to the user will be printed
     */
    public void sortByDateTasks() {
        List<Map.Entry<Integer, Task>> entries = new ArrayList<>(tasks.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Integer, Task>>() {
            @Override
            public int compare(Map.Entry<Integer, Task> task1, Map.Entry<Integer, Task> task2) {
                LocalDate dueDateFirstTask = task1.getValue().getDueDate();
                LocalDate dueDateSecondTask = task2.getValue().getDueDate();
                int result = dueDateFirstTask.compareTo(dueDateSecondTask);
                return result;
            }
        });
        tasks.clear();
        entries.forEach((entry) -> {
            tasks.put(entry.getKey(), entry.getValue());
        });
        //TODO output for the user, so ta the he knows it worked
    }

    /**
     * isDateValid confirms the validity of the inserted date
     * by creating Local Date and parsing it then formatting it
     * to the pattern inserted in the method parameter
     *
     * @param format correct format of the date
     * @param value  value that equals the component in the task format
     * @return result result as a boolean expression
     */
    public boolean isDateValid(String format, String value) {
        DateTimeFormatter formattings = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(value, formattings);

        String result = localDate.format(formattings);

        return result.equals(value);
    }

    /**
     * This method return a string format of the LocalDate date
     * to be added and returned to the console
     *
     * @param date   due date
     * @param format date format pattern
     * @return date as a string
     */
    public String convertDateToString(LocalDate date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String result = date.format(formatter);

        return result;
    }

    /**
     * parseDate parses a string representation of the given date
     *
     * @param format date format pattern
     * @param value  value that equals the component in the task format
     * @return localDate  parsed of the given String date
     */
    public LocalDate parseDate(String format, String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(value, formatter);
        return localDate;
    }

    /**
     * In this method resides the implementation
     * to compare our tasks and sort them according to their project name value
     * <p>
     * then our map will be cleared, sorted data will be inserted
     * and a message confirming the completion of task to the user will be printed
     */
    public void sortByProjectTasks() {
        List<Map.Entry<Integer, Task>> entries = new ArrayList<>(tasks.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Integer, Task>>() {
            @Override
            public int compare(Map.Entry<Integer, Task> firstTask, Map.Entry<Integer, Task> secondTask) {
                String firstProject = firstTask.getValue().getProjectName();
                String secondProject = secondTask.getValue().getProjectName();

                int result = firstProject.compareTo(secondProject);
                return result;
            }
        });

        tasks.clear();
        entries.forEach(entry -> {
            tasks.put(entry.getKey(), entry.getValue());
        });
        //TODO output for the user, so ta the he knows it worked
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
     * This method gives us access to the to-do
     * list
     *
     * @return tasks , which is the whole to-do list
     * whitch all taks
     */
    public Map<Integer, Task> getTasks() {
        return tasks;
    }
}

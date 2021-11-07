package todo.app.main;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class Validation {
    private Map<Integer, Task> tasks;
    private final String DATE_PATTERN = "dd-MM-yyyy";

    public Validation(Map<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * This method tells us if an id exists
     *
     * @param id is the id of the task you want to check
     * @return true when id doesn't exist
     */
    public boolean isIdAvailable(int id) {
        return tasks.get(id) == null && id != 0;
    }

    /**
     * This method checks if a task has all data needed.
     *
     * @param parts the user input split into parts of the task
     * @return true when the all parts aren't empty
     */
    public boolean isTaskComplete(String[] parts) {
        return (parts.length == 5 && !parts[1].equals("") && !parts[2].equals("") && !parts[3].equals("") && !parts[4].equals(""));
    }

    /**
     * This method confirms the validity of the inserted date
     * by creating Local Date and parsing it then formatting it
     * to the pattern - DATE_PATTERN
     *
     * @return true when the date is valid
     */
    public boolean isDateValid(String date) {
        DateTimeFormatter formattings = DateTimeFormatter.ofPattern(DATE_PATTERN);
        try {
            LocalDate localDate = LocalDate.parse(date, formattings);
            localDate.format(formattings);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * This method checks if an edited task
     * has the criteria we need to edit it:
     * Task exist -> id exist
     * Date is valid
     * All data is filled:
     * ID, Title, Due Date (format: dd-mm-yyyy), Status, Project Name
     *
     * @param parts the user input split into parts of the task
     * @return true when all criteria is met
     */
    public boolean isEditedTaskValid(String[] parts){
       if(isTaskComplete(parts)){
           boolean dateValidationRequired = !parts[2].equals("-");
           if (dateValidationRequired) {
               return !isIdAvailable(Integer.parseInt(parts[0])) && isDateValid(parts[2]);
           }
           return !isIdAvailable(Integer.parseInt(parts[0])) && isTaskComplete(parts);
       }
       return false;
    }

    /**
     * This method tells us if a path is valid.
     * @param path is the directory of the file
     * @return true when the path is valid
     */
    public  boolean isPathValid(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ex) {
            return false;
        }

        return true;
    }
}

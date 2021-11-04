package todo.app.main;

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
     * This method tells us if an id exist
     *
     * @param id is the id of the task you want
     *           to prove his existence
     * @return a true value when id doesn't exist
     */
    public boolean isIdAvailable(int id) {
        return tasks.get(id) == null && id != 0;
    }

    /**
     * This method checks if a task has data.
     *
     * @param parts all the subjects value of the tasks
     * @return true when the string task isn't empty
     */
    public boolean isTaskComplete(String[] parts) {
        return (parts.length == 5 && !parts[1].equals("") && !parts[2].equals("") && !parts[3].equals("") && !parts[4].equals(""));
    }

    /**
     *  This method confirms the validity of the inserted date
     * by creating Local Date and parsing it then formatting it
     * to the pattern inserted in the method parameter
     *
     * @return result result as a boolean expression
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
     * All data is filed:
     * ID,  Title,Due Date (format: dd-mm-yyyy),Status,Project Name
     *
     * @param parts data of the task
     * @return
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
}

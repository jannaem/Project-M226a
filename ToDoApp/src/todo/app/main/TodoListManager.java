package todo.app.main;

import java.util.Scanner;

/**
 * ToDolISTManager is the main class where all classes and
 * features are connected and implemented.
 *
 * @author Janna Esteban
 */
public class TodoListManager {
    private static final int ADD_TASK = 1;
    private static final int MARK_AS_DONE = 2;
    private static final int REMOVE_TASK = 3;
    private static final int EDIT_TASK = 4;
    private static final int DISPLAY_ALL_TASKS = 5;
    private static final int SORT_TASKS_BY_DATE = 6;
    private static final int SORT_TASKS_PROJECT = 7;
    private static final int SAVE_TASKS_TO_FILE = 8;
    private static final int READ_FROM_FILE = 9;
    private static final int EXIT = 10;
    private static final int GET_BACK_TO_MENU = 0;

    public static boolean applicationRunning = true;
    private final ToDoList toDoList = new ToDoList();
    private final Validation validation = new Validation(toDoList.getTasks());
    private final FileHandler fileHandler = new FileHandler(toDoList);
    private final IO io = new IO();
    private boolean repeat;
    private int id;


    /**
     * This method start the application by running an infinite loop
     * to make sure the app keeps running as long as we are using it
     * and using the switch statement implemented in another method
     */

    public void start() {
        io.printMsg("title");
        while (applicationRunning) {
            io.printAvailableActions();
            int actionNumber = io.readInputAction();
            processTask(actionNumber);

        }
    }

    /**
     * This method runs a switch statement which
     * uses user inputs to process different tasks
     * in different ways
     *
     * @param actionNumber the action that the user will insert
     */

    public void processTask(int actionNumber) {
        Scanner scan = new Scanner(System.in);
        repeat = true;
        String task;

        if (actionNumber > 1 && actionNumber < 9 && toDoList.isToDoListEmpty())
            io.printMsg("add task");
        else {
            switch (actionNumber) {
                case ADD_TASK -> {
                    io.printInstructionsTask("add");
                    task = getNewTask();
                    if (!task.equals("0")) {
                        toDoList.addTask(task);
                        io.printTaskStatus("task", "added");
                    }

                }
                case MARK_AS_DONE -> markTaskAsDone();
                case REMOVE_TASK -> executeRemoveTask();
                case EDIT_TASK -> {
                    io.printInstructionsTask("update");
                    task = getEditedTask();
                    if (!task.equals("0")) {
                        if (toDoList.editTask(task))
                            io.printTaskStatus("task", "updated");
                        else
                            io.printTaskStatus("no changes", "");
                    }
                }
                case DISPLAY_ALL_TASKS -> io.printTasks(toDoList);

                case SORT_TASKS_BY_DATE -> {
                    toDoList.sortByDateTasks();
                    io.printTasks(toDoList);
                }
                case SORT_TASKS_PROJECT -> {
                    toDoList.sortByProjectTasks();
                    io.printTasks(toDoList);
                }
                case SAVE_TASKS_TO_FILE -> checkPathInput("save");
                case READ_FROM_FILE -> checkPathInput("read");
                case EXIT -> {
                    io.printGoodBye();
                    applicationRunning = false;
                }
                default -> throw new IllegalStateException("Unexpected value: " + actionNumber);
            }
        }
    }

    /**
     * This method gets user input and checks
     * multiple points to ensure a successful performance
     * <p>
     * it will check against components completion
     * ID and task to be removed existence
     * and if the user correctly followed instructions
     */
    private void executeRemoveTask() {
        while (repeat) {
            io.printInstructionsTask("remove");
            id = io.readTaskId();
            if (id != 0 && validation.isIdAvailable(id)) {
                toDoList.deleteTask(id);
                io.printTaskStatus("removed", String.valueOf(id));
                repeat = false;
            } else if (id == 0) repeat = false;
        }
    }

    /**
     * In this method will check against components completion
     * ID existence
     * validity of date
     * and if the user correctly followed instructions
     *
     * @return userInput user's inserted information
     */
    private String getNewTask() {
        while (true) {
            String userInput = io.readInputTask();
            String[] parts = userInput.split(",");
            if (!userInput.equals("0") && validation.isTaskComplete(parts)) {
                if (parts[0].equals("") || Integer.parseInt(parts[0]) == GET_BACK_TO_MENU) io.printErrorMsg("invalid id");
                else if (!validation.isIdAvailable(Integer.parseInt(parts[0]))) io.printErrorMsg("id exist");
                else if (!validation.isDateValid(parts[2])) io.printErrorMsg("invalid date");
                else return userInput;
            } else if (userInput.equals("0")) return userInput;
            io.printErrorMsg("follow instruction / return");
        }
    }

    /**
     * In this method will get the user input,
     * that will be validated with the class Validate
     * and ask for input again if it returns false.
     *
     * @return userInput user's inserted information
     */
    private String getEditedTask() {
        while (true) {
            String userInput = io.readInputTask();
            if (!userInput.equals("0")) {
                String[] parts = userInput.split(",");
                if (validation.isEditedTaskValid(parts))
                    return userInput;
            } else return userInput;
            io.printErrorMsg("follow instruction / return");
        }
    }

    /**
     * This method checks if a task
     *
     * @param parts all the subjects value of the tasks
     * @return true when the string task, was correctly
     */
    private boolean isRawTaskValid(String[] parts) {
        if (parts.length == 5) {
            boolean dateValidationRequired = !parts[2].equals("-") && !toDoList.isDateValid(parts[2]);
            if (dateValidationRequired) {
                if (toDoList.getTasks().get(Integer.parseInt(parts[0])) != null) return true;
                else io.printErrorMsg("id dont exist");
            } else return true;
        } else io.printErrorMsg("follow instruction / return");
        return false;
    }

    /**
     * In this method  will check against components completion
     * ID and task existence and as soon the user enter a valid id,
     * it will give it to the next method so, that the task
     * can be marked as done.
     *
     * <p>
     * Checks user input if id is valid
     */
    private void markTaskAsDone() {
        io.printInstructionsTask("mark as done");
        while (repeat) {
            id = io.readTaskId();
            if (validation.isIdAvailable(id)) {
                repeat = false;
                if (!toDoList.getTasks().get(id).getStatus().equals("Done")) {
                    toDoList.markTaskAsDone(id);
                    io.printTaskStatus("done", String.valueOf(id));
                } else {
                    io.printTaskStatus("status", String.valueOf(id));
                }
            } else if (id == 0) repeat = false;
            else io.printErrorMsg("id dont exist");
        }
    }


    /**
     * In this method a given path is taken from the user
     * which we get from IO. We check if the path is valid
     * and inform the user if it worked otherwise the path
     * wasn't valid, and he has to try again or enter 0.
     *
     * @param typeOfExecution it tells us if you want to read or save the tasks
     */
    private void checkPathInput(String typeOfExecution) {
        while (repeat) {
            String path = io.readPathInput();
            if (!path.equals("0") && executePath(typeOfExecution, path)) {
                io.printTaskStatus(typeOfExecution, path);
                repeat = false;
            } else if (path.equals("0")) repeat = false;
            else io.printErrorMsg("path/file not found");
        }
    }

    /**
     * We read or save task to or from a file it depends on the type of execution.
     * After that we will get a return value, which tells us if it could
     * save/read all tasks.
     *
     * @param typeOfExecution it tells us if you want to read or save the tasks
     * @param path            is the input from user, which tells us where the file is
     * @return true value when the tasks could be safe/read.
     */
    private boolean executePath(String typeOfExecution, String path) {
        switch (typeOfExecution) {
            case "read" -> {
                return (fileHandler.readFile(path));
            }
            case "save" -> {
                return (fileHandler.saveToFile(path));
            }
        }
        return false;
    }
}
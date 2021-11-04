package todo.app.main;

import java.util.Scanner;

/**
 * ToDolISTManager is the class which connects all
 * classes and features.
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
    private static final String GET_BACK_TO_MENU = "0";

    public static boolean applicationRunning = true;
    private final ToDoList toDoList = new ToDoList();
    private final Validation validation = new Validation(toDoList.getTasks());
    private final FileHandler fileHandler = new FileHandler(toDoList);
    private final IO io = new IO();
    private boolean repeat;
    private int id;


    /**
     * This method starts the application by running an infinite loop
     * to make sure, that the program keeps running as long as we are using it.
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
     * uses the user input to either edit, create,
     * delete, mark a task as done, sort by date/project
     * and show all tasks or save/read tasks to/from a file.
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
                    task = checkNewTask();
                    if (!task.equals(GET_BACK_TO_MENU)) {
                        toDoList.addTask(task);
                        io.printTaskStatus("task", "added");
                    }
                }
                case MARK_AS_DONE -> markTaskAsDone();
                case REMOVE_TASK -> removeTask();
                case EDIT_TASK -> {
                    io.printInstructionsTask("update");
                    task = checkEditedTask();
                    if (!task.equals(GET_BACK_TO_MENU)) {
                        if (toDoList.editTask(task)) io.printTaskStatus("task", "updated");
                        else io.printTaskStatus("no changes", "");
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
                case SAVE_TASKS_TO_FILE -> saveOrReadTaskFromFile("save");
                case READ_FROM_FILE -> saveOrReadTaskFromFile("read");
                case EXIT -> {
                    io.printGoodBye();
                    applicationRunning = false;
                }
                default -> throw new IllegalStateException("Unexpected value: " + actionNumber);
            }
        }
    }

    /**
     * This method will check for the existence for
     * this ID. If the task with this ID exists,
     * it will be deleted.
     */
    private void removeTask() {
        while (repeat) {
            io.printInstructionsTask("remove");
            id = io.readTaskId();
            if (validation.isIdAvailable(id)) {
                toDoList.deleteTask(id);
                io.printTaskStatus("removed", String.valueOf(id));
                repeat = false;
            } else if (id == 0) repeat = false;
        }
    }

    /**
     * This method will check
     * for if all task information is given,
     * if ID exists already, if it has a valid date
     * and if the user correctly followed instructions.
     *
     * @return userInput user's inserted information
     */
    private String checkNewTask() {
        while (true) {
            String userInput = io.readInputTask();
            String[] parts = userInput.split(",");
            if (!userInput.equals("0") && validation.isTaskComplete(parts)) {
                if (parts[0].equals("") || parts[0].equals(GET_BACK_TO_MENU)) io.printErrorMsg("invalid id");
                else if (!validation.isIdAvailable(Integer.parseInt(parts[0]))) io.printErrorMsg("id exist");
                else if (!validation.isDateValid(parts[2])) io.printErrorMsg("invalid date");
                else return userInput;
            } else if (userInput.equals("0")) return userInput;
            io.printErrorMsg("follow instruction / return");
        }
    }

    /**
     * This method will get the user input,
     * that will be validated with the class Validate
     * and ask for input until the validation returns true.
     *
     * @return userInput user's inserted information
     */
    private String checkEditedTask() {
        while (true) {
            String userInput = io.readInputTask();
            if (!userInput.equals(GET_BACK_TO_MENU)) {
                String[] parts = userInput.split(",");
                if (validation.isEditedTaskValid(parts)) return userInput;
            } else return userInput;
            io.printErrorMsg("follow instruction / return");
        }
    }

    /**
     * This method  will check if ID exists, if the
     * task with the ID exists, it checks if its already
     * marked as done if not it will be marked. The user will be
     * informed if the ID doesn't exist or the task has been marked
     * or is already marked.
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
     * which we get from the Class IO. We check if the path is valid
     * and inform the user if it worked. As long as the path is invalid,
     * the user has to enter a new path or can return to the menu.
     * If the path is valid, the tasks will be read or save
     * from/to a file it depends on the type of execution.
     *
     * @param typeOfExecution it tells us if you want to read or save the tasks
     */
    private void saveOrReadTaskFromFile(String typeOfExecution) {
        while (repeat) {
            String path = io.readPathInput();
            if (!path.equals(GET_BACK_TO_MENU) && validation.isPathValid(path)) {
                switch (typeOfExecution) {
                    case "read" -> fileHandler.readFile(path);
                    case "save" -> fileHandler.saveToFile(path);
                }
                io.printTaskStatus(typeOfExecution, path);
                repeat = false;
            } else if (path.equals("0")) repeat = false;
            else io.printErrorMsg("path/file not found");
        }
    }

}
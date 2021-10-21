package todo.app;

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

    public static boolean applicationRunning = true;
    private final ToDoList toDoList = new ToDoList();
    private final FileHandler fileHandler = new FileHandler(toDoList);
    private final IO io = new IO();
    private boolean repeat;
    private int id;
    private String path;
    private String task;


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

        if (actionNumber > 1 && actionNumber < 9 && toDoList.isToDoListEmpty())
            io.printMsg("add task");
        else {
            repeat = true;
            switch (actionNumber) {
                case ADD_TASK -> {
                    io.printInstructionsTask("add");
                    task = checkTask("add");
                    if(!task.equals("0")){
                        toDoList.addTask(task);
                        io.printTaskStatus("task", "added");
                    }

                }
                case MARK_AS_DONE -> executeMarkTaskAsDone();
                case REMOVE_TASK -> executeRemoveTask();
                case EDIT_TASK -> {
                    io.printInstructionsTask("update");
                    task = checkTask("edit");
                    if (!task.equals("0")) {
                        if(toDoList.editTask(task))
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
                case SAVE_TASKS_TO_FILE -> executeTaskFromFile("save");
                case READ_FROM_FILE -> executeTaskFromFile("read");
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
            if (id != 0) {
                if (toDoList.checkId(id)) {
                    toDoList.deleteTask(id);
                    io.printTaskStatus("removed", String.valueOf(id));
                    repeat = false;
                }
            }else{
                repeat = false;
            }
        }
    }


    /**
     * In this method reside the implementation of
     * how this program read user's input by using a scanner inside a while loop
     * and then checking multiple points to ensure a successful performance
     * <p>
     * it will check against components completion
     * ID existence
     * validity of date
     * and if the user correctly followed instructions
     *
     * @return userInput user's inserted information
     */
    private String checkTask(String typeOfCheck) {
        while (true) {
            String userInput = io.readInputTask();
            if (!userInput.equals("0")) {
                String[] parts = userInput.split(",");
                if (typeOfCheck.equals("edit")) {
                    if (parts.length == 5) {
                        boolean dateValidationRequired = !parts[2].equals("-");
                        if (dateValidationRequired && !toDoList.isDateValid(parts[2])) {
                            if (toDoList.getTasks().get(Integer.parseInt(parts[0])) != null) {
                                return userInput;
                            } else {
                                io.printErrorMsg("id dont exist");
                            }
                        } else {
                            return userInput;
                        }
                    } else {
                        io.printErrorMsg("follow instruction / return");
                    }
                } else {
                    if (parts.length == 5 && !parts[1].equals("") && !parts[2].equals("") && !parts[3].equals("") && !parts[4].equals("")) {
                        if (toDoList.isDateValid(parts[2])) {
                            if(parts[0].equals("") || parts[0].equals("0")){
                                io.printErrorMsg("invalid id");
                            }else{
                                if (toDoList.getTasks().get(Integer.parseInt(parts[0])) == null) {
                                    return userInput;
                                } else {
                                    io.printErrorMsg("id exist");
                                }
                            }
                        } else {
                            io.printErrorMsg("invalid date");
                        }
                    } else {
                        io.printErrorMsg("follow instruction");
                    }
                }
            } else {
                return userInput;
            }
        }
    }


    /**
     * In this method  will check against components completion
     * ID and task existence and as soon the user a valid id,
     * it will give it to the next method so, that the task
     * can be marked as done.
     *
     * <p>
     * Checks user input if id is valid
     */
    private void executeMarkTaskAsDone() {
        io.printInstructionsTask("mark as done");
        while (repeat) {
            id = io.readTaskId();
            if (id != 0) {
                if (toDoList.checkId(id)) {
                    repeat = false;
                    if(!toDoList.getTasks().get(id).getStatus().equals("Done")){
                        toDoList.markTaskAsDone(id);
                        io.printTaskStatus("done", String.valueOf(id));
                    }else{
                        io.printTaskStatus("status", String.valueOf(id));
                        repeat = false;
                    }
                } else {
                    io.printErrorMsg("id dont exist");
                }
            }else repeat = false;
        }
    }


    /**
     * In this method a given path is taken from the user
     * which we get from IO. We start executing the readFile
     * and inform the user if it worked otherwise the path
     * wasn't valid, and he has to try again or enter 0.
     */
    private void executeTaskFromFile(String typeOfExecution) {
        while (repeat) {
            path = io.readPathInput();
            if (!path.equals("0")) {
                if (typeOfExecution.equals("read")) {
                    if (fileHandler.readFile(path)) {
                        repeat = false;
                        io.printTaskStatus("reading files", path);
                    }
                } else {
                    if (fileHandler.saveToFile(path)) {
                        repeat = false;
                        io.printTaskStatus("task saved", path);
                    }
                }
                if (repeat) {
                    io.printErrorMsg("path/file not found");
                }
            }else repeat = false;
        }
    }
}
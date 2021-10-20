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

    /**
     * linkedHashMap was used to maintain insertion order
     * applicationRunning is used as an indicator and a boolean expression
     * in the loops and switch statements below
     */
    public static boolean applicationRunning = true;
    private ToDoList toDoList = new ToDoList();
    private OutputUtils output = new OutputUtils();
    private FileHandler fileHandler = new FileHandler(toDoList);
    private IO io = new IO();
    private boolean repeat = true;
    private int id;
    private String path;
    private String task;


    /**
     * This method start the application by running an infinite loop
     * to make sure the app keeps running as long as we are using it
     * and using the switch statement implemented in another method
     */

    public void start() {
        output.printMsg("title");
        while (applicationRunning) {
            output.printAvailableActions();
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
            output.printMsg("add task");
        else {
            switch (actionNumber) {
                case ADD_TASK ->{
                    io.printInstructionsTask("add");
                    task = executeAddTask();
                    toDoList.addTask(task);
                }
                case MARK_AS_DONE -> executeMarkTaskAsDone();
                case REMOVE_TASK -> executeRemoveTask();
                case EDIT_TASK -> executeEditTask();
                case DISPLAY_ALL_TASKS ->{
                    output.printMsg("all tasks");
                    output.printTasks(toDoList);
                }
                case SORT_TASKS_BY_DATE ->{
                    toDoList.sortByDateTasks();
                    output.printMsg("all tasks");
                    output.printTasks(toDoList);
                }
                case SORT_TASKS_PROJECT ->{
                    toDoList.sortByProjectTasks();
                    output.printTasks(toDoList);
                    output.printMsg("all tasks");
                }
                case SAVE_TASKS_TO_FILE -> saveTaskToFile();
                case READ_FROM_FILE -> readTaskFromFile();
                case EXIT ->{
                    output.printGoodBye();
                    applicationRunning = false;
                }
            }
        }
    }

    /**
     *
     * @return
     */
    private String executeAddTask() {
        while (true) {
            String userInput = io.readInputTask();
            if (!userInput.equals("0")) {
                String[] parts = userInput.split(",");
                if (parts.length == 5) {
                    if (toDoList.isDateValid(parts[2])) {
                        if (toDoList.getTasks().get(Integer.parseInt(parts[0])) == null) {
                            return userInput;
                        } else {
                            output.printErrorMsg("id exist");
                        }
                    } else {
                        output.printErrorMsg("invalid date");
                    }
                } else {
                    output.printErrorMsg("follow instruction");
                }
            } else {
                return userInput;
            }
        }
    }

    /**
     *
     */
    private void executeRemoveTask(){
        while (repeat) {
            io.printInstructionsTask("remove");
            id = io.readTaskId();
            if (id != 0) {
                if (toDoList.checkId(id)) {
                    toDoList.deleteTask(id);
                    output.printTaskStatus("removed", String.valueOf(id));
                    repeat = false;
                }
            }
        }
    }

    /**
     *
     */
    private void executeEditTask(){
        io.printInstructionsTask("update");
        task = checkEdit();
        if (!task.equals("0")) {
            toDoList.editTask(task);
            output.printTaskStatus("task", "updated");
        }
    }

    /**
     *
     */
    private String checkEdit() {
        while (true) {
            String userInput = io.readInputTask();
            if (!userInput.equals(0)) {
                String[] parts = userInput.split(",");
                if (parts.length == 5) {
                    boolean dateValidationRequired = true;
                    if (parts[2].equals("-")) {
                        dateValidationRequired = false;
                    }

                    boolean isDateValid = true;
                    if (dateValidationRequired) {
                        isDateValid = toDoList.isDateValid(parts[2]);
                    }

                    if (isDateValid) {
                        if (toDoList.getTasks().get(Integer.parseInt(parts[0])) != null) {
                            return userInput;
                        } else {
                            output.printErrorMsg("id dont exist");
                        }
                    } else {
                        output.printErrorMsg("follow instruction / return");
                    }
                } else {
                    return userInput;
                }
            }
        }
    }

    /**
     *
     */
    private void executeMarkTaskAsDone() {
        while (repeat) {
            io.printInstructionsTask("mark as done");
            id = io.readTaskId();
            if (id != 0) {
                if (toDoList.checkId(id)) {
                    repeat = false;
                    toDoList.markTaskAsDone(id);
                    output.printTaskStatus("done");
                } else {
                    output.printErrorMsg("id dont exist");
                }
            }
        }
    }

    /**
     *
     */
    private void saveTaskToFile() {
        while (repeat) {
            io.printInstructionsTask("save/read file");
            path = io.readPathInput();
            if (!path.equals("0")) {
                if (fileHandler.saveToFile(path)) {
                    repeat = false;
                    output.printTaskStatus("task saved", path);
                } else {
                    output.printErrorMsg("path/file not found");
                }
            }
        }
    }

    /**
     *
     */
    private void readTaskFromFile() {
        while (repeat) {
            io.printInstructionsTask("save/read file");
            path = io.readPathInput();
            if (!path.equals("0")) {
                if (fileHandler.readFile(path)) {
                    repeat = false;
                    output.printTaskStatus("reading files");
                } else {
                    output.printErrorMsg("path/file not found");
                }
            }
        }
    }
}
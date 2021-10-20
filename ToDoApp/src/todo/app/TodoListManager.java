package todo.app;

/**
 * ToDolISTManager is the main entity where all classes and
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
    private FileHandler fileHandler = new FileHandler();
    private IO input = new IO();


    /**
     * This method start the application by running an infinite loop
     * to make sure the app keeps running as long as we are using it
     * and using the switch statement implemented in another method
     */

    public void start() {
        output.printMsg("title");
        while (applicationRunning) {
            output.printAvailableActions();
            int actionNumber = input.readInputAction();
            executeAction(actionNumber);

        }
    }

    /**
     * This method runs a switch statement in where it instantiate objects
     * made of classes that I created as features of the application
     * for it to be used to in the start() method
     *
     * @param actionNumber the action that the user will insert
     */

    public void executeAction(int actionNumber) {
        int id;
        String path;
        String task;
        boolean repeat = true;

        if (actionNumber > 1 && actionNumber < 9 && toDoList.isToDoListEmpty())
            output.printMsg("add task");

        else {
            switch (actionNumber) {
                case ADD_TASK:
                    output.printInstructionsTask("add");
                    task = executeAdd();

                    toDoList.addTask(task);
                    break;
                case MARK_AS_DONE:
                    while (repeat) {
                        output.printInstructionsTask("mark as done");
                        id = input.readTaskId();
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
                    break;
                case REMOVE_TASK:
                    while (repeat) {
                        output.printInstructionsTask("remove");
                        id = input.readTaskId();
                        if (id != 0) {
                            if (toDoList.checkId(id)) {
                                toDoList.deleteTask(id);
                                output.printTaskStatus("removed", String.valueOf(id));
                                repeat = false;
                            }
                        }
                    }
                    break;
                case EDIT_TASK:
                    output.printInstructionsTask("update");
                    task = executeEdit();
                    if (!task.equals("0")) {
                        toDoList.editTask(task);
                        output.printTaskStatus("task", "updated");
                    }

                    break;
                case DISPLAY_ALL_TASKS:
                    output.printMsg("all tasks");
                    output.printTasks(toDoList);
                    break;
                case SORT_TASKS_BY_DATE:
                    toDoList.sortByDateTasks();
                    output.printMsg("all tasks");
                    output.printTasks(toDoList);
                    break;
                case SORT_TASKS_PROJECT:
                    toDoList.sortByProjectTasks();
                    output.printTasks(toDoList);
                    output.printMsg("all tasks");
                    break;
                case SAVE_TASKS_TO_FILE:
                    while (repeat) {
                        output.printInstructionsTask("save/read file");
                        path = input.readPathInput();
                        if (!path.equals("0")) {
                            if (fileHandler.saveToFile(path)) {
                                repeat = false;
                                output.printTaskStatus("task saved");
                            } else {
                                output.printErrorMsg("path/file not found");
                            }
                        }
                    }
                    break;
                case READ_FROM_FILE:
                    output.printInstructionsTask("save/read file");
                    path = input.readPathInput();
                    if (!path.equals("0"))
                        fileHandler.readFile(path);
                    break;
                case EXIT:
                    output.printGoodBye();
                    applicationRunning = false;
                    break;

            }
        }
    }

    private String executeAdd() {
        while (true) {
            String userInput = input.readInputTask();
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

    private String executeEdit() {
        while (true) {
            String userInput = input.readInputTask();
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
}
package todo.app;

/**
 * ToDolISTManager is the main entity where all classes and
 * features are connected and implemented.
 *
 * @author Janna Esteban & Remo Aeberli
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
        while (applicationRunning) {

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
        String id;
        String path;
        String task;

        if (actionNumber > 1 && actionNumber < 9 && toDoList.isToDoListEmpty())
            //TODO output message

        else {
            switch (actionNumber) {
                case ADD_TASK:

                    break;
                case MARK_AS_DONE:


                    break;
                case REMOVE_TASK:

                    break;
                case EDIT_TASK:

                    break;
                case DISPLAY_ALL_TASKS:

                    break;
                case SORT_TASKS_BY_DATE:

                    break;
                case SORT_TASKS_PROJECT:

                    break;
                case SAVE_TASKS_TO_FILE:

                    break;
                case READ_FROM_FILE:

                    break;
                case EXIT:

                    break;

            }
        }
    }

}
package todo.app;

/**
 * This class holds methods to save the tasks
 * inserted by the user to a local existing
 * file and to read the tasks from a
 * existing file.
 *
 * @author Janna Esteban Mena
 */

public class FileHandler {

    /**
     * This method gets a defined path which is taken from the user.
     * It adds the tasks from the file and saves it in todolist with
     * the before defined tasks.
     * @param path path of the local file to be read by the program
     * @param toDoList so that you can get add all readed task to the
     *                 to-do list
     */
    public void readFiles(String path, ToDoList toDoList) {
    }

    /**
     * In this method reside the implementation of
     * how a given path is taken from the user
     * logged into to save information of tasks inserted by the user
     * <p>
     * it used a PrintWriter and print out a message to confirm success or failure
     *
     * @param path path to the local files for tasks to be saved in
     */
    public void saveToFile(String path) {
    }
}

package todo.app.main;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class holds methods to save the tasks
 * inserted by the user to a local existing
 * file and to read the tasks from an
 * existing file.
 *
 * @author Janna Esteban Mena
 */

public class FileHandler {
    private ToDoList toDoList;

    public FileHandler(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    /**
     * This method gets a defined path which is taken from the user.
     * It adds the tasks from the file and saves it in todolist with
     * the before defined tasks.
     *
     * @param path path of the local file to be read by the program
     * @return true if file was succesfully readed
     */
    public boolean readFile(String path) {
        boolean isReaded;
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                String file = scanner.nextLine();
                String[] parts = file.split(",");
                Task task = Task.buildTask(parts[0], parts[1], toDoList.parseDate(parts[2]),
                        parts[3], parts[4]);
                if (toDoList.getTasks().get(Integer.valueOf(parts[0])) != null) {
                    toDoList.getTasks().replace(Integer.valueOf(parts[0]), task);
                } else {
                    toDoList.getTasks().put(Integer.valueOf(parts[0]), task);
                }

            }
            scanner.close();
            isReaded = true;
        } catch (FileNotFoundException e) {
            isReaded = false;
        }
        return isReaded;
    }

    /**
     * In this method reside the implementation of
     * how a given path is taken from the user
     * logged into to save information of tasks inserted by the user
     * <p>
     * it used a PrintWriter and print out a message to confirm success or failure
     *
     * @param path path to the local files for tasks to be saved in
     * @return true when all task could be saved
     */
    public boolean saveToFile(String path) {
        boolean isSaved;
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(path));

            List<String> lines = toDoList.getTasks().values().stream().map(task -> task.taskToString(toDoList)).collect(Collectors.toList());

            lines.forEach(pw::println);
            pw.close();
            isSaved = true;
        } catch (FileNotFoundException e) {
            isSaved = false;
        }

        return isSaved;
    }
}

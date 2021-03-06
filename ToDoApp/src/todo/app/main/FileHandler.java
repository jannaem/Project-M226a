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
    private final ToDoList toDoList;

    public FileHandler(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    /**
     * This method gets a defined path which is taken from the user.
     * It adds the tasks from the file and saves them in the todolist (map tasks) with
     * the before defined tasks.
     *
     * @param path path of the local file to be read by the program
     * @return true if file was successfully read
     */
    public boolean readFile(String path) {
        boolean isReaded;
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
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
     * This method saves each task on a new line in the defined
     * file.
     *
     * @param path directory of the file
     * @return true when all task could be saved
     */
    public boolean saveToFile(String path) {
        boolean isSaved;
        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            List<String> lines = toDoList.getTasks().values().stream().map(task -> task.taskToString(toDoList)).collect(Collectors.toList());

            lines.forEach(pw::println);
            pw.close();
            isSaved = true;
        } catch (IOException e ) {
            isSaved = false;
        }

        return isSaved;
    }
}

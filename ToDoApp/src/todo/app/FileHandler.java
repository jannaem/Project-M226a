package todo.app;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class holds methods to save the tasks
 * inserted by the user to a local existing
 * file and to read the tasks from a
 * existing file.
 *
 * @author Janna Esteban Mena
 */

public class FileHandler {
    ToDoList toDoList = new ToDoList();

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
                Task task = Task.buildTask(parts[0], parts[1], toDoList.parseDate("dd-MM-yyyy", parts[2]),
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
        //TODO output for the user, so ta the he knows it worked
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
        BufferedWriter bw = null;
        boolean isSaved;
        try {
            FileWriter fw = new FileWriter(path);
            List<String> lines = toDoList.getTasks().entrySet().stream()
                    .map(entry -> entry.getValue().toString())
                    .collect(Collectors.toList());

            bw = new BufferedWriter(fw);
            for (String line : lines) {
                System.out.println(line);
                bw.write(line);
            }
            bw.close();
            isSaved = true;
            System.out.println("saving");
        } catch (IOException e) {
            isSaved = false;
        }

        return isSaved;
    }
}

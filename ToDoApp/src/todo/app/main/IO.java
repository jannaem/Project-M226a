package todo.app.main;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * IO gives all the output for the commandline and
 * also gets all the input from the user.
 *
 * @author Janna Esteban
 */

public class IO {
    private  final String RESET = "\u001B[0m";
    private  final String RED = "\u001B[31m";
    private  final String GREEN = "\u001B[32m";
    private  final String BLUE = "\u001B[34m";
    private final Scanner scan = new Scanner(System.in);

    /**
     * This method will display all the actions/features
     * that the user might need.
     * <p>
     * Will be used to print all valid actions
     */
    public void printAvailableActions(){
        System.out.println(BLUE+"\n╭────────────────────────╮     ╭────────────────────────╮     ╭────────────────────────╮");
        System.out.println("│1. Add a task           │     │2. Mark task as done    │     │3. Remove task          │");
        System.out.println("╰────────────────────────╯     ╰────────────────────────╯     ╰────────────────────────╯");
        System.out.println("╭────────────────────────╮     ╭────────────────────────╮     ╭────────────────────────╮");
        System.out.println("│4. Edit task            │     │5. Display all tasks    │     │6. Sort tasks by date   │");
        System.out.println("╰────────────────────────╯     ╰────────────────────────╯     ╰────────────────────────╯");
        System.out.println("╭────────────────────────╮     ╭────────────────────────╮     ╭────────────────────────╮");
        System.out.println("│7. sort tasks by project│     │8. save tasks to file   │     │9. read from file       │");
        System.out.println("╰────────────────────────╯     ╰────────────────────────╯     ╰────────────────────────╯");
        System.out.println("╭────────────────────────╮");
        System.out.println("│10. Exit                │");
        System.out.println("╰────────────────────────╯"+RESET);
    }

    /**
     * This method will allow the user to choose an action
     * from the list, checks if it's a valid input and returns the input,
     * when it's valid.
     * @return userInput to the start() method, for it to be used
     */

    public int readInputAction(){
        List<Integer> availableActions = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        while (true) {
            try {
                System.out.print("\nEnter action: ");
                int action = Integer.parseInt(scan.nextLine());
                if (availableActions.contains(action)) {
                    return action;
                } else {
                    printErrorMsg("invalid");
                }
            } catch (Exception e) {
                printErrorMsg("not a number");

            }
        }
    }

    /**
     * This method's purpose is to act as a user guide
     * on how adding, updating a task,
     * saving/reading a task from a txt file
     * or how to mark a task as done
     * should be used.
     */
    public void printInstructionsTask(String instructionType) {
        System.out.print("\nEnter 0 to RETURN\n");
        switch (instructionType){
            case "add" ->{
                System.out.println("\nTo add a new task, please follow the instructions and press ENTER:");
                System.out.println("ID,  Title,Due Date (format: dd-mm-yyyy),Status,Project Name\n");
                System.out.println("Example of a task to add (pay attention to white space):\n" +
                        "15, finish programm,26-10-2021,in-progress,School");
            }
            case "update"->{
                System.out.println("\nTo update a task, follow the instructions and press ENTER: ");
                System.out.println("Task ID,Title,Due Date (format: dd-mm-yyyy),Status,Project Name");
                System.out.println("ID here represent the ID of the task where an update will occur");
                System.out.println("insert a (-) when an update is not needed to that specific \n");
            }
            case "mark as done"-> System.out.println("\nTo mark a task as done, enter ID and press ENTER \n");

            case "save/read file"->{
                System.out.println("\nPlease enter path to read");
                System.out.println("Example of a file path to read/saving from to \\XXXX\\XXXX\\XXXX\\file.txt");
                System.out.println("After reading/saving press 5 to display the tasks that were read from the file.");
            }
            case "remove"->System.out.println("\nTo remove a task, enter ID and press ENTER");
        }
    }

    /**
     * This method prints the output in a box.
     * @param strings all the words/text that you want to print in the box
     */
    private static void printBox(String... strings) {
        String line;
        int maxBoxWidth = Integer.MIN_VALUE;
        for (String str : strings) {
            maxBoxWidth = Math.max(str.length(), maxBoxWidth);
        }

        String repeat = String.valueOf('─').repeat(Math.max((maxBoxWidth + 2), 0));

        line  = "╭" + repeat + "╮";
        System.out.println(line);

        for (String str : strings) {
            StringBuilder sb = new StringBuilder(str);
            System.out.printf("│ %s │%n",sb.append( String.valueOf(' ').repeat(Math.max(maxBoxWidth - str.length(), 0))));

        }

        line  = "╰" + repeat + "╯";
        System.out.println(line);
    }

    /**
     * This method displays all existing tasks.
     */
    public void printTasks(Map<Integer, Task> tasks){
        System.out.println("\nHere are all the tasks");
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            int key = entry.getKey();
            Task task = entry.getValue();
            printBox("ID:" + key , "Title:"+ task.getTitle(), "Due Date:"
                    + task.getDueDate().toString(), "Status:"+ task.getStatus(), "Project:" + task.getProjectName());
        }
    }

    /**
     * This method prints hints or extra information to help
     * with the visualization
     * @param msgType type of message you want to print
     */
    public void printMsg(String msgType){
        switch(msgType){
            case "title" -> System.out.println("T A P P - Write your own tasks \n-----------------------");
            case "add task" ->System.out.println("Your List is empty, you have to add tasks first");
        }
    }

    /**
     * This method tells the status of a task after a task
     * has been edited, created, deleted, marked as done,
     * sorted by date/project or saved/read tasks to/from a file.
     *
     * @param msgType is the type of output you want to print
     */
    public void printTaskStatus(String msgType, String information){
        System.out.print(GREEN);
        switch(msgType){
            case "no changes" ->  System.out.println("No change was applied...");
            case "read" -> System.out.println("Tasks are being read from path "+information);
            case "status" -> System.out.println("Task status is already done for the task with ID: "+information);
            case "task" ->  System.out.println("Task successfully "+information);
            case "done" ->  System.out.println("Status is set as Done for the task with ID: " + information);
            case "removed" ->  System.out.println("Task with ID: " + information + ", was successfully removed...");
            case "save" -> System.out.println("task succesfully saved into file: " + information);
        }
        System.out.print(RESET);
    }

    /**
     * This method display error messages and tells
     * what went wrong.
     * @param msgType is the type of error we got
     */
    public void printErrorMsg(String msgType){
        System.out.print(RED);
        switch(msgType){
            case "invalid id" ->  System.out.println("Please enter a valid ID or 0 to RETURN");
            case "id exist" -> System.out.println("A task with this ID already exists, try again ");
            case "id doesn't exist" -> System.out.println("ID doesn't exist, try again");
            case "invalid date" -> System.out.println("The date entered is invalid, try again ");
            case "follow instruction / return" ->  System.out.println("Please follow instructions or enter 0 to RETURN");
            case "not a number" ->  System.out.println("Action must be a number...");
            case "invalid" ->System.out.println("Please enter a valid action from the list.");
            case "path/file not found" -> System.out.println("Path or file do not exist...");
        }
        System.out.print(RESET);
    }

    /**
     * This method reads user input by using a scanner.
     *
     * @return userInput user's inserted information
     */
    public String readInputTask() {
        System.out.print("\nEnter your task: ");
        return scan.nextLine();
    }

    /**
     * In this  method gets the path, after
     * printing the instructions.
     *
     * @return user's inserted information
     */
    public String readPathInput(){
        printInstructionsTask("save/read file");
        System.out.print("\nPath:");

        return scan.nextLine();
    }

    /**
     * This method checks if the ID is a number(int).
     *
     * @return  user's inserted information
     */
    public int readTaskId(){
        while (true) {
            System.out.print("\nid:");
            try {
                return Integer.parseInt(scan.nextLine());
            } catch (Exception err) {
                printErrorMsg("invalid id");
            }
        }
    }

    /**
     * This method prints a nice output to
     * tell the user goodbye:)
     */
    public  void printGoodBye(){
        System.out.println("""
                                                                                             \s
                                                           8I   ,dPYb,                       \s
                                                           8I   IP'`Yb                       \s
                                                           8I   I8  8I                       \s
                                                           8I   I8  8'                       \s
                   ,gggg,gg    ,ggggg,    ,ggggg,    ,gggg,8I   I8 dP      gg     gg   ,ggg, \s
                  dP"  "Y8I   dP"  "Y8gggdP"  "Y8gggdP"  "Y8I   I8dP   88ggI8     8I  i8" "8i\s
                 i8'    ,8I  i8'    ,8I i8'    ,8I i8'    ,8I   I8P    8I  I8,   ,8I  I8, ,8I\s
                ,d8,   ,d8I ,d8,   ,d8',d8,   ,d8',d8,   ,d8b, ,d8b,  ,8I ,d8b, ,d8I  `YbadP'\s
                P"Y8888P"888P"Y8888P"  P"Y8888P"  P"Y8888P"`Y8 8P'"Y88P"' P""Y88P"888888P"Y888
                       ,d8I'                                                    ,d8I'        \s
                     ,dP'8I                                                   ,dP'8I         \s
                    ,8"  8I                                                  ,8"  8I         \s
                    I8   8I                                                  I8   8I         \s
                    `8, ,8I                                                  `8, ,8I         \s
                     `Y8P"                                                    `Y8P" \s""");
    }
}

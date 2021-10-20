package todo.app;
import java.util.Map;
/**
 * OutputUtils gives all the output for the commandline
 *
 * @author Janna Esteban
 */
public class OutputUtils {
    private  final String ANSI_RESET = "\u001B[0m";
    private  final String ANSI_BLACK = "\u001B[30m";
    private  final String ANSI_RED = "\u001B[31m";
    private  final String ANSI_GREEN = "\u001B[32m";
    private  final String ANSI_BLUE = "\u001B[34m";
    private  final String ANSI_PURPLE = "\u001B[35m";
    private  final String ANSI_CYAN = "\u001B[36m";

    /**
     * This method prints a nicer output in a box
     * @param strings all the words/text that you want to print in the box
     */
    public static void printBox(String... strings) {
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
     * This method prints hits or extra information for the user
     * @param msgType type of message you want to print
     */
    public void printMsg(String msgType){
        System.out.print(ANSI_CYAN);
        switch(msgType){
            case "title" -> System.out.println("T A P P - Write your own tasks \n-----------------------");
            case "add task" ->System.out.println("Your List is empty, you have to add tasks first");
            case "no tasks" ->  System.out.println("There are no tasks to be saved..");
            case "all tasks" ->   System.out.println("\nHere are all the tasks");
        }
        System.out.print(ANSI_RESET);
    }

    /**
     * This method tells the status of a task after
     * you made it, so that the user now, that it worked
     *
     * @param msgType
     */
    public void printTaskStatus(String msgType, String information){
        System.out.print(ANSI_GREEN);
        switch(msgType){
            case "task" ->  System.out.println("Task successfully "+information);
            case "done" ->  System.out.println("Status is set as Done for the task with ID: " + information);
            case "removed" ->  System.out.println("Task with ID: " + information + ", was successfully removed...");
            case "task saved" -> System.out.println("task succesfully saved into file: " + information); //information -> path
        }
        System.out.print(ANSI_RESET);
    }

    public void printTaskStatus(String msgType){
        System.out.print(ANSI_GREEN);
        switch(msgType){
            case "no changes" ->  System.out.println("No change was applied...");
            case "reading files" -> System.out.println("Tasks are being read...");
        }
        System.out.print(ANSI_RESET);
    }

    /**
     * This method tells the user errors and tells him
     * what went wrong.
     * @param msgType is the type of error we got
     */
    public void printErrorMsg(String msgType){
        System.out.print(ANSI_RED);
        switch(msgType){
            case "invalid id" ->  System.out.println("Please enter a valid ID or 0 to RETURN");
            case "id exist" -> System.out.println("A task with this ID already exists, try again ");
            case "id dont exist" -> System.out.println("ID doesn't exist, try again");
            case "invalid date" -> System.out.println("The date entered is invalid, try again ");
            case "follow instruction" ->  System.out.println("Please follow instructions, try again");
            case "follow instruction / return" ->  System.out.println("Please follow instructions or enter 0 to RETURN");
            case "not a number" ->  System.out.println("Action must be a number...");
            case "invalid" ->System.out.println("Please enter a valid action from the list.");
            case "path/file not found" -> System.out.println("Path or file do not exist...");
        }
        System.out.print(ANSI_RESET);
    }

    /**
     * This method shows the user, that he has to enter
     * an input and which type of input is it.
     * @param inputType is the type of input
     */
    public void printInputInstruction(String inputType){
        System.out.print(ANSI_BLACK);
        switch(inputType){
            case "task" -> System.out.print("\nEnter your task: ");
            case "action" -> System.out.print("\nEnter action: ");
            case "path" -> System.out.print("\npath:");
            case "id" -> System.out.print("\nid:");
        }
        System.out.print(ANSI_RESET);
    }

    /**
     * This method will display all the actions/features
     * that the user might need, to ease the application usage.
     * <p>
     * Will be used to print all valid actions
     */
    public void printAvailableActions(){
        System.out.println(ANSI_BLUE+"\n╭────────────────────────╮     ╭────────────────────────╮     ╭────────────────────────╮");
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
        System.out.println("╰────────────────────────╯"+ANSI_RESET);
    }

    /**
     * This method purpose is to act as a user guide
     * on how adding a task should be used for the user.
     * <p>
     * to ease usage and minimize crashes
     */
    public void printInstructionsTask(String instructionType) {
        System.out.print(ANSI_PURPLE);
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
            case "mark as done"->System.out.println("\nTo mark a task as done, enter ID and press ENTER \n");

            case "save/read file"->{
                System.out.println("\nPlease enter path to read");
                System.out.println("Example of a file path to read/saving from to /XXXX/XXXX/XXXX/file.txt");
                System.out.println("After reading/saving press 5 to display the tasks that were read from the file.");
            }
            case "remove"->System.out.println("\nTo remove a task, enter ID and press ENTER");

        }
        System.out.println("\nEnter 0 to RETURN"+ANSI_RESET);

    }

    /**
     * This method display all existing tasks and how the method is executed and will later be used
     * in the switch statement that runs the program
     * his main purpose is to print out all existing tasks to the user
     *
     */
    public void printTasks(ToDoList toDoList){
        for (Map.Entry<Integer, Task> entry : toDoList.getTasks().entrySet()) {
            int key = entry.getKey();
            Task task = entry.getValue();
            printBox("ID:" + key , "Title:"+ task.getTitle(), "Due Date:"
                    + toDoList.convertDateToString(task.getDueDate(),"dd-MM-yyyy"), "Status:"+ task.getStatus(), "Project:" + task.getProjectName());
        }
    }
    /**
     *
     */
    public  void printGoodBye(){
        System.out.println("                                                                              \n" +
                "                                           8I   ,dPYb,                        \n" +
                "                                           8I   IP'`Yb                        \n" +
                "                                           8I   I8  8I                        \n" +
                "                                           8I   I8  8'                        \n" +
                "   ,gggg,gg    ,ggggg,    ,ggggg,    ,gggg,8I   I8 dP      gg     gg   ,ggg,  \n" +
                "  dP\"  \"Y8I   dP\"  \"Y8gggdP\"  \"Y8gggdP\"  \"Y8I   I8dP   88ggI8     8I  i8\" \"8i \n" +
                " i8'    ,8I  i8'    ,8I i8'    ,8I i8'    ,8I   I8P    8I  I8,   ,8I  I8, ,8I \n" +
                ",d8,   ,d8I ,d8,   ,d8',d8,   ,d8',d8,   ,d8b, ,d8b,  ,8I ,d8b, ,d8I  `YbadP' \n" +
                "P\"Y8888P\"888P\"Y8888P\"  P\"Y8888P\"  P\"Y8888P\"`Y8 8P'\"Y88P\"' P\"\"Y88P\"888888P\"Y888\n" +
                "       ,d8I'                                                    ,d8I'         \n" +
                "     ,dP'8I                                                   ,dP'8I          \n" +
                "    ,8\"  8I                                                  ,8\"  8I          \n" +
                "    I8   8I                                                  I8   8I          \n" +
                "    `8, ,8I                                                  `8, ,8I          \n" +
                "     `Y8P\"                                                    `Y8P\"  ");
    }
}

package todo.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IO {
    private  final String ANSI_RESET = "\u001B[0m";
    private  final String ANSI_BLACK = "\u001B[30m";
    private  final String ANSI_RED = "\u001B[31m";
    private  final String ANSI_GREEN = "\u001B[32m";
    private  final String ANSI_BLUE = "\u001B[34m";
    private  final String ANSI_PURPLE = "\u001B[35m";
    private  final String ANSI_CYAN = "\u001B[36m";
    private final Scanner scan = new Scanner(System.in);
    private final OutputUtils output = new OutputUtils();

    /**
     *This method will get you the answer of the chosen action
     * @return userInput to the start() method, for it to be used
     */

    public int readInputAction(){
        List<Integer> availableActions = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        while (true) {
            try {
                System.out.print(ANSI_BLACK+"\nEnter action: "+ANSI_RESET);
                int action = Integer.parseInt(scan.nextLine());
                if (availableActions.contains(action)) {
                    return action;
                } else {
                    System.out.println(ANSI_RED+"Please enter a valid action from the list."+ANSI_RESET);
                }
            } catch (Exception e) {
                output.printErrorMsg("not a number");

            }
        }
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
     * This method reads user input by using a scanner.
     *
     * @return userInput user's inserted information
     */
    public String readInputTask() {
            output.printInputInstruction("task");
            String userInput = scan.nextLine();
            return  userInput;
    }
    /**
     * it will check against components completion
     * ID existence
     * validity of date
     * and if the user correctly followed instructions
     *
     * @return userInput user's inserted information
     */
    public String readUpdatedInputTask(ToDoList toDoList){
        while (true) {
            output.printInputInstruction("task");
            String userInput = scan.nextLine();

            if (!userInput.equals(0)) {
                String[] parts = userInput.split(",");
                if (parts.length == 5) {
                    boolean dateValidationRequired = true;
                    if (parts[2].equals("-")) {
                        dateValidationRequired = false;
                    }

                    boolean isDateValid = true;
                    if (dateValidationRequired) {
                        isDateValid = toDoList.isDateValid( parts[2]);
                    }

                    if (isDateValid) {
                        if (toDoList.getTasks().get(parts[0]) != null) {
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
     * In this  method reside the implementation of
     * how this program read user's input
     *
     * @return userInput user's inserted information
     */
    public String readPathInput(){
            output.printInputInstruction("path");
            String userInput = scan.nextLine();

            return userInput;
    }

    /**
     * This method will check against components completion
     * ID and task to be removed existence
     * and if the user correctly followed instructions
     *
     * @return userInput user's inserted information
     */
    public int readTaskId(){
        while (true) {
            output.printInputInstruction("id");
            try {
                int userInput =  Integer.parseInt(scan.nextLine());
                return userInput;

            } catch (Exception err) {
                output.printErrorMsg("valid id");
            }
        }
    }
}

package todo.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IO {
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
                output.printInputInstruction("action");
                int action = Integer.parseInt(scan.nextLine());
                if (availableActions.contains(action)) {
                    return action;
                } else {
                    output.printErrorMsg("invalid");
                }
            } catch (Exception e) {
                output.printErrorMsg("not a number");

            }
        }
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

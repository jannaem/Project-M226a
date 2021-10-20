package todo.app;
import java.util.Map;
/**
 * OutputUtils gives all the output for the commandline
 *
 * @author Janna Esteban & Remo Aeberli
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
}

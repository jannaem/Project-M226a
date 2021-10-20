package todo.app;

import java.time.LocalDate;
/**
 * This class facilitates the structure of the tasks to be added,
 * help connect classes and implement features.
 * @author Janna Esteban
 */
public class Task {

    private int id;
    private String title;
    private LocalDate dueDate;
    private String status;
    private String projectName;

    /**
     * @return task's ID
     */

    public int getId() {
        return id;
    }

    /**
     * @return Task's title
     */

    public String getTitle() {
        return title;
    }

    /**
     * @return Task's due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @return Task's status
     */

    public String getStatus() {
        return status;
    }

    /**
     * @return Task's project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param id Task's ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param title Task's title to set
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param dueDate Task's due date to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @param status Task's status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param projectName Task's project name to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * buildTask is used to acquire task's elements and components
     * then uses all the setters methods to build one accordingly
     *
     * @param id          Task's ID to be set
     * @param title       Task's title to be set
     * @param dueDate     Task's due date to be set
     * @param status      Task's status to be set
     * @param projectName Task's project name to set
     * @return task task that was built accordingly with acquired elements and components
     */
    public static Task buildTask(String id, String title, LocalDate dueDate, String status, String projectName) {
        Task task = new Task();

        task.setId(Integer.parseInt(id));
        task.setTitle(title);
        task.setDueDate(dueDate);
        task.setStatus(status);
        task.setProjectName(projectName);

        return task;
    }

    /**
     * toString is used to represent all task's components as strings
     *
     * @return the string representation of different objects
     */

    public String toString(ToDoList toDoList) {
        return id + "," + title + "," + toDoList.convertDateToString(dueDate, "dd-MM-yyyy") + "," + status + "," + projectName;
    }


}

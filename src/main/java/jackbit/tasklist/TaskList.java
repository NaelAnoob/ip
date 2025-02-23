package jackbit.tasklist;

import jackbit.task.Task;
import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> taskList;

    /**
     * Creates a TaskList with the given list of tasks.
     *
     * @param taskList The list of tasks to initialize.
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return taskList.size();
    }

    /**
     * Retrieves a task at the specified index.
     *
     * @param i The index of the task.
     * @return The task at the specified index.
     */
    public Task get(int i) {
        return taskList.get(i);
    }

    /**
     * Removes a task at the specified index.
     *
     * @param i The index of the task to remove.
     */
    public void remove(int i) {
        taskList.remove(i);
    }

    /**
     * Adds a task at the specified index.
     *
     * @param index The position to insert the task.
     * @param task The task to be added.
     */
    public void add(int index, Task task) {
        taskList.add(index, task);
    }

    /**
     * Returns the list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}

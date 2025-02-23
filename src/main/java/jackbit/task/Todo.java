package jackbit.task;

/**
 * Represents a To-Do task.
 */
public class Todo extends Task {

    /**
     * Creates a new To-Do task with the given name.
     *
     * @param name The name of the To-Do task.
     */
    public Todo(String name) {
        super(name);
    }

    /**
     * Returns the string representation of the To-Do task.
     *
     * @return A string indicating that this is a To-Do task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

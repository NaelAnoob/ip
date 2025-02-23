package jackbit.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Deadline class represents a task with a specific deadline.
 */
public class Deadline extends Task {
    private LocalDate by;

    /**
     * Constructs a Deadline instance with the specified name and deadline.
     *
     * @param name The name of the deadline task.
     * @param by   The deadline date in the format "yyyy-MM-dd".
     */
    public Deadline(String name, String by) {
        super(name);
        this.by = LocalDate.parse(by);
    }

    /**
     * Constructs a Deadline instance with the specified name and deadline, optionally using a custom date format.
     *
     * @param name The name of the deadline task.
     * @param by   The deadline date.
     * @param mDY  If true, the date is parsed using the format "MMM d yyyy".
     */
    public Deadline(String name, String by, boolean mDY) {
        super(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        if (mDY) {
            this.by = LocalDate.parse(by, formatter);
        } else {
            this.by = LocalDate.parse(by);
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
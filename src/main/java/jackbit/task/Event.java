package jackbit.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Event class represents a task with a start and end date.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Constructs an Event instance with the specified name, start date, and end date.
     *
     * @param name The name of the event task.
     * @param from The start date in the format "yyyy-MM-dd".
     * @param to   The end date in the format "yyyy-MM-dd".
     */
    public Event(String name, String from, String to) {
        super(name);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    /**
     * Constructs an Event instance with the specified name, start date, and end date, optionally using a custom date format.
     *
     * @param name The name of the event task.
     * @param from The start date.
     * @param to   The end date.
     * @param mDY  If true, the dates are parsed using the format "MMM d yyyy".
     */
    public Event(String name, String from, String to, boolean mDY) {
        super(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        this.from = LocalDate.parse(from, formatter);
        this.to = LocalDate.parse(to, formatter);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
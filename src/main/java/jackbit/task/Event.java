package jackbit.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private LocalDate from;
    private LocalDate to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);

    }

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

package jackbit.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private LocalDate by;

    public Deadline(String name, String by) {
        super(name);
        this.by = LocalDate.parse(by);
    }

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

package duke.Tasks;

import java.time.LocalDateTime;

/**
 * Extending the <code>Task</code> class that includes a <code>LocalDateTime</code> Deadline by
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, inputFormatter);
    }

    public Deadline(String description, String by, boolean loadFlag) {
        super(description);
        this.by = LocalDateTime.parse(by, outputFormatter);
    }

    public LocalDateTime getDateTime() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(outputFormatter) + ")";
    }
}

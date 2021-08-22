package duke.task;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Optional;

public class Deadline extends Task{

    private LocalDate time;

    private Deadline(String description, LocalDate time) {
        super(description);
        this.time = time;
    }

    public static Deadline of(Optional<String> args) throws IllegalArgumentException, DateTimeException {
        // parse args
        String[] parsedArgs = args.orElseThrow(() -> new IllegalArgumentException("☹ OOPS!!! The args of a deadline cannot be empty."))
                                  .split(" /by ");
        if (parsedArgs.length < 2) {
            throw new IllegalArgumentException("☹ OOPS!!! Insufficient args for deadline.");
        }
        LocalDate d = LocalDate.parse(parsedArgs[1]);
        return new Deadline(parsedArgs[0], d);
    }

    public static Task of(boolean isDone, String description, String time) throws DateTimeException {
        Task ret = new Deadline(description, LocalDate.parse(time));
        return isDone ? ret.done() : ret;
    }

    public String getTaskType() { return "D"; }

    @Override
    public String toDatabaseString() {
        return String.format("%s|%s", super.toDatabaseString(), this.time);
    }

    @Override
    public String toString() {
        return String.format("[%s]%s (by: %s)", this.getTaskType(), super.toString(), this.time);
    }
}
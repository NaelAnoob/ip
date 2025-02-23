package jackbit.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private boolean done;
    private final String name;

    public Task(String name){
        this.done = false;
        this.name = name;
    }

    public static Task toTask(String description) {
        String type = description.substring(0,3);
        boolean descDone = description.substring(3, 6).equals("[X]");
        Task task = null;

        switch (type) {
            case "[T]" -> {
                task = new Todo(description.substring(6));
                break;
            }
            case "[D]" -> {
                int nameEnd = description.indexOf("(by: ");
                task = new Deadline(description.substring(6, nameEnd - 1),
                        description.substring(nameEnd + 5, description.length() - 1),
                        true);
                break;
            }
            case "[E]" -> {
                int nameEnd = description.indexOf("(from: ");
                int fromEnd = description.indexOf("to: ");
                task = new Event(description.substring(6, nameEnd - 1),
                        description.substring(nameEnd + "(from: ".length(), fromEnd - 1),
                        description.substring(fromEnd + "to: ".length(), description.length() - 1),
                        true);
                break;
            }
        }

        if (descDone) {task.mark();}
        return task;
    }


    public String getName() {
        return this.name;
    }
    public void mark(){
        this.done = true;
    }
    public void unmark(){
        this.done = false;
    }



    public String toString(){
        String mark = !this.done ? "[ ]" : "[X]";
        return mark + " " + name;
    }
}








//END OF TASK//

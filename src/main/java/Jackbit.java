import java.util.ArrayList;
import java.util.Scanner;
public class Jackbit {



    public static class Task {
        private boolean done;
        private final String name;

        public Task(String name){
            this.done = false;
            this.name = name;
        }

        public void mark(){
            this.done = true;
            System.out.println("Nice! I've marked this task as done: \n     " + this);
        }

        public void unmark(){
            this.done = false;
            System.out.println("OK, I've marked this task as not done yet: \n     " + this);
        }

        public String toString(){
            String mark = !this.done ? "[]" : "[X]";
            return mark + " " + name;
        }
    }

    public static class Todo extends Task {

        public Todo(String name) {
            super(name);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    public static class Deadline extends Task {

        private String by;

        public Deadline(String name, String by) {
            super(name);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    public static class Event extends Task {

        private String from;
        private String to;

        public Event(String name, String from, String to) {
            super(name);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }


    public static class JackbitException extends Exception {
        public JackbitException(String errorMessage) {
            super(errorMessage);
        }

    }



    public static void main(String[] args) {
        ArrayList<Task> taskList = new ArrayList<>(100);
        String box =    "       XXXXXXXXXXXXXXXXX        \n" +
                        "     XXX     XX  XX    XX       \n" +
                        "   XX        XX  XX     X       \n" +
                        "   XX                   X       \n" +
                        "    X        X    X     X       \n" +
                        "    X        XXXXXX     X       \n" +
                        "     X                 XX       \n" +
                        "     XXXXXXXXXXXXXXXXXXX        \n" +
                        "            XXXX                \n" +
                        "          XXXX                  \n" +
                        "           XXXX                 \n" +
                        "            XXXXX               \n" +
                        " XXX     XXXX             XXXX  \n" +
                        "   XXX    XXXX          XXX     \n" +
                        "    XXX     XXXX       XXX      \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       \n" +
                        "     XXXXXXXXXXXXXXXXXXXX       ";

        System.out.println("\n" + box + "\n \n POP!! I'm JackBit, but you can call me Jack! \n  Have anything to talk about?");
        Scanner chatter = new Scanner(System.in);
        task_list(chatter, taskList);
    }

    private static void echo(Scanner chatter){
        String msg = chatter.nextLine();
        while (!msg.equals("bye")) {
            System.out.println(msg);
            msg = chatter.nextLine();
        }
        System.out.println("\n ________________________________ \n\n See you later!!");
    }

    private static void task_list(Scanner chatter, ArrayList<Task> taskList) {
        String msg = chatter.nextLine();
        int i = 0;
        Integer marker;

        while (!msg.equals("bye")){

            if (msg.equals("list")) {
                System.out.println("Here are the tasks in your task_list: \n");
                for (int c = 1; c <= i; c++){
                    System.out.println(c + "." + taskList.get(c - 1));
                }
            } else if (msg.startsWith("mark")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                taskList.get(marker - 1).mark();
            } else if (msg.startsWith("unmark")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                taskList.get(marker - 1).unmark();
            } else if (msg.startsWith("delete")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                i--;

                System.out.println("I have removed this task from your task_list: \n" + taskList.get(marker - 1) + "\n The number of tasks you have is " + i);
                taskList.remove(marker.intValue() - 1);

            }


            else { //Adding to list
                if (msg.startsWith("todo")) {
                    if (msg.length() < 6) {
                        try {
                            throw new JackbitException("You need to wind me up a little more bud. Give me more to work with and write your task fully.");
                        } catch (JackbitException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    taskList.add(i, new Todo(msg));
                } else if (msg.startsWith("deadline")) {
                    String name = msg.substring(9,msg.indexOf("/by"));
                    String by = msg.substring(msg.indexOf("/by") + 4);
                    taskList.add(i, new Deadline(name, by));
                } else if (msg.startsWith("event")) {
                    String name = msg.substring(6,msg.indexOf("/from"));
                    String from = msg.substring(msg.indexOf("/from") + 6, msg.indexOf("/to"));
                    String to = msg.substring(msg.indexOf("/to") + 4);
                    taskList.add(i, new Event(name, from, to));
                } else {
                    try {
                        throw new JackbitException("First rule you learn in clown school: Random gibberish is never funny");
                    } catch (JackbitException e) {
                        throw new RuntimeException(e);
                    }
                }


                System.out.println("I've added this task: \n" + taskList.get(i) + "\n The number of tasks you have is " + (i + 1));

                i++;


            }

            msg = chatter.nextLine();

        }

        System.out.println("\n ________________________________ \n\n See you later!!");

    }
}

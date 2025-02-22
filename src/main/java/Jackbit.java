import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Jackbit {



    public static class Task {
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

    public static class Event extends Task {

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


    public static class JackbitException extends Exception {
        public JackbitException(String errorMessage) {
            super(errorMessage);
        }

    }



    public static void main(String[] args) {

        ArrayList<Task> taskList;
        try {
            taskList = loadTasks();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String box = "       XXXXXXXXXXXXXXXXX        \n" +
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
        int index = taskList.size();
        Integer marker;

        while (!msg.equals("bye")){

            if (msg.equals("list")) {
                System.out.println("Here are the tasks in your task_list: \n");
                for (int c = 1; c <= index; c++){
                    System.out.println(c + "." + taskList.get(c - 1));
                }
            } else if (msg.startsWith("mark")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                Task task = taskList.get(marker - 1);
                task.mark();
                System.out.println("Nice! I've marked this task as done: \n     " + task);

            } else if (msg.startsWith("unmark")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                Task task = taskList.get(marker - 1);
                task.unmark();
                System.out.println("OK, I've marked this task as not done yet: \n     " + task);
            } else if (msg.startsWith("delete")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                index--;

                System.out.println("I have removed this task from your task_list: \n" + taskList.get(marker - 1) + "\n The number of tasks you have is " + index);
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
                    taskList.add(index, new Todo(msg));
                } else if (msg.startsWith("deadline")) {
                    String name = msg.substring(9,msg.indexOf("/by"));
                    String by = msg.substring(msg.indexOf("/by") + 4);
                    taskList.add(index, new Deadline(name, by));
                } else if (msg.startsWith("event")) {
                    String name = msg.substring(6,msg.indexOf("/from"));
                    String from = msg.substring(msg.indexOf("/from") + 6, msg.indexOf("/to") - 1);
                    String to = msg.substring(msg.indexOf("/to") + 4);
                    taskList.add(index, new Event(name, from, to));
                } else {
                    try {
                        throw new JackbitException("First rule you learn in clown school: Random gibberish is never funny");
                    } catch (JackbitException e) {
                        throw new RuntimeException(e);
                    }
                }


                System.out.println("I've added this task: \n" + taskList.get(index) + "\n The number of tasks you have is " + (index + 1));

                index++;


            }

            msg = chatter.nextLine();

        }

        System.out.println("\n ________________________________ \n\n See you later!!");
        try {
            saveTasks(taskList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void saveTasks(ArrayList<Task> taskList) throws IOException {
        FileWriter newData = new FileWriter("./data/jackbit.txt");

        for (Task task : taskList) {
            newData.write(task.toString() + "\n");
        }

        newData.close();

    }

    private static ArrayList<Task> loadTasks() throws FileNotFoundException {
        File taskData = new File("./data/jackbit.txt");
        Scanner s = new Scanner(taskData);
        int i = 1;
        ArrayList<Task> taskList = new ArrayList<>(100);

        while (s.hasNext()) {

            Task task = Task.toTask(s.nextLine());
            System.out.println(i + ". " + task.toString());
            taskList.add(task);
            i++;
        }

        return taskList;
    }






    // END OF JACKBIT CLASS
}



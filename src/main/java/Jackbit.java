import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Jackbit {


    //UI//

    private class Ui {
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

        public void welcome() {
            System.out.println("\n" + box + "\n \n POP!! I'm JackBit, but you can call me Jack! \n  Have anything to talk about?");
        }
    }

    //END OF UI//

    //STORAGE//

    private class Storage {
        private String filePath;

        public Storage(String filePath){
            this.filePath = filePath;
        }

        private ArrayList<Task> load() throws FileNotFoundException{
            File file = new File(filePath);
            Scanner s = new Scanner(file);
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

        private void save(ArrayList<Task> taskList) throws IOException {
            FileWriter fileWriter = new FileWriter(filePath);

            for (Task task : taskList) {
                fileWriter.write(task.toString() + "\n");
            }

            fileWriter.close();

        }
    }

    //END OF STORAGE//


    //TASKLIST//

    private class TaskList {
        ArrayList<Task> taskList;

        public TaskList(ArrayList<Task> taskList) {
            this.taskList = taskList;
        }


        public int size() {
            return taskList.size();
        }

        public Task get(int i) {
            return taskList.get(i);
        }

        public void remove(int i) {
            taskList.remove(i);
        }

        public void add(int index, Task task) {
            taskList.add(index, task);
        }

        public ArrayList<Task> getTaskList() {
            return taskList;
        }
    }

    //END OF TASKLIST//


    //PARSER//



    //END OF PARSER//


    //TASK//


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


    //END OF TASK//



    public static class JackbitException extends Exception {
        public JackbitException(String errorMessage) {
            super(errorMessage);
        }

    }

    private Storage storage;
    private TaskList tasks;
    private Ui ui;



    public Jackbit(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void run() {
        ui.welcome();
        Scanner chatter = new Scanner(System.in);
        String msg = chatter.nextLine();
        int index = tasks.size();
        Integer marker;

        while (!msg.equals("bye")){

            if (msg.equals("list")) {
                System.out.println("Here are the tasks in your task_list: \n");
                for (int c = 1; c <= index; c++){
                    System.out.println(c + "." + tasks.get(c - 1));
                }
            } else if (msg.startsWith("mark")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                Task task = tasks.get(marker - 1);
                task.mark();
                System.out.println("Nice! I've marked this task as done: \n     " + task);

            } else if (msg.startsWith("unmark")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                Task task = tasks.get(marker - 1);
                task.unmark();
                System.out.println("OK, I've marked this task as not done yet: \n     " + task);
            } else if (msg.startsWith("delete")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                index--;

                System.out.println("I have removed this task from your task_list: \n" + tasks.get(marker - 1) + "\n The number of tasks you have is " + index);
                tasks.remove(marker.intValue() - 1);

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
                    tasks.add(index, new Todo(msg));
                } else if (msg.startsWith("deadline")) {
                    String name = msg.substring(9,msg.indexOf("/by"));
                    String by = msg.substring(msg.indexOf("/by") + 4);
                    tasks.add(index, new Deadline(name, by));
                } else if (msg.startsWith("event")) {
                    String name = msg.substring(6,msg.indexOf("/from"));
                    String from = msg.substring(msg.indexOf("/from") + 6, msg.indexOf("/to") - 1);
                    String to = msg.substring(msg.indexOf("/to") + 4);
                    tasks.add(index, new Event(name, from, to));
                } else {
                    try {
                        throw new JackbitException("First rule you learn in clown school: Random gibberish is never funny");
                    } catch (JackbitException e) {
                        throw new RuntimeException(e);
                    }
                }


                System.out.println("I've added this task: \n" + tasks.get(index) + "\n The number of tasks you have is " + (index + 1));

                index++;


            }

            msg = chatter.nextLine();

        }

        System.out.println("\n ________________________________ \n\n See you later!!");

        try {
            storage.save(tasks.getTaskList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public static void main(String[] args) {

        new Jackbit("data/jackbit.txt").run();

    }

    private void echo(Scanner chatter){
        String msg = chatter.nextLine();
        while (!msg.equals("bye")) {
            System.out.println(msg);
            msg = chatter.nextLine();
        }
        System.out.println("\n ________________________________ \n\n See you later!!");
    }






    // END OF JACKBIT CLASS
}



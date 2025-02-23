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


    // PARSER //

    private class Parser {
        private TaskList taskList;

        public Parser(TaskList taskList) {
            this.taskList = taskList;
        }

        /**
         * Parses the user command and performs the corresponding action.
         *
         * @param command The user command to parse.
         */
        public void parse(String command) throws JackbitException{
            if (command.equals("list")) {
                listTasks();
            } else if (command.startsWith("mark")) {
                markTask(command);
            } else if (command.startsWith("unmark")) {
                unmarkTask(command);
            } else if (command.startsWith("delete")) {
                deleteTask(command);
            } else if (command.startsWith("todo") || command.startsWith("deadline") || command.startsWith("event")) {
                addTask(command);
            } else {
                throw new JackbitException("First rule you learn in clown school: Random gibberish is never funny");
            }
        }

        private void listTasks() {
            System.out.println("Here are the tasks in your task_list: \n");
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i));
            }
        }

        private void markTask(String command) {
            int index = Integer.parseInt(command.substring(5).trim()) - 1;
            Task task = taskList.get(index);
            task.mark();
            System.out.println("Nice! I've marked this task as done: \n     " + task);
        }

        private void unmarkTask(String command) {
            int index = Integer.parseInt(command.substring(7).trim()) - 1;
            Task task = taskList.get(index);
            task.unmark();
            System.out.println("OK, I've marked this task as not done yet: \n     " + task);
        }

        private void deleteTask(String command) {
            int index = Integer.parseInt(command.substring(7).trim()) - 1;
            Task task = taskList.get(index);
            taskList.remove(index);
            System.out.println("I have removed this task from your task_list: \n" + task + "\n The number of tasks you have is " + taskList.size());
        }

        private void addTask(String command) throws JackbitException {
            if (command.startsWith("todo")) {
                if (command.length() < 6) {
                    throw new JackbitException("You need to wind me up a little more bud. Give me more to work with and write your task fully.");
                }
                taskList.add(taskList.size(), new Todo(command.substring(5).trim()));
            } else if (command.startsWith("deadline")) {
                if (command.length() < 10) {
                    throw new JackbitException("You need to wind me up a little more bud. Give me more to work with and write your task fully.");
                }
                String[] parts = command.substring(9).split("/by");
                if (parts.length < 2) {
                    throw new JackbitException("Invalid deadline format. Use: deadline <description> /by <date>");
                }
                taskList.add(taskList.size(), new Deadline(parts[0].trim(), parts[1].trim()));
            } else if (command.startsWith("event")) {
                if (command.length() < 7) {
                    throw new JackbitException("You need to wind me up a little more bud. Give me more to work with and write your task fully.");
                }
                String[] parts = command.substring(6).split("/from|/to");
                if (parts.length < 3) {
                    throw new JackbitException("Invalid event format. Use: event <description> /from <start> /to <end>");
                }
                taskList.add(taskList.size(), new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
            }
            System.out.println("I've added this task: \n" + taskList.get(taskList.size() - 1) + "\n The number of tasks you have is " + taskList.size());
        }
    }

// END OF PARSER //


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
    private Parser parser;


    public Jackbit(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        parser = new Parser(tasks);
    }


    public void run() {
        ui.welcome();
        Scanner chatter = new Scanner(System.in);
        String msg = chatter.nextLine();

        while (!msg.equals("bye")){

            try {
                parser.parse(msg);
                msg = chatter.nextLine();
            } catch (JackbitException e) {
                try {
                    storage.save(tasks.getTaskList());
                } catch (IOException er) {
                    throw new RuntimeException(e);
                }
                throw new RuntimeException(e);
            }

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



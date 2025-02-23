package jackbit.parser;

import jackbit.Jackbit.JackbitException;

import jackbit.task.Deadline;
import jackbit.task.Event;
import jackbit.task.Task;
import jackbit.task.Todo;
import jackbit.tasklist.TaskList;

/**
 * The Parser class is responsible for parsing user commands and executing corresponding actions.
 */
public class Parser {
    private TaskList taskList;

    /**
     * Constructs a Parser instance with the specified TaskList.
     *
     * @param taskList The TaskList to be managed by the parser.
     */
    public Parser(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Parses the user command and performs the corresponding action.
     *
     * @param command The user command to parse.
     * @throws JackbitException If the command is invalid or incomplete.
     */
    public void parse(String command) throws JackbitException {
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
        for (int i = 0; i < taskList.getSize(); i++) {
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
        System.out.println("I have removed this task from your task_list: \n" + task + "\n The number of tasks you have is " + taskList.getSize());
    }

    private void addTask(String command) throws JackbitException {
        if (command.startsWith("todo")) {
            if (command.length() < 6) {
                throw new JackbitException("You need to wind me up a little more bud. Give me more to work with and write your task fully.");
            }
            taskList.add(taskList.getSize(), new Todo(command.substring(5).trim()));
        } else if (command.startsWith("deadline")) {
            if (command.length() < 10) {
                throw new JackbitException("You need to wind me up a little more bud. Give me more to work with and write your task fully.");
            }
            String[] parts = command.substring(9).split("/by");
            if (parts.length < 2) {
                throw new JackbitException("Invalid deadline format. Use: deadline <description> /by <date>");
            }
            taskList.add(taskList.getSize(), new Deadline(parts[0].trim(), parts[1].trim()));
        } else if (command.startsWith("event")) {
            if (command.length() < 7) {
                throw new JackbitException("You need to wind me up a little more bud. Give me more to work with and write your task fully.");
            }
            String[] parts = command.substring(6).split("/from|/to");
            if (parts.length < 3) {
                throw new JackbitException("Invalid event format. Use: event <description> /from <start> /to <end>");
            }
            taskList.add(taskList.getSize(), new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
        }
        System.out.println("I've added this task: \n" + taskList.get(taskList.getSize() - 1) + "\n The number of tasks you have is " + taskList.getSize());
    }
}
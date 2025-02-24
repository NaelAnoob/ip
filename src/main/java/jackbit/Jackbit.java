package jackbit;

import jackbit.ui.Ui;
import jackbit.storage.Storage;
import jackbit.parser.Parser;

import jackbit.tasklist.TaskList;

import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.IOException;

/**
 * The main class for the Jackbit application.
 * This class initializes the application and handles the main execution loop.
 */
public class Jackbit {

    /**
     * Custom exception class for Jackbit-specific errors.
     */
    public static class JackbitException extends Exception {
        public JackbitException(String errorMessage) {
            super(errorMessage);
        }
    }

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Constructs a Jackbit instance with the specified file path for storage.
     *
     * @param filePath The path to the file where tasks are stored.
     */
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

    /**
     * Runs the Jackbit application.
     * This method handles the main execution loop and processes user input.
     */
    public String run(String msg) {
        ui.welcome();
        String reply;

        try {
            reply = parser.parse(msg);
        } catch (JackbitException e) {
            try {
                storage.save(tasks.getTaskList());
            } catch (IOException er) {
                throw new RuntimeException(e);
            }
            return e.getMessage();
        }

        try {
            storage.save(tasks.getTaskList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return reply;
    }



    private void echo(Scanner chatter) {
        String msg = chatter.nextLine();
        while (!msg.equals("bye")) {
            System.out.println(msg);
            msg = chatter.nextLine();
        }
        System.out.println("\n ________________________________ \n\n See you later!!");
    }
}
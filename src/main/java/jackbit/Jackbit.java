package jackbit;

import jackbit.ui.Ui;
import jackbit.storage.Storage;
import jackbit.parser.Parser;

import jackbit.tasklist.TaskList;

import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.IOException;

public class Jackbit {

    //TASKLIST//


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



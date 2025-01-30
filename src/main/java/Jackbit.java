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

    public static void main(String[] args) {
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
        task_list(chatter);
    }

    private static void echo(Scanner chatter){
        String msg = chatter.nextLine();
        while (!msg.equals("bye")) {
            System.out.println(msg);
            msg = chatter.nextLine();
        }
        System.out.println("\n ________________________________ \n\n See you later!!");
    }

    private static void task_list(Scanner chatter){
        Task[] task_list = new Task[100];
        String msg = chatter.nextLine();
        int i = 0;
        Integer marker;

        while (!msg.equals("bye")){

            if (msg.equals("list")) {
                System.out.println("Here are the tasks in your task_list: \n");
                for (int c = 1; c <= i; c++){
                    System.out.println(c + "." + task_list[c-1]);
                }
            } else if (msg.startsWith("mark")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                task_list[marker - 1].mark();
            } else if (msg.startsWith("unmark")) {
                marker = Integer.valueOf(msg.substring(msg.length() - 1));
                task_list[marker - 1].unmark();
            } else {
                task_list[i] = new Task(msg);
                i++;
                System.out.println("added: " + msg);
            }

            msg = chatter.nextLine();

        }

        System.out.println("\n ________________________________ \n\n See you later!!");

    }
}

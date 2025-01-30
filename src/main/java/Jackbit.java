import java.util.Scanner;
public class Jackbit {
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
        add_list(chatter);
    }

    private static void echo(Scanner chatter){
        String msg = chatter.nextLine();
        while (!msg.equals("bye")) {
            System.out.println(msg);
            msg = chatter.nextLine();
        }
        System.out.println("\n ________________________________ \n\n See you later!!");
    }

    private static void add_list(Scanner chatter){
        String[] list = new String[100];
        String msg = chatter.nextLine();
        int i = 0;

        while (!msg.equals("bye")){

            if (msg.equals("list")) {
                for (int c = 1; c <= i; c++){
                    System.out.println(c + ". " + list[c-1]);
                }
            } else {
                list[i] = msg;
                i++;
                System.out.println("added: " + msg);
            }
            
            msg = chatter.nextLine();

        }

        System.out.println("\n ________________________________ \n\n See you later!!");

    }
}

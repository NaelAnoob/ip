package jackbit.ui;

/**
 * Handles user interactions and displays messages.
 */
public class Ui {
    private final String box = "       XXXXXXXXXXXXXXXXX        \n" +
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
            "     XXXXXXXXXXXXXXXXXXXX       ";

    /**
     * Displays a welcome message with an ASCII art banner.
     */
    public void welcome() {
        System.out.println("\n" + box + "\n \n POP!! I'm JackBit, but you can call me Jack! \n  Have anything to talk about?");
    }
}

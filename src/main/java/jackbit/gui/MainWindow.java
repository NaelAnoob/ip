package jackbit.gui;

import jackbit.Jackbit;
import jackbit.ui.Ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Stage stage;

    private Jackbit jackbit;
    private Ui ui;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image jackImage = new Image(this.getClass().getResourceAsStream("/images/jackbit.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String welcomeMessage = new Ui().welcome();
        dialogContainer.getChildren().addAll(
                DialogBox.getJackDialog(welcomeMessage, jackImage)
        );
    }

    /** Injects the Jackbit instance */
    public void setJack(Jackbit j) {
        jackbit = j;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = jackbit.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJackDialog(response, jackImage)
        );
        userInput.clear();

        // Close the stage if the user says "bye"
        if (input.trim().equalsIgnoreCase("bye")) {
            stage.close();
        }
    }
}

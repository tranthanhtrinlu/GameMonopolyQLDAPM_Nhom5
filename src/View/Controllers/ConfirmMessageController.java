package View.Controllers;
import javax.swing.*;

/**
 * Controller for user input on confirming some dialogue
 * @author Cory Helm
 */
public class ConfirmMessageController {

    /**
     * Sends a message to the user of whatever String s
     * @param frame JFrame, the frame relative to
     * @param s String, the message
     */
    public void sendMessage(JFrame frame, String s) {
        JOptionPane.showMessageDialog(frame,
                s);
    }
}

package View.Controllers;

import javax.swing.*;

public class ConfirmMessageController {
    public void sendMessage(JFrame frame, String s) {
        JOptionPane.showMessageDialog(frame,
                s);
    }
}

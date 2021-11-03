package View.Controllers;

import View.BoardGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StartGameController {

    public int getNumOfPlayers(JFrame frame) {
        Integer[] choices = {2, 3, 4, 5};
        return (Integer) JOptionPane.showInputDialog(frame, "Choose Number Of Players",
                "How many players?", JOptionPane.DEFAULT_OPTION, null,
                choices,
                choices[0]);
    }

    public ArrayList<String> getNameOfPlayers(int num, JFrame frame) {
        ArrayList<JTextField> names = new ArrayList<>();
        JPanel myPanel = new JPanel(new GridLayout(num,2));
        for (int i = 0; i<num; i++){
            myPanel.add(new JLabel("Name " + (i+1) + ":"));
            names.add(new JTextField());
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(names.get(i));
        }
        int result = JOptionPane.showConfirmDialog(frame, myPanel,
                "Enter Info", JOptionPane.DEFAULT_OPTION);

        // if result == -1 check if any names are missing. needs to be dummy proof

        ArrayList<String> nameText = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            nameText.add(names.get(i).getText());
        }
        return nameText;

    }
}

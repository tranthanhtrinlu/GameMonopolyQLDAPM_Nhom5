package View.Controllers;
import Model.BoardModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controller for intro to the game
 * mainly for getting the number of players and their names
 * @author Tony Massaad and Cory Helm
 */
public class StartGameController {


    private final static String PLAYER = " Players";
    private final static String AI_PLAYER = " AI";

    /**
     * gets the current number of players in the game
     * @param frame JFrame
     * @return Integer of number of players
     */
    public int getNumOfPlayers(JFrame frame) {
        JPanel panel = new JPanel(new GridLayout(4,1));
        AtomicInteger num = new AtomicInteger(2);
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < BoardModel.MAX_PLAYERS-1; i++){
            addingToPanel(panel, num, group, i, i+2, PLAYER);
        }
        JOptionPane.showConfirmDialog(frame, panel,
                "number of players", JOptionPane.DEFAULT_OPTION);
        return num.get();
    }

    /**
     * gets the name of all the players in the game
     * Does not exit until all names according to num are filled
     * @param num Integer num of players
     * @param frame JFrame
     * @return array of names
     */
    public ArrayList<String> getNameOfPlayers(int num, JFrame frame) {
        ArrayList<JTextField> names = new ArrayList<>();
        ArrayList<String> nameText = new ArrayList<>();
        JPanel myPanel = new JPanel(new GridLayout(num,2));
        for (int i = 0; i<num; i++){
            myPanel.add(new JLabel("Name " + (i+1) + ":"));
            names.add(new JTextField());
            myPanel.add(Box.createHorizontalStrut(15));
            myPanel.add(names.get(i));
        }
        while (true){
            boolean stay = true;
            int result = JOptionPane.showConfirmDialog(frame, myPanel,
                    "Enter Info", JOptionPane.DEFAULT_OPTION);
            if (result == 0){
                for (int i = 0; i < num; i++) {
                    if (names.get(i).getText().equals("")){
                        nameText.clear();
                        break;
                    }
                    nameText.add(names.get(i).getText());
                }
                if (nameText.size() == num)
                    stay = false;
                if (!stay)
                    break;
            }
        }
        return nameText;
    }

    /**
     * get the choice of the number of AI
     * @param frame JFrame, the frame
     * @param numberOfPlayers Integer, the number of players
     * @return Integer, the number of AIs
     */
    public int getNumOfAIs(JFrame frame, int numberOfPlayers) {
        int numOfAIs = (BoardModel.MAX_PLAYERS) - numberOfPlayers;
        JPanel panel = new JPanel(new GridLayout(numOfAIs+2,1));
        AtomicInteger num = new AtomicInteger(0);
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i<numOfAIs+1; i++){
            addingToPanel(panel, num, group, i, i, AI_PLAYER);
        }
        JOptionPane.showConfirmDialog(frame, panel,
                "Enter Number of AIs", JOptionPane.DEFAULT_OPTION);
        return num.get();
    }

    /**
     * Add to the choice panel for the viewer to choose from
     * @param panel JPanel, the panel
     * @param num AtomicInteger, the val to set the choice
     * @param group ButtonGroup, group the radio buttons
     * @param actualVal Integer, the value index in which the button is at
     * @param theoreticalVal Integer, the value index on what to set the num and button text to
     * @param name String, the name. AI if AI otherwise PLAYER
     */
    private void addingToPanel(JPanel panel, AtomicInteger num, ButtonGroup group, int actualVal, int theoreticalVal, String name){
        JRadioButton button = new JRadioButton(theoreticalVal + name);
        if (actualVal == 0)
            button.setSelected(true);
        button.addActionListener(e -> num.set(theoreticalVal));
        group.add(button);
        panel.add(button);
    }

}

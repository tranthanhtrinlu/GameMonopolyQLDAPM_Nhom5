package View.Components;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerDisplayPanel extends JPanel {


    private ArrayList<JButton> playerButtons;
    private ArrayList<JPanel> playerDisplays;
    public PlayerDisplayPanel(){
        this.playerButtons = new ArrayList<>();
        this.playerDisplays = new ArrayList<>();
        this.setBackground(new Color(224, 225, 224));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void addNewPlayerViewButton(Player p){
        JPanel playerDisplay = new JPanel();
        playerDisplay.setLayout(new BoxLayout(playerDisplay, BoxLayout.Y_AXIS));
        JButton playerButton = new JButton(p.getPlayerName());
        playerButton.addActionListener(e -> {
            System.out.println("Do something");
            // do function here add the following the the JPanel playerDisplay in the array list accordingly and update it
            // Location: currlocation
            // label: money
            // Properties:
            //list of properties
        });
        playerDisplay.add(playerButton);
        this.playerButtons.add(playerButton);
        this.playerDisplays.add(playerDisplay);
        this.add(playerDisplay);
        this.revalidate();
        //this.repaint();
    }

    public void removePlayerView(int i){
        this.playerDisplays.remove(i);
        this.playerButtons.remove(i);
    }

}

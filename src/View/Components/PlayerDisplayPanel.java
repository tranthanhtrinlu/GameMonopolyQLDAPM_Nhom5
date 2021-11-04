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
        JButton playerButton = new JButton(p.getPlayerName() + " +");
        playerButton.addActionListener(e -> {
            System.out.println("Do something");
            if (playerButton.getText().substring(playerButton.getText().length() - 1).equals("+")){
                playerDisplay.add(new JLabel("Current Location: " + p.getCurrLocation()));
                playerDisplay.add(new JLabel("Money: " + p.getMoneyAmount()));
                playerDisplay.add(new JLabel("Properties:"));
                for (int i = 0; i < p.getNumOfProperties(); i++){
                    playerDisplay.add(new JLabel(p.getPropertyByIndex(i).getName()));
                }
                playerButton.setText(playerButton.getText().substring(0,playerButton.getText().length() - 1) + "-");
            }
            else{
                playerDisplay.removeAll();
                playerButton.setText(playerButton.getText().substring(0,playerButton.getText().length() - 1) + "+");
                playerDisplay.add(playerButton);
            }
            playerDisplay.revalidate();
        });
        playerDisplay.add(playerButton);
        this.playerButtons.add(playerButton);
        this.playerDisplays.add(playerDisplay);
        this.add(playerDisplay);
        this.revalidate();
    }

    public void removePlayerView(int i){
        this.playerDisplays.remove(i);
        this.playerButtons.remove(i);
    }

    public void updatePlayerDisplay(int index, Player p) {
        JPanel panel = this.playerDisplays.get(index);
        JButton button = this.playerButtons.get(index);
        panel.removeAll();
        if (button.getText().substring(button.getText().length() - 1).equals("+")){
            panel.add(new JLabel("Current Location: " + p.getCurrLocation()));
            panel.add(new JLabel("Money: " + p.getMoneyAmount()));
            panel.add(new JLabel("Properties:"));
            for (int i = 0; i < p.getNumOfProperties(); i++){
                panel.add(new JLabel(p.getPropertyByIndex(i).getName()));
            }
        }
        else{
            panel.add(button);
        }
        panel.revalidate();
    }
}

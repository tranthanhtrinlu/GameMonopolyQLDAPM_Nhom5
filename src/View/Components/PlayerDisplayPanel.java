package View.Components;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerDisplayPanel extends JPanel {
    private final static String CURRENT_TURN = "(Playing)";
    private final static String DROP_DOWN_STRING = "+";
    private final static String UP_STRING = "-";
    private final static String OUT_STRING = "(out)";

    private ArrayList<JButton> playerButtons;
    private ArrayList<JPanel> playerDisplays;
    private ArrayList<Boolean> turns;
    public PlayerDisplayPanel(){
        this.playerButtons = new ArrayList<>();
        this.playerDisplays = new ArrayList<>();
        this.turns = new ArrayList<>();
        this.setBackground(new Color(224, 225, 224));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void addNewPlayerViewButton(Player p, int index){
        JPanel playerDisplay = new JPanel();
        playerDisplay.setLayout(new BoxLayout(playerDisplay, BoxLayout.Y_AXIS));
        JButton playerButton;
        if (index == 0)
            playerButton = new JButton(p.getPlayerName() + " " + DROP_DOWN_STRING + " " + CURRENT_TURN);
        else
            playerButton = new JButton(p.getPlayerName() + " " + DROP_DOWN_STRING);
        //playerButton.setPreferredSize(new Dimension(200, playerButton.getHeight()));

        playerButton.addActionListener(e -> {
            String[] text = playerButton.getText().split(" ");
            int length = text.length;
            if (text[length-1].equals(DROP_DOWN_STRING) || (text[length-1].equals(CURRENT_TURN) && text[length-2].equals(DROP_DOWN_STRING))){
                playerDisplay.add(new JLabel("Current Location: " + p.getCurrLocation()));
                playerDisplay.add(new JLabel("Money: " + p.getMoneyAmount()));
                playerDisplay.add(new JLabel("Properties:"));
                for (int i = 0; i < p.getNumOfProperties(); i++){
                    playerDisplay.add(new JLabel(p.getPropertyByIndex(i).getName()));
                }
                if (text[length-1].equals(CURRENT_TURN))
                    playerButton.setText(p.getPlayerName() + " " + UP_STRING + " " + CURRENT_TURN);
                else
                    playerButton.setText(p.getPlayerName() + " " + UP_STRING);
            }
            else if (text[length-1].equals(UP_STRING) || (text[length-1].equals(CURRENT_TURN) && text[length-2].equals(UP_STRING))){
                playerDisplay.removeAll();
                if (text[length-1].equals(CURRENT_TURN))
                    playerButton.setText(p.getPlayerName() + " " + DROP_DOWN_STRING + " " + CURRENT_TURN);
                else
                    playerButton.setText(p.getPlayerName() + " " + DROP_DOWN_STRING);
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

    public void removePlayerView(int i, Player p){
        JPanel panel = this.playerDisplays.get(i);
        JButton button = this.playerButtons.get(i);
        button.setText(p.getPlayerName() + " " + OUT_STRING);
        panel.removeAll();
        button.setEnabled(false);
        panel.add(button);
        panel.revalidate();
    }

    public void updatePlayerDisplay(int index, Player p) {
        JPanel panel = this.playerDisplays.get(index);
        JButton button = this.playerButtons.get(index);
        String[] text = button.getText().split(" ");
        int length = text.length;
        panel.removeAll();
        if (text[length-1].equals(UP_STRING) || (text[length-1].equals(CURRENT_TURN) && text[length-2].equals(UP_STRING))){
            panel.add(button);
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


    public void updateCurrentTurn(int currentTurn, int index, ArrayList<Player> players){
        String[] text = this.playerButtons.get(index).getText().split(" ");

        if (index == currentTurn){
            String s;
            s = players.get(index).getPlayerName() + " " + UP_STRING + " " + CURRENT_TURN;
            if (text[text.length - 1].equals(DROP_DOWN_STRING) || (text[text.length-1].equals(CURRENT_TURN) && text[text.length-2].equals(DROP_DOWN_STRING))){
                s = players.get(index).getPlayerName() + " " + DROP_DOWN_STRING + " " + CURRENT_TURN;
            }
            this.playerButtons.get(index).setText(s);
        }
        else{
            String s;
            s = players.get(index).getPlayerName() + " " + UP_STRING;
            if (text[text.length - 1].equals(DROP_DOWN_STRING) || (text[text.length-1].equals(CURRENT_TURN) && text[text.length-2].equals(DROP_DOWN_STRING))){
                s = players.get(index).getPlayerName() + " " + DROP_DOWN_STRING;
            }
            this.playerButtons.get(index).setText(s);
        }
        this.playerDisplays.get(index).revalidate();
    }
}

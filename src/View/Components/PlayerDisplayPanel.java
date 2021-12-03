package View.Components;
import Model.BoardElements.Location;
import Model.GamePlayer.Player;
import Model.BoardElements.Property;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Player displays side panel which always tracks the state of each player for every view
 * @author Tony Massaad
 */
public class PlayerDisplayPanel extends JPanel {
    private final static String CURRENT_TURN = "(Playing)";
    private final static String DROP_DOWN_STRING = "+";
    private final static String UP_STRING = "-";
    private final static String OUT_STRING = "(out)";
    private final ArrayList<JButton> playerButtons;
    private final ArrayList<JPanel> playerDisplays;

    /**
     * default constructor for PlayerDisplayPanel
     */
    public PlayerDisplayPanel(){
        this.playerButtons = new ArrayList<>();
        this.playerDisplays = new ArrayList<>();
        this.setBackground(new Color(224, 225, 224));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * will add the new player buttons to view location, money, properties
     * @param p Player current playing
     * @param index Integer
     */
    public void addNewPlayerViewButton(Player p, int index){
        JPanel playerDisplay = new JPanel();
        playerDisplay.setLayout(new BoxLayout(playerDisplay, BoxLayout.Y_AXIS));
        JButton playerButton;
        if (index == 0)
            playerButton = new JButton(p.getPlayerName() + " " + DROP_DOWN_STRING + " " + CURRENT_TURN);
        else
            playerButton = new JButton(p.getPlayerName() + " " + DROP_DOWN_STRING);
        playerButton.addActionListener(e -> {
            String[] text = playerButton.getText().split(" ");
            int length = text.length;
            if (text[length-1].equals(DROP_DOWN_STRING) || (text[length-1].equals(CURRENT_TURN) && text[length-2].equals(DROP_DOWN_STRING))){
                this.addToPanel(playerDisplay, p);
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
    }

    /**
     * removes the player view
     * @param i Integer, index of player
     * @param p Player being removed
     */
    public void removePlayerView(int i, Player p){
        JPanel panel = this.playerDisplays.get(i);
        JButton button = this.playerButtons.get(i);
        button.setText(p.getPlayerName() + " " + OUT_STRING);
        panel.removeAll();
        button.setEnabled(false);
        panel.add(button);
    }

    /**
     * updates players display to get updated infomation on money,location, properties
     * @param index Integer
     * @param p Player the player being updated
     */
    public void updatePlayerDisplay(int index, Player p) {
        JPanel panel = this.playerDisplays.get(index);
        JButton button = this.playerButtons.get(index);
        String[] text = button.getText().split(" ");
        int length = text.length;
        panel.removeAll();
        if (text[length-1].equals(UP_STRING) || (text[length-1].equals(CURRENT_TURN) && text[length-2].equals(UP_STRING))){
            panel.add(button);
            this.addToPanel(panel, p);
        }
        else{
            panel.add(button);
        }
    }

    /**
     * updates the current turn that is being played
     * @param currentTurn Integer of the current turn
     * @param index Integer
     * @param p Player
     */
    public void updateCurrentTurn(int currentTurn, int index, Player p){
        String[] text = this.playerButtons.get(index).getText().split(" ");
        if (text[text.length-1].equals(OUT_STRING)){
            this.playerButtons.get(index).setText(p.getPlayerName() + " " + OUT_STRING);
        }
        else{
            if (index == currentTurn){
                String s;
                s = p.getPlayerName() + " " + UP_STRING + " " + CURRENT_TURN;
                if (text[text.length - 1].equals(DROP_DOWN_STRING) || (text[text.length-1].equals(CURRENT_TURN) && text[text.length-2].equals(DROP_DOWN_STRING))){
                    s = p.getPlayerName() + " " + DROP_DOWN_STRING + " " + CURRENT_TURN;
                }
                this.playerButtons.get(index).setText(s);
            }
            else{
                String s;
                s = p.getPlayerName() + " " + UP_STRING;
                if (text[text.length - 1].equals(DROP_DOWN_STRING) || (text[text.length-1].equals(CURRENT_TURN) && text[text.length-2].equals(DROP_DOWN_STRING))){
                    s = p.getPlayerName() + " " + DROP_DOWN_STRING;
                }
                this.playerButtons.get(index).setText(s);
            }
        }
    }

    /**
     * Adds player contents to specific panel
     * @param panel JPanel, the panel
     * @param p Player, the player
     */
    private void addToPanel(JPanel panel, Player p){
        JLabel moneyLabel = new JLabel("Money: $" + p.getMoneyAmount());
        JLabel locationLabel = new JLabel("Current Location: " + p.getCurrLocation());
        JLabel propertyLabel = new JLabel("Properties:");
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 11));
        locationLabel.setFont(new Font("Arial", Font.BOLD, 11));
        propertyLabel.setFont(new Font("Arial", Font.BOLD, 11));
        panel.add(locationLabel);
        panel.add(moneyLabel);
        panel.add(propertyLabel);
        for (int i = 0; i < p.getNumOfProperties(); i++) {
            JLabel location;
            Location place = p.getPropertyByIndex(i);
            if (p.getPropertyByIndex(i) instanceof Property) {
                location = new JLabel(place.getName() + " [" + ((Property) place).getNumOfHouses() + " houses, rent: $" + ((Property) place).getRent() + "]");
            } else {
                location = new JLabel(place.getName());
            }
            location.setFont(new Font("Arial", Font.BOLD, 11));
            panel.add(location);
        }
    }
}

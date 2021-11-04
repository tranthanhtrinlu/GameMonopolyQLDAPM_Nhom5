package View;

import Events.BoardEvent;
import Listener.BoardView;
import Model.BoardModel;
import Model.Location;
import Model.Player;
import View.Components.GameDisplayPanel;
import View.Components.PlayerDisplayPanel;
import View.Controllers.StartGameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BoardGUI extends JFrame implements ActionListener, BoardView {
    private final static int GAME_WIDTH = 985;
    private final static int GAME_HEIGHT = 807;


    private GameDisplayPanel gamePanel;
    private PlayerDisplayPanel sidePanel;
    private JPanel gameControlPanel;

    protected BoardModel model;
    private List<Player> gamePlayers;
    private int currentTurn;
    private boolean pass, purchasesProperty, purchaseHouses;
    private JButton turnPass, quit, roll, payOutOfJail, rollDouble, purchaseEstateHouses, sellHouses;

    public BoardGUI(){
        super("Monopoly");
        this.gamePanel = new GameDisplayPanel();
        this.sidePanel = new PlayerDisplayPanel();
        this.currentTurn = 0;
        this.gamePlayers = new ArrayList<>();
        this.model = new BoardModel();
        this.turnPass = new JButton("Pass");
        this.quit = new JButton("Quit");
        this.roll = new JButton("Roll");
        this.payOutOfJail = new JButton("Pay out of jail");
        this.rollDouble = new JButton("Roll Double");
        this.purchaseEstateHouses = new JButton("Purchase Houses");
        this.sellHouses = new JButton("Sell Houses");
        this.gameControlPanel = new JPanel();
        this.gameControlPanel.setLayout(new BoxLayout(this.gameControlPanel, BoxLayout.Y_AXIS));
        this.gameControlPanel.setBounds(520,315, 150,200);
        this.gameControlPanel.setBackground(new Color(255,255,255));
        this.setLayout(null);
        this.sidePanel.setBounds(0,0,200, GAME_HEIGHT);
        this.gamePanel.setBounds(200,0,GAME_WIDTH,GAME_HEIGHT);
        this.add(this.gameControlPanel);
        this.add(this.gamePanel);
        this.add(this.sidePanel);

        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        StartGameController start = new StartGameController();
        int num = start.getNumOfPlayers(this);
        ArrayList<String> names = start.getNameOfPlayers(num, this);
        for (int i = 0; i < num; i++){
            this.gamePlayers.add(new Player(names.get(i)));
            this.sidePanel.addNewPlayerViewButton(this.gamePlayers.get(i));
        }
        this.updateChoicePanel();
    }

    /**
     * Overridden method to handle player movement in general, depending on where the player is located.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handlePlayerMovement(BoardEvent e){
        Location place = e.boardElement(this.gamePlayers.get(this.currentTurn).getPosition());
        Player p = this.gamePlayers.get(this.currentTurn);
        this.updateRoll(e.getRoll1(), e.getRoll2());
        

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.roll){
            this.model.RollButtonChoice();
            this.updateChoicePanel();
            this.sidePanel.updatePlayerDisplay(this.currentTurn, this.gamePlayers.get(this.currentTurn));
        }
        else if (e.getSource() == this.quit){
            //this.model.quitButtonChoice();
            this.updateChoicePanel();
            this.sidePanel.updatePlayerDisplay(this.currentTurn, this.gamePlayers.get(this.currentTurn));
        }
        else if (e.getSource() == this.turnPass){
            //this.model.turnPassButtonChoice();
            this.updateChoicePanel();
            this.sidePanel.updatePlayerDisplay(this.currentTurn, this.gamePlayers.get(this.currentTurn));
        }
        else if (e.getSource() == this.payOutOfJail){

        }
        else if (e.getSource() == this.rollDouble){

        }
        else if (e.getSource() == purchaseEstateHouses){

        }
        else if (e.getSource() == sellHouses){
            // to be implemented
        }
    }

    private void updateChoicePanel() {
        Player p = this.gamePlayers.get(currentTurn);
        this.gameControlPanel.removeAll();
        boolean inJail = p.getInJail();
        boolean hasProperty = p.numberOfEstateProperties() != 0;
        if (!inJail) {
            if (hasProperty) {
                this.gameControlPanel.add(this.roll);
                this.gameControlPanel.add(this.purchaseEstateHouses);
                this.gameControlPanel.add(this.turnPass);
                this.gameControlPanel.add(this.quit);
            }
            else {
                this.gameControlPanel.add(this.roll);
                this.gameControlPanel.add(this.turnPass);
                this.gameControlPanel.add(this.quit);
            }
        }
        else{
            this.gameControlPanel.add(this.payOutOfJail);
            this.gameControlPanel.add(this.rollDouble);
            this.gameControlPanel.add(this.quit);
        }
        this.gameControlPanel.revalidate();
    }

    public static void main(String[] args) {
        new BoardGUI();
    }

}

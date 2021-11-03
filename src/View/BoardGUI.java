package View;

import Model.BoardModel;
import Model.Player;
import View.Components.GameDisplayPanel;
import View.Components.PlayerDisplayPanel;
import View.Controllers.StartGameController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BoardGUI extends JFrame{
    private final static int GAME_WIDTH = 985;
    private final static int GAME_HEIGHT = 807;


    private GameDisplayPanel gamePanel;
    private PlayerDisplayPanel sidePanel;

    protected BoardModel model;
    private List<Player> gamePlayers;
    private int currentTurn;
    private boolean pass, purchasesProperty, purchaseHouses;

    public BoardGUI(){
        super("Monopoly");
        this.gamePanel = new GameDisplayPanel();
        this.sidePanel = new PlayerDisplayPanel();
        this.currentTurn = 0;
        this.gamePlayers = new ArrayList<>();
        this.model = new BoardModel();
        this.setLayout(null);
        this.sidePanel.setBounds(0,0,200, GAME_HEIGHT);
        this.gamePanel.setBounds(200,0,GAME_WIDTH,GAME_HEIGHT);
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

    }

    public static void main(String[] args) {
        new BoardGUI();
    }

}

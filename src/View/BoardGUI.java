package View;
import Events.*;
import Listener.BoardView;
import Model.BoardModel;
import Model.Player;
import View.Components.GameDisplayPanel;
import View.Components.PlayerDisplayPanel;
import View.Controllers.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * BoardGUI Class, also known as the boardView class
 * @author Tony Massaad
 */
public class BoardGUI extends JFrame implements BoardView{
    public final static int GAME_WIDTH = 985;
    public final static int GAME_HEIGHT = 807;
    private final static int[] DICE_DIM = new int[]{96, 96};

    private final GameDisplayPanel gamePanel;
    private final PlayerDisplayPanel sidePanel;
    private final JPanel gameControlPanel;

    private final JButton turnPass, quit, roll, payOutOfJail, rollDouble, purchaseEstateHouses, sellHouses;
    private final ArrayList<Image> diceImages;
    private final JLabel dice1, dice2;

    public BoardGUI(){
        super("Monopoly");
        this.gamePanel = new GameDisplayPanel();
        this.sidePanel = new PlayerDisplayPanel();
        BoardModel model = new BoardModel();
        this.turnPass = new JButton("Pass");
        this.quit = new JButton("Quit");
        this.roll = new JButton("Roll");
        this.payOutOfJail = new JButton("Pay out of jail");
        this.rollDouble = new JButton("Roll Double");
        this.purchaseEstateHouses = new JButton("Purchase Houses");
        this.sellHouses = new JButton("Sell Houses");

        BoardController controller = new BoardController(model);
        this.roll.setActionCommand(BoardModel.PlayerChoice.ROLL.getChoice() + " ");
        this.roll.addActionListener(controller);
        this.quit.setActionCommand(BoardModel.PlayerChoice.QUIT.getChoice() + " ");
        this.quit.addActionListener(controller);
        this.turnPass.setActionCommand(BoardModel.PlayerChoice.PASS.getChoice() + " ");
        this.turnPass.addActionListener(controller);
        this.payOutOfJail.setActionCommand(BoardModel.PlayerChoice.PAY_OUT.getChoice() + " ");
        this.payOutOfJail.addActionListener(controller);
        this.rollDouble.setActionCommand(BoardModel.PlayerChoice.ROLL_OUT.getChoice() + " ");
        this.rollDouble.addActionListener(controller);
        this.purchaseEstateHouses.setActionCommand(BoardModel.PlayerChoice.BUY_HOUSE.getChoice() + " ");
        this.purchaseEstateHouses.addActionListener(controller);
        this.sellHouses.setActionCommand(BoardModel.PlayerChoice.SELL_HOUSE.getChoice() + " ");
        this.sellHouses.addActionListener(controller);

        this.gameControlPanel = new JPanel();
        this.gameControlPanel.setLayout(new GridLayout(10,1));
        this.gameControlPanel.setBounds(520,315, 150,200);
        this.gameControlPanel.setBackground(new Color(255,255,255));
        this.setLayout(null);
        this.sidePanel.setBounds(0,0,200, GAME_HEIGHT);
        this.gamePanel.setBounds(200,0,GAME_WIDTH,GAME_HEIGHT);
        this.diceImages = new ArrayList<>(){{
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice1.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice2.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice3.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice4.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice5.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
            add(new ImageIcon(this.getClass().getResource("/DiceImages/dice6.png")).getImage().getScaledInstance(DICE_DIM[0], DICE_DIM[1], Image.SCALE_SMOOTH));
        }};
        this.dice1 = new JLabel(new ImageIcon(this.diceImages.get(0)));
        this.dice2 = new JLabel(new ImageIcon(this.diceImages.get(0)));
        this.dice1.setBounds(450,150,96,96);
        this.dice2.setBounds(650,150,96,96);
        this.add(this.dice1);
        this.add(this.dice2);
        this.add(this.gameControlPanel);
        this.add(this.gamePanel);
        JScrollPane scroll = new JScrollPane(this.sidePanel);
        scroll.setBounds(0,0,200, GAME_HEIGHT);
        this.add(scroll);
        model.addView(this);
        model.addViewToListener(this);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        StartGameController start = new StartGameController();
        int numberOfPlayers = start.getNumOfPlayers(this);
        model.setNumberOfPlayers(numberOfPlayers);
        ArrayList<String> names = start.getNameOfPlayers(numberOfPlayers, this);
        for (int i = 0; i < numberOfPlayers; i++){
            model.addGamePlayers(new Player(names.get(i)));
            this.sidePanel.addNewPlayerViewButton(model.getPlayersByIndex(i), i);
            this.gamePanel.addInitialPlayers(i);
        }
        this.updateChoicePanel(model.getPlayersByIndex(0));
    }

    //****BEGINNING OF PROPERTY FUNCTIONS**//

    /**
     * Overridden method to handle a property with no owner. A player can either pass on or buy a property.
     * If bought, give player choices to purchase houses.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public boolean propertyNoOwner(PropertyEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getProperty().getName(), e.getProperty().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Overridden method to handle a property already owned by a player.
     * Can either buy houses/hotels or pass.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyOwned(PropertyEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getProperty().getName() + ", Property they own. Moving to the next player");
    }

    /**
     * Overridden method to handle a property owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.PropertyEvent e.
     */
    @Override
    public void propertyRent(PropertyEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getProperty().getName() + " Owned by " + e.getProperty().getOwner().getPlayerName()
                + " and rent is $" + e.getCost() + "\nThey pay now");
    }

    /**
     * announcement that the player cannot buy a property
     */
    @Override
    public void announceCannotBuy(PropertyEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getProperty().getName() + " but does not have enough Money, moving to the next player");
    }

    /**
     * announcement that the player purchased the property
     * @param e PropertyEvent, the property event
     */
    @Override
    public void announcePurchaseProperty(PropertyEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getProperty().getName());
    }


    // ** ENDING OF PROPERTY IMPLEMENTATION


    //** BEGINNING OF RAIL ROAD IMPLEMENTATION **//

    /**
     * Overridden method to handle a railroad with no owner. A player can either pass on or buy a property.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public boolean railRoadNoOwner(RailRoadEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getRailRoad().getName(), e.getRailRoad().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Method to handle a railroad that a player who owns it lands on.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadOwned(RailRoadEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getRailRoad().getName() + ", Property they own. Moving to the next player");
    }

    /**
     * Overridden method to handle a railroad owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.RailRoadEvent e.
     */
    @Override
    public void railRoadRent(RailRoadEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getRailRoad().getName() + " Owned by " + e.getRailRoad().getOwner().getPlayerName()
                + " and rent is $" + e.getRentCost() + "\nThey pay now");
    }


    @Override
    public void announceCannotBuyRailRoad(RailRoadEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getRailRoad().getName() + " but does not have enough Money, moving to the next player");
    }


    @Override
    public void announcePurchaseRailRoad(RailRoadEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getRailRoad().getName());
    }

    // **END OF RAIL ROAD IMPLEMENTATION** //

    //** BEGINNING OF UTILITY IMPLEMENTATION **//

    /**
     * Overridden method to handle a utility with no owner. A player can either pass on or buy a property.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public boolean UtilityNoOwner(UtilityEvent e) {
        LocationController control = new LocationController();
        int result = control.LocationNoOwnerController(this, e.getUtility().getName(), e.getUtility().getCost());
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Method to handle a utility that a player who owns it lands on.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityOwned(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "player:  " + e.getPlayer().getPlayerName() + " landed on " + e.getUtility().getName() + " a Utility they own. Moving to the next player");
    }

    /**
     * Overridden method to handle a utility owned by a player not currently on their turn.
     * If the player does not have enough money to pay rent, they are bankrupted.
     * @param e A Events.UtilityEvent e.
     */
    @Override
    public void UtilityPay(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " landed on " + e.getUtility().getName() + " Owned by " + e.getUtility().getOwner().getPlayerName() + ".\n" +
                "Number of utilities owned by owner is " + e.getUtility().getOwner().getNumOfUtilities() + ". so payment (dice roll * (10 if 2 utilities else 4)) is $" + e.getPayment());
    }

    @Override
    public void announceCannotBuyUtility(UtilityEvent e){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " tried to buy " + e.getUtility().getName() + " but does not have enough Money, moving to the next player");
    }

    @Override
    public void announcePurchaseOfUtility(UtilityEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has purchased " + e.getUtility().getName());
    }


    // ** END OF UTILITY IMPLEMENTATION **

    /**
     * Overridden method for handling free parking.
     * If a player lands here they get the money in the "center" of the board.
     * @param e A Events.Tax_FreeParkingEvent event.
     */
    @Override
    public void FreeParking(FreeParkingEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on free parking, they receive $" + e.getCenterMoney());
    }

    /**
     * Overridden method for handling the payment of tax.
     * If player runs out of money, they will go bankrupt.
     * @param e A Events.Tax_FreeParkingEvent event.
     */
    @Override
    public void payTax(TaxEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on " + e.getLocation().getName() + ", they lose $" + e.getLocation().getCost() + " which goes into the center money");
    }

    /**
     * Overridden method for handling "Just Visiting".
     * @param e A Events.LandOnJailEvent event.
     */
    @Override
    public void visiting(LandOnJailEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed " + e.getLandOnJail().getName() + " - Just Visiting");
    }


    /**
     * Overridden method for sending a player to jail.
     * @param e Events.GoToJailEvent e.
     */
    @Override
    public void SendPlayerToJail(GoToJailEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " landed on " + e.getGoToJail().getName() + ", they go to jail.\nMoving to the next player");
        this.handlePlayerPieceMovement(e.getCurrentTurn(), e.getOldPos(), e.getNewPos());
    }


    /**
     * Overridden method for handling a free pass. These replace the Chance + Community Chest as placeholders on the board.
     * @param e Events.FreePassEvent event.
     */
    @Override
    public void FreePass(FreePassEvent e) {
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " Landed on a free pass, moving to the next player");
    }


    /* HANDLES */


    @Override
    public void handleAnnounceBankruptedPlayer(Player p){
        ConfirmMessageController messageController = new ConfirmMessageController();
        messageController.sendMessage(this, "Player: " + p.getPlayerName() + " has no more money. Removing player from game\nTheir properties are now back in estate!");
    }


    /**
     * Overridden method for handling the player quit.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void handlePlayerQuit(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " has quit the game. Removing player from game\nTheir properties are now back in estate!");
    }

    /**
     * Overridden method for handling the announcement of a player passing the turn.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void announcePlayerPass(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player " + e.getPlayer().getPlayerName() + " passed the turn. Moving to the next player.");
    }

    /**
     * Overridden boolean method for handling the payment in jail.
     * @param e A Events.BoardEvent e.
     */
    @Override
    public void payJail(boolean payed, BoardEvent e){
        ConfirmMessageController controller = new ConfirmMessageController();
        if (payed){
            controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " payed out of jail.");
        }
        else{
            controller.sendMessage(this, "Player: " + e.getPlayer().getPlayerName() + " attempted to pay out of jail but could not due to financial issues.");
        }
    }

    /**
     * Button handle for rolling doubles to get out of jail
     * @param e BoardEvent, the BoardEvent
     */
    @Override
    public void handleRollingDoubles(BoardEvent e){
        ConfirmMessageController controller = new ConfirmMessageController();
        if (e.getDoubles()){
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " rolled a double! they are now Just Visiting Jail");
            return;
        }
        if (e.getPlayer().getTurnsInJail() != 0){
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " attempted to roll out of jail. They failed and now have " + e.getPlayer().getTurnsInJail() + " left in jail");
        }
        else{
            controller.sendMessage(this, e.getPlayer().getPlayerName() + " are now out of turns in jail, they have to pay the $50");
        }
    }

    @Override
    public void announceDecisionToPurchaseHouses(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player " + e.getPlayer().getPlayerName() + " has decided to purchase houses.");

    }

    @Override
    public void handlePlayerChoiceToPurchaseHouses(BoardEvent e) {
        BuyHouseController controller = new BuyHouseController();
        controller.numberHouses(this);
        controller.propertiesHouses(this, e.getPlayer());
    }

    @Override
    public void announceDecisionToSellHouses(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, "Player " + e.getPlayer().getPlayerName() + " has decided to sell houses.");
    }

    @Override
    public void handlePlayerChoiceToSellHouses(BoardEvent e) {
        SellHouseController controller = new SellHouseController();
        controller.numberHouses(this);
        controller.propertiesHouses(this, e.getPlayer());
    }

    /**
     *
     * @param b boolean, true to enable otherwise false
     */
    @Override
    public void buttonEnableCondition(boolean b){
        this.turnPass.setEnabled(b);
        this.quit.setEnabled(b);
        this.roll.setEnabled(b);
        this.payOutOfJail.setEnabled(b);
        this.rollDouble.setEnabled(b);
        this.purchaseEstateHouses.setEnabled(b);
        this.sellHouses.setEnabled(b);
        this.gameControlPanel.revalidate();
    }

    /**
     * Overridden method that announces when a player has reached GO!.
     */
    @Override
    public void announceReachingGo(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " received $" + BoardModel.GO_MONEY + " for reaching GO");
    }


    /**
     * handle the next turn of the player according to the view
     * @param e BoardEvent, the BoardEvent
     */
    @Override
    public void handleNextTurnDisplay(BoardEvent e, int updatedTurn){
        for (int i = 0; i<e.getNumOfPlayers(); i++){
            if (e.getPlayerByIndex(i).getOut())
                continue;
            this.sidePanel.updateCurrentTurn(updatedTurn, i, e.getPlayerByIndex(i));
        }
    }

    /**
     * handle the update of the side panel for each player in a view
     * @param e BoardEvent, the board event
     */
    @Override
    public void handleUpdateSidePanelDisplay(BoardEvent e){
        for (int i = 0; i<e.getNumOfPlayers(); i++) {
            if (e.getPlayerByIndex(i).getOut())
                continue;
            this.sidePanel.updatePlayerDisplay(i, e.getPlayerByIndex(i));
        }
    }

    /**
     * handle the piece display on the board for each player in a view
     * @param currentTurn Integer, the current turn
     * @param oldPos Integer, the old position
     * @param position Integer, the new position
     */
    @Override
    public void handlePlayerPieceMovement(int currentTurn, int oldPos, int position) {
        this.gamePanel.movePieceImage(currentTurn, oldPos, position);

    }

    /**
     * Handle the announcement of the winner if there is a winner
     * if winner, send message and end game.
     */
    @Override
    public void handleAnnounceWinner(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " wins the game\nThank you Playing\nExiting Program");
        System.exit(0);
    }

    /**
     * announce that the current player rolled a double to every view
     */
    @Override
    public void handleAnnounceRollingAgain(BoardEvent e) {
        ConfirmMessageController controller = new ConfirmMessageController();
        controller.sendMessage(this, e.getPlayer().getPlayerName() + " Rolled a double. They get to roll again!");
    }

    /**
     * update the dice rolls on the board
     * @param roll1 Integer, the first roll
     * @param roll2 Integer, the second roll
     */
    @Override
    public void handleUpdateRoll(int roll1, int roll2) {
        dice1.setIcon(new ImageIcon(diceImages.get(roll1-1)));
        dice2.setIcon(new ImageIcon(diceImages.get(roll2-1)));
    }

    @Override
    public void handleRemoveOfPlayerPiece(BoardEvent e){
        this.gamePanel.removePieceFromBoard(e.getTurn(),e.getPlayer().getPosition());
    }


    @Override
    public void handleRemoveOfPlayerView(BoardEvent e){
        this.sidePanel.removePlayerView(e.getTurn(), e.getPlayer());
    }


    /**
     * Update panel according to the views
     * @param player
     */
    @Override
    public void updateChoicePanel(Player player) {
        this.gameControlPanel.removeAll();
        boolean inJail = player.getInJail();
        boolean canPurchase = player.numberOfEstateProperties() != 0;
        boolean canSell = player.numberOfEstatePropertiesWithHouses() != 0;

        if (!inJail) {
            if (canPurchase && canSell) {
                this.gameControlPanel.add(this.roll);
                this.gameControlPanel.add(this.purchaseEstateHouses);
                this.gameControlPanel.add(this.sellHouses);
                this.gameControlPanel.add(this.turnPass);
                this.gameControlPanel.add(this.quit);
            }
            else if (canPurchase){
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
        } else {
            this.gameControlPanel.add(this.payOutOfJail);
            this.gameControlPanel.add(this.rollDouble);
            this.gameControlPanel.add(this.quit);
        }
        this.gameControlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.gameControlPanel.revalidate();

    }

    public static void main(String[] args) {
        new BoardGUI();
    }

}

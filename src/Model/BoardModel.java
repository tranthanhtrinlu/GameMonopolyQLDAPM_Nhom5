package Model;
import Events.BoardEvent;
import Listener.BoardView;
import Model.BoardElements.*;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Tony Massaad
 * Class MVC.BoardModel that acts as the model for the Monopoly game. Essentially sets up the game and board as a whole.
 */
public class BoardModel {
    public static final int MAX_PLAYERS = 5;
    public static final int SIZE_OF_BOARD = 39; // 0-39 inclusive
    public static final int GO_MONEY = 200;
    public static final int JAIL_POSITION = 10; // 11 - 1
    public static final int TOTAL_UTILITIES = 2;
    private static final int ROLLING_DICE_DELAY = 10;
    public static int centerMoney = 0;

    private final List<Location> board;
    private final List<BoardView> views;
    private int currentTurn;
    private int numberOfPlayers;
    private Status status;
    private int roll1;
    private int roll2;
    private final ArrayList<Player> gamePlayers;
    private boolean payed;


    /**
     * Color constants for the Board
     */
    public enum Color{
        BROWN, LIGHTBLUE, PURPLE, ORANGE, RED, YELLOW, GREEN, DARKBLUE, NONE
    }

    /**
     * Status Constant of the Game.
     */
    public enum Status{
        FINISHED, UNFINISHED
    }

    /**
     * Constants for each board element in board
     */
    public enum BoardElements{
        GO(new FreePass("Go", 0)),
        MEDITERRANEAN(new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250,Color.BROWN, 2)),
        FREE_PASS( new FreePass("Free Pass",0)),
        BALTIC(new Property("Baltic Avenue", 60,50,4,20,60,180,320,450, Color.BROWN, 2)),
        INCOME(new Tax("Income Tax", 200)),
        READING_R(new RailRoad("Reading Railroad", 200)),
        ORIENTAL(new Property("Oriental Avenue", 100, 50,6,30,90,270,400,550, Color.LIGHTBLUE, 3)),
        FREE_PASS2( new FreePass("Free Pass",0)),
        VERMONT(new Property("Vermont Avenue", 100, 50,6,30,90,270,400,550, Color.LIGHTBLUE, 3)),
        CONNECTICUT(new Property("Connecticut Avenue", 120, 50,8,40,100,300,450,600, Color.LIGHTBLUE, 3)),
        LAND_ON_JAIL(new LandOnJail("In Jail - Just Visiting", 0)),
        CHARLES(new Property("ST.Charles Place", 140,100,10,50,150,450,625,750, Color.PURPLE, 3)),
        ELECTRIC(new Utility("Electric Company", 150)),
        STATES(new Property("States Avenue",140,100,10,50,150,450,625,750,Color.PURPLE, 3)),
        VIRGINIA(new Property("Virginia Avenue", 160,100,12,60,180,500,700,900, Color.PURPLE, 3)),
        PENNSYLVANIA_R(new RailRoad("Pennsylvania Railroad", 200)),
        JAMES(new Property("ST. James Place", 180, 100,14,70,200,550,750,950, Color.ORANGE, 3)),
        FREE_PASS3( new FreePass("Free Pass",0)),
        TENNESSEE(new Property("Tennessee Avenue", 180,100,14,70,200,550,750,950, Color.ORANGE, 3)),
        NEW_YORK(new Property("New York Avenue", 200,100,16,80,220,600,800,1000, Color.ORANGE, 3)),
        FREE_PARKING(new FreeParking(0, "Free Parking")),
        KENTUCKY(new Property("Kentucky Avenue",220,150,18,90,250,700,875,1050, Color.RED,3)),
        FREE_PASS4( new FreePass("Free Pass",0)),
        INDIANA(new Property("Indiana Avenue", 220,150,18,90,250,700,875,1050, Color.RED, 3)),
        ILLINOIS(new Property("Illinois Avenue", 240,150,20,100,300,750,925,1110, Color.RED, 3)),
        BO_R(new RailRoad("B&O Railroad", 200)),
        ATLANTIC(new Property("Atlantic Avenue", 260,150,22,110,330,800,975,1150,Color.YELLOW, 3)),
        VENTNOR(new Property("Ventnor Avenue", 260,150,22,110,330,800,975,1150, Color.YELLOW, 3)),
        WATER(new Utility("Water Works", 150)),
        MARVIN(new Property("Marvin Gardens", 280,150,24,120,360,850,1025,1200, Color.YELLOW, 3)),
        GO_TO_JAIL(new GoToJail("In Jail", 0)),
        PACIFIC(new Property("Pacific Avenue", 300,200,26,130,390,900,1100,1275, Color.GREEN, 3)),
        CAROLINA(new Property("North Carolina Avenue", 300,200,26,130,390,900,1100,1275, Color.GREEN, 3)),
        FREE_PASS5( new FreePass("Free Pass",0)),
        PENNSYLVANIA(new Property("Pennsylvania Avenue", 320,200,28,150,450,1000,1200,1400, Color.GREEN, 3)),
        SHORT_R(new RailRoad("ShortLine Railroad", 200)),
        FREE_PASS6( new FreePass("Free Pass",0)),
        PARK(new Property("Park Place", 350,200,35,175,500,1100,1300,1500,Color.DARKBLUE, 2)),
        LUXURY(new Tax("Luxury Tax", 100)),
        BOARDWALK(new Property("Boardwalk", 400,200,50,200,600,1400,1700,2000, Color.DARKBLUE, 2));

        private final Location piece;
        /**
         * Constructor for Each boardElements
         * @param piece Location, the location of the board element
         */
        BoardElements(Location piece){
            this.piece = piece;
        }

        /**
         * Getter method to get the location piece
         * @return Location, the location piece
         */
        public Location getPiece() {
            return this.piece;
        }
    }

    /**
     * Constants for handling player choice throughout the game
     */
    public enum PlayerChoice{
        ROLL(1), QUIT(2), PASS(3), PAY_OUT(4), ROLL_OUT(5), BUY_HOUSE(6), SELL_HOUSE(7);

        private final int choice;

        /**
         * Constructor for player choice
         * @param choice Integer, the choice
         */
        PlayerChoice(int choice){
            this.choice = choice;
        }

        /**
         * getter method to get the choice
         * @return Integer, the choice
         */
        public int getChoice() {
            return choice;
        }
    }

    /**
     * constants for what announcements the view should handle
     */
    public enum nextPlayerTurnAnnouncements{
        ROLL_AGAIN, ROLL_OUT, PAY_OUT, PASS, NONE
    }

    /**
     * Default constructor for the BoardModel.
     */
    public BoardModel(){
         this(0,0,0);
    }

    public BoardModel(int currentTurn, int roll1, int roll2){
        this.gamePlayers = new ArrayList<>();
        this.board = new ArrayList<>();
        this.views = new ArrayList<>();
        this.currentTurn = currentTurn;
        this.initializeBoard();
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.status = Status.UNFINISHED;
        this.payed = false;
    }

    /**
     * gets how much money is in the center
     * @return Integer centerMoney
     */
    public static int getCenterMoney() {
        return BoardModel.centerMoney;
    }

    /**
     * will set centerMoney
     * @param centerMoney Integer money in center
     */
    public static void setCenterMoney(int centerMoney) {
        BoardModel.centerMoney = centerMoney;
    }

    /**
     * used to add money to the center
     * @param add Integer added
     */
    public static void addToCenterMoney(int add){
        BoardModel.centerMoney += add;
    }


    /**
     * add players to the game
     * @param player Player, the player
     */
    public void addGamePlayers(Player player) {
        this.gamePlayers.add(player);
    }

    /**
     * Get the player by index
     * @param i Integer, the index
     * @return Player, the player
     */
    public Player getPlayersByIndex(int i) {
        return this.gamePlayers.get(i);
    }

    /**
     * set the number of players in the game
     * @param num Integer, the number
     */
    public void setNumberOfPlayers(int num){
        this.numberOfPlayers = num;
    }

    /**
     * Getter for the current turn.
     * @return Integer currentTurn
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Boolean method for determining whether a player has rolled doubles.
     * @return True if doubles are rolled, false otherwise.
     */
    private boolean rollDiceOfTwo(){
        Random r = new Random();
        this.roll1 = r.nextInt(6) +  1;
        this.roll2 = r.nextInt(6) + 1;
        return this.roll1 == this.roll2;
    }

    /**
     * Method for adding a Listener.BoardView object to the ArrayList of BoardViews.
     * @param view A Listener.BoardView object view.
     */
    public void addView(BoardView view){
        this.views.add(view);
    }

    /**
     * Method for initializing the board. Adds all necessary elements, including properties, railroads and utilities.
     */
    private void initializeBoard(){
        for (BoardElements elements : BoardElements.values()){
            this.board.add(elements.getPiece());
        }
    }

    /**
     * Method that loops through the board list and adds all the listeners to each.
     * @param view A Listener.BoardView view.
     */
    public void addViewToListener(BoardView view){
        for (Location location : this.board){
            location.addListener(view);
        }
    }

    /**
     * method for handling the next turn of the player.
     */
    public void nextTurn() {
        this.currentTurn++;
        while (true){
            if (this.currentTurn == this.gamePlayers.size())
                this.currentTurn = 0;

            if (this.gamePlayers.get(this.currentTurn).getOut()){
                this.currentTurn++;
                continue;
            }
            break;
        }
    }

    /**
     * Overridden boolean method for updating the game players if one loses the game or quits.
     * @return True if the game players is updated, false otherwise.
     */
    public boolean checkBankrupt() {
        Player p = this.gamePlayers.get(this.currentTurn);
        if (p.getMoneyAmount() == 0){
            this.removePlayer();
            return true;
        }
        return false;
    }

    /**
     * Remove the current player of the game
     * set the player out attribute to true
     * bankrupt the player and move to the next turn
     * if the status checks the next player is the last, announce winner
     * otherwise reduce the number of players by 1
     */
    private void removePlayer(){
        Player p = this.gamePlayers.get(this.currentTurn);
        p.setOut(true);
        p.bankrupted();
        nextTurn();
        this.numberOfPlayers -= 1;
        updateStatus();
    }

    /**
     * get the current status
     * @return Status, the status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * check if there is a winner
     * if winner, change the status to finished
     * otherwise not
     */
    public void updateStatus(){
        if (this.numberOfPlayers == 1){
            this.status = Status.FINISHED;
        }
    }

    /**
     * If the current player turn is the AI, AI rolls if they are not in jail, otherwise try to roll double
     * @return boolean, true if AI otherwise false
     */
    private boolean playAI(){
        Player p = this.gamePlayers.get(this.currentTurn);
        if(p instanceof AI){
            if (!p.getInJail())
                playCurrPlayerTurn(PlayerChoice.ROLL.getChoice());
            else
                playCurrPlayerTurn(PlayerChoice.ROLL_OUT.getChoice());
            return true;
        }
        return false;
    }

    /**
     * handle what happens to the player view if it's the AI's turn or not
     * if it is the AI's turn, the play buttons are disabled, otherwise enabled and updated
     */
    private void handleIfAITurn(){
        if(!playAI()){
            for (BoardView view : views){
                view.buttonEnableCondition(true);
                view.updateChoicePanel(gamePlayers.get(currentTurn));
            }
        }
    }

    /**
     * Get the type of announcement to display to the view depending on what happens
     * @param view BoardView, the current view
     * @param announcement nextPlayerTurnAnnouncements, the announcement handler
     * @param e BoardEvent, the events occurring in the BoardModel
     */
    private void getTypeOfViewAnnouncement(BoardView view, nextPlayerTurnAnnouncements announcement, BoardEvent e){
        switch(announcement){
            case ROLL_AGAIN:
                view.handleAnnounceRollingAgain(e);
                break;
            case PASS:
                view.announcePlayerPass(e);
                break;
            case ROLL_OUT:
                view.handleResultsOfRollingInJail(e);
                break;
            case PAY_OUT:
                view.payJail(payed, e);
                break;
            default:
                break;
        }
    }

    /**
     * handle the bankruptcy functionality for when a player is bankrupted
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    private void handleBankruptcy(BoardEvent e){
        for (BoardView view : views){
            view.handleAnnounceBankruptedPlayer(e.getPlayer());
            view.handleRemoveOfPlayerPiece(e);
            view.handleRemoveOfPlayerView(e);

            view.handleUpdateSidePanelDisplay(e);
            view.handleNextTurnDisplay(e, currentTurn);
            if (status != Status.UNFINISHED){
                view.handleAnnounceWinner(e);
            }
        }
        handleIfAITurn();
    }

    /**
     * handle the transition to the next player with the respective announcements of the current player result
     * @param e BoardEvent, the events occurring in the BoardModel
     * @param announcements nextPlayerTurnAnnouncements, the announcement of the current player result
     */
    private void handleTransitionToNextPlayerTurn(BoardEvent e, nextPlayerTurnAnnouncements announcements){
        nextTurn();
        for (BoardView view : views){
            getTypeOfViewAnnouncement(view, announcements, e);
            view.handleUpdateSidePanelDisplay(e);
            view.handleNextTurnDisplay(e, currentTurn);
        }
        payed = false;
        handleIfAITurn();
    }

    /**
     * handler for when the player rolls doubles and can roll again
     * @param e BoardEvent, the events occurring in the BoardModel
     */
    private void handlePlayerPlayAgainAnnouncement(BoardEvent e){
        for (BoardView view : views){
            getTypeOfViewAnnouncement(view, nextPlayerTurnAnnouncements.ROLL_AGAIN, e);
            view.handleUpdateSidePanelDisplay(e);
        }
        handleIfAITurn();
    }

    /**
     * handler for when the player quits the game
     * @param e BoardEvent, the events occurring in the BoardModel
     */
    private void handlePlayerQuit(BoardEvent e){
        removePlayer();
        for (BoardView view : this.views){
            view.handlePlayerQuit(e);
            if (status != Status.UNFINISHED){
                view.handleAnnounceWinner(e);
            }
            view.handleRemoveOfPlayerPiece(e);
            view.handleUpdateSidePanelDisplay(e);
            view.handleNextTurnDisplay(e, currentTurn);
        }
        handleIfAITurn();
    }

    /**
     * handler for when the player pays out of Jail
     * @param e BoardEvent, the events occurring in the BoardModel
     */
    private void handlePlayerPayingOutOfJail(BoardEvent e){
        Player p = e.getPlayer();
        Location place = e.boardElementByIndex(p.getPosition());
        if (p.payJail()){
            p.setCurrLocation(place.getName());
            p.setInJail(false);
            payed = true;
        }
        handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.PAY_OUT);
    }

    /**
     * Handler for when the player chooses to purchase houses
     * @param e BoardEvent, the events occurring the BoardModel
     */
    private void handleBuyingOfHouses(BoardEvent e){
        for (BoardView view : this.views){
            view.handlePlayerChoiceToPurchaseHouses(e);
            view.handleUpdateSidePanelDisplay(e);
            view.updateChoicePanel(e.getPlayer());
        }
    }

    /**
     * handler for when the player chooses to sell houses
     * @param e BoardEvent, the events occurring the BoardModel
     */
    private void handleSellingOfHouses(BoardEvent e){
        for (BoardView view : this.views){
            view.handlePlayerChoiceToSellHouses(e);
            view.handleUpdateSidePanelDisplay(e);
            view.updateChoicePanel(e.getPlayer());
        }
    }

    /**
     * Handler for when the player rolls
     * displays the movement of player
     * operates the property landed functionality, and run the state of the game accordingly
     * If player is bankrupted, handle bankruptcy method
     * otherwise if not doubles, move to next player, otherwise play again if not in jail else move to next player
     * @param e BoardEvent, the events occurring in the BoardModel
     */
    private void movePlayerFunctionality(BoardEvent e){
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            private final Player p = e.getPlayer();
            private int pos = p.getPosition();
            private final int sum = roll1 + roll2;
            @Override
            public void run() {
                if (p.getSumOfMovement(p.getPosition(), sum) != pos){
                    int oldPos = pos;
                    boolean go = false;
                    pos++;
                    if (pos > SIZE_OF_BOARD){
                        go = true;
                        pos = 0;
                    }
                    for (BoardView view : views){
                        view.handlePlayerPieceMovement(currentTurn, oldPos, pos);
                        if (go){
                            view.announceReachingGo(e);
                        }
                    }
                }
                else{
                    p.movePlayer(sum);
                    Location place = e.boardElementByIndex(p.getPosition());
                    p.setCurrLocation(place.getName());
                    place.locationElementFunctionality(p, sum, currentTurn);

                    if (checkBankrupt()){
                        handleBankruptcy(e);
                    }else{
                        if (!e.getDoubles()){
                            handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.NONE);
                        }
                        else{
                            if (p.getInJail()){
                                handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.NONE);
                            }
                            else{
                                handlePlayerPlayAgainAnnouncement(e);
                            }
                        }
                    }
                    timer2.cancel();
                }
            }
        }, 0, 200);
    }

    /**
     * Handler for when the player attempts to roll out of jail
     * if the player rolls doubles, announce and run the movePlayerFunctionality method
     * otherwise, handle the in jail functionality accordingly
     * @param e BoardEvent, the event
     */
    private void rollingOutOfJailFunctionality(BoardEvent e){
        if (e.getDoubles()){
            e.getPlayer().setInJail(false);
            for (BoardView view : views){
                view.handleResultsOfRollingInJail(e);
            }
            movePlayerFunctionality(e);
        }
        else{
            e.getPlayer().setTurnsInJail(e.getPlayer().getTurnsInJail() - 1);
            if (e.getPlayer().getTurnsInJail() == 0){
                e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - 50);
            }
            if (checkBankrupt()){
                handleBankruptcy(e);
            }else{
                handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.ROLL_OUT);
            }
        }
    }

    /**
     * handler for when the player rolls in general
     * animation for rolling
     * if player rolls, run movePlayerFunctionality. Otherwise, run rollingOutOfJailFunctionality
     * @param e BoardEvent, the events occurring in the BoardModel
     * @param choice Integer, the player choice
     */
    private void handleRollingDice(BoardEvent e, int choice){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int counter = 0;
            private final Random r = new Random();
            @Override
            public void run() {
                if (counter < ROLLING_DICE_DELAY) {
                    counter++;
                    int lastRoll1 = r.nextInt(6) + 1;
                    int lastRoll2 = r.nextInt(6) + 1;
                    for (BoardView view : views){
                        view.buttonEnableCondition(false);
                        if (counter == ROLLING_DICE_DELAY)
                            view.handleUpdateRoll(roll1, roll2);
                        else
                            view.handleUpdateRoll(lastRoll1, lastRoll2);
                    }
                } else {
                    if (choice == PlayerChoice.ROLL.getChoice()){
                        movePlayerFunctionality(e);
                    }
                    else{
                        rollingOutOfJailFunctionality(e);
                    }
                    timer.cancel();
                }
            }
        }, 0, 200);
    }

    /**
     * handler for when the player decides to save the game.
     * @param filename String, the name of the file to save the information in
     */
    private void handleSaveToXML(String filename){

        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(filename+".xml"));
            out.write(this.toXML());
            out.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * returns an XML representation of the board model as a singular string
     * @return str, the string containing the XML representation of this board model
     */
    private String toXML(){
        String str = "<boardModel>\n";

        //str += "\t<version>" + this.getVersion + "</version>\n";
        str += "\t<centerMoney>" + this.centerMoney + "</centerMoney>\n";
        str += "\t<currentTurn>" + this.currentTurn + "</currentTurn>\n";
        str += "\t<roll1>" + this.roll1 + "</roll1>\n";
        str += "\t<roll2>" + this.roll2 + "</roll2>\n";

        for(Player p : this.gamePlayers){
            str += "\t<player>\n";

            str += p.toXML();

            str += "\t</player>\n";
        }

        str += "</boardModel>\n";

        return str;
    }

    /**
     * Method for simulating the player's turn depending on numerous scenarios. Rolls the dice and determines whether the player is in jail. Gives choices on whether to move, pass, or quit the game.
     */
    public void playCurrPlayerTurn(int choice){
        boolean doubles = rollDiceOfTwo();
        BoardEvent e = new BoardEvent(this, this.board, doubles, this.roll1, this.roll2, this.gamePlayers.get(this.currentTurn), this.currentTurn, this.gamePlayers);

        if (choice == PlayerChoice.ROLL.getChoice()){ // roll
            handleRollingDice(e, choice);
        }
        else if (choice == PlayerChoice.QUIT.getChoice()){ // quit
            handlePlayerQuit(e);
        }
        else if (choice == PlayerChoice.PASS.getChoice()){ // pass
            handleTransitionToNextPlayerTurn(e, nextPlayerTurnAnnouncements.PASS);
        }
        else if (choice == PlayerChoice.PAY_OUT.getChoice()){ // pay out of jail
            handlePlayerPayingOutOfJail(e);
        }
        else if (choice == PlayerChoice.ROLL_OUT.getChoice()){ // roll double out of jail
            handleRollingDice(e, choice);
        }
        else if (choice == PlayerChoice.BUY_HOUSE.getChoice()){ // purchase house
            handleBuyingOfHouses(e);
        }
        else if (choice == PlayerChoice.SELL_HOUSE.getChoice()){ // sell house
            handleSellingOfHouses(e);
        }
    }
}

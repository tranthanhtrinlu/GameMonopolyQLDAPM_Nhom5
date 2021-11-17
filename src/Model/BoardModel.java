package Model;
import Events.BoardEvent;
import Listener.BoardView;

import java.util.*;

/**
 * @author Tony Massaad
 * Class MVC.BoardModel that acts as the model for the Monopoly game. Essentially sets up the game and board as a whole.
 */
public class BoardModel {
    public static final int SIZE_OF_BOARD = 39; // 0-39 inclusive
    public static final int GO_MONEY = 200;
    public static final int JAIL_POSITION = 10; // 11 - 1
    public static final int TOTAL_UTILITIES = 2;
    private static final int ROLLING_DICE_DELAY = 10;
    protected static int centerMoney = 0;

    private List<Location> board;
    private List<BoardView> views;
    private int currentTurn;
    private int numberOfPlayers;
    private Status status;
    private int roll1;
    private int roll2;
    private final ArrayList<Player> gamePlayers;


    /**
     * Sets up the colours for element of the board.
     */
    public enum Color{
        BROWN, LIGHTBLUE, PURPLE, ORANGE, RED, YELLOW, GREEN, DARKBLUE, NONE
    }

    public enum Status{
        FINISHED, UNFINISHED;
    }

    public enum BoardElements{
        MEDITERRANEAN("Mediterranean Avenue", 60, 50,2,10,30,90,160,250,Color.BROWN, 2),
        GO("Go", 0, 0,0,0,0,0,0,0,Color.NONE, 0),
        BALTIC("Baltic Avenue", 60,50,4,20,60,180,320,450, Color.BROWN, 2),
        ORIENTAL("Oriental Avenue", 100, 50,6,30,90,270,400,550, Color.LIGHTBLUE, 3),
        VERMOUNT("Vermount Avenue", 100, 50,6,30,90,270,400,550, Color.LIGHTBLUE, 3),
        CONNECTICUT("Connecticut Avenue", 120, 50,8,40,100,300,450,600, Color.LIGHTBLUE, 3),
        CHARLES("ST.Charles Place", 140,100,10,50,150,450,625,750, Color.PURPLE, 3),
        STATES("STATES AVENUE",140,100,10,50,150,450,625,750,Color.PURPLE, 3),
        VIRGINIA("Virginia Avenue", 160,100,12,60,180,500,700,900, Color.PURPLE, 3),
        JAMES("ST. James Place", 180, 100,14,70,200,550,750,950, Color.ORANGE, 3),
        TENNESSE("Tennesse Avenue", 180,100,14,70,200,550,750,950, Color.ORANGE, 3),
        NEWYORK("New York Avenue", 200,100,16,80,220,600,800,1000, Color.ORANGE, 3),
        KENTUCKY("Kentucky Avenue",220,150,18,90,250,700,875,1050, Color.RED,3),
        INDIANA("Indiana Avenue", 220,150,18,90,250,700,875,1050, Color.RED, 3),
        ILLINOIS("Illinois Avenue", 240,150,20,100,300,750,925,1110, Color.RED, 3),
        ATLANTIC("Atlantic Avenue", 260,150,22,110,330,800,975,1150,Color.YELLOW, 3),
        VENTNOR("Ventnor Avenue", 260,150,22,110,330,800,975,1150, Color.YELLOW, 3),
        MARVIN("Marvin Gardens", 280,150,24,120,360,850,1025,1200, Color.YELLOW, 3),
        PACIFIC("Pacific Avenue", 300,200,26,130,390,900,1100,1275, Color.GREEN, 3),
        CAROLINA("NORTH CAROLINA AVENUE", 300,200,26,130,390,900,1100,1275, Color.GREEN, 3),
        PENNYSYLVANIA("PENNYSYLVANIA AVENUE", 320,200,28,150,450,1000,1200,1400, Color.GREEN, 3),
        PARK("PARK PLACE", 350,200,35,175,500,1100,1300,1500,Color.DARKBLUE, 2),
        BOARDWALK("BOARDWALK", 400,200,50,200,600,1400,1700,2000, Color.DARKBLUE, 2),
        FREEPASS( "Free Pass",0, 0, 0, 0, 0, 0, 0, 0, Color.NONE,0),

        private final String name;
        private final int cost, costPerHouse, initalRent, house1Rent, house2Rent, house3Rent, house4Rent, hotelRent, numOfColors;
        private final Color color;
        BoardElements(String name, int cost, int costPerHouse, int initalRent, int house1Rent, int house2Rent, int house3Rent, int house4Rent, int hotelRent, Color color, int numOfColors){
            this.name = name;
            this.cost = cost;
            this.costPerHouse = costPerHouse;
            this.initalRent = initalRent;
            this.house1Rent = house1Rent;
            this.house2Rent = house2Rent;
            this.house3Rent = house3Rent;
            this.house4Rent = house4Rent;
            this.hotelRent = hotelRent;
            this.color = color;
            this.numOfColors = numOfColors;
        }

        public String getName() {
            return name;
        }

        public int getCost() {
            return cost;
        }

        public int getInitalRent() {
            return initalRent;
        }

        public Color getColor() {
            return this.color;
        }

        public int getHotelRent() {
            return this.hotelRent;
        }

        public int getCostPerHouse() {
            return this.costPerHouse;
        }

        public int getHouse1Rent() {
            return this.house1Rent;
        }

        public int getHouse2Rent() {
            return this.house2Rent;
        }

        public int getHouse3Rent() {
            return this.house3Rent;
        }

        public int getHouse4Rent() {
            return this.house4Rent;
        }

        public int getNumOfColors() {
            return this.numOfColors;
        }
    }

    /**
     * Default constructor for the MVC.BoardModel.
     */
    public BoardModel(){
        this.gamePlayers = new ArrayList<>();
        this.board = new ArrayList<>();
        this.views = new ArrayList<>();
        this.currentTurn = 0;
        this.initializeBoard();
        this.roll1 = 0;
        this.roll2 = 0;
        this.status = Status.UNFINISHED;
    }

    /**
     * gets how much money is in the center
     * @return Integer centerMoney
     */
    protected static int getCenterMoney() {
        return BoardModel.centerMoney;
    }

    /**
     * will set centerMoney
     * @param centerMoney Integer money in center
     */
    protected static void setCenterMoney(int centerMoney) {
        BoardModel.centerMoney = centerMoney;
    }

    /**
     * used to add money to the center
     * @param add Integer added
     */
    protected static void addToCenterMoney(int add){
        BoardModel.centerMoney += add;
    }


    public void addGamePlayers(Player player) {
        this.gamePlayers.add(player);
    }

    public Player getPlayersByIndex(int i) {
        return this.gamePlayers.get(i);
    }

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
        this.board.add(new FreePass(BoardElements.GO));
        this.board.add(new Property(BoardElements.MEDITERRANEAN));
        this.board.add(new FreePass(BoardElements.FREEPASS));
        this.board.add(new Property(BoardElements.BALTIC));
        this.board.add(new Tax(200, "INCOME TAX"));
        this.board.add(new RailRoad("Reading Railroad", 200));
        this.board.add(new Property(BoardElements.ORIENTAL));
        this.board.add(new FreePass(BoardElements.FREEPASS));
        this.board.add(new Property(BoardElements.VERMOUNT));
        this.board.add(new Property(BoardElements.CONNECTICUT));
        this.board.add(new LandOnJail(0, "In Jail"));
        this.board.add(new Property(BoardElements.CHARLES));
        this.board.add(new Utility("Electric Company", 150));
        this.board.add(new Property(BoardElements.STATES));
        this.board.add(new Property(BoardElements.VIRGINIA));
        this.board.add(new RailRoad("PENNSYLVANIA RAILROAD", 200));
        this.board.add(new Property(BoardElements.JAMES));
        this.board.add(new FreePass(BoardElements.FREEPASS));
        this.board.add(new Property(BoardElements.TENNESSE));
        this.board.add(new Property(BoardElements.NEWYORK));
        this.board.add(new FreeParking(0, "FREE PARKING"));
        this.board.add(new Property(BoardElements.KENTUCKY));
        this.board.add(new FreePass(BoardElements.FREEPASS));
        this.board.add(new Property(BoardElements.INDIANA));
        this.board.add(new Property(BoardElements.ILLINOIS));
        this.board.add(new RailRoad("B&O Railroad", 200));
        this.board.add(new Property(BoardElements.ATLANTIC));
        this.board.add(new Property(BoardElements.VENTNOR));
        this.board.add(new Utility("Water Works", 150));
        this.board.add(new Property(BoardElements.MARVIN));
        this.board.add(new GoToJail(0, "GO TO JAIL"));
        this.board.add(new Property(BoardElements.PACIFIC));
        this.board.add(new Property(BoardElements.CAROLINA));
        this.board.add(new FreePass(BoardElements.FREEPASS));
        this.board.add(new Property(BoardElements.PENNYSYLVANIA));
        this.board.add(new RailRoad("Short Line", 200));
        this.board.add(new FreePass(BoardElements.FREEPASS));
        this.board.add(new Property(BoardElements.PARK));
        this.board.add(new Tax(100, "LUXURY TAX"));
        this.board.add(new Property(BoardElements.BOARDWALK));
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
    private boolean checkBankrupt() {
        Player p = this.gamePlayers.get(this.currentTurn);
        if (p.getMoneyAmount() == 0){
            this.removePlayer();
            return true;
        }
        return false;
    }

    private void removePlayer(){
        Player p = this.gamePlayers.get(this.currentTurn);
        p.setOut(true);
        p.bankrupted();
        nextTurn();
        this.numberOfPlayers -= 1;
        updateStatus();
    }

    public void updateStatus(){
        if (this.numberOfPlayers == 1){
            this.status = Status.FINISHED;
        }
    }


    private void movePlayerFunctionality(BoardEvent e){
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            private final Player p = gamePlayers.get(currentTurn);
            private int pos = p.getPosition();
            private final int sum = roll1 + roll2;
            @Override
            public void run() {
                if (p.getPosition()+sum != pos){
                    int oldPos = pos;
                    pos++;
                    for (BoardView view : views){
                        view.handlePlayerPieceMovement(currentTurn, oldPos, pos);
                        if (pos - SIZE_OF_BOARD+1 == 0)
                            view.announceReachingGo(e);
                    }
                }
                else{
                    p.movePlayer(sum);
                    Location place = board.get(p.getPosition());
                    if (place.getName().equals("In Jail") && !p.getInJail()){ // change using enum
                        p.setCurrLocation(place.getName() + ", Just visiting");
                    }else{
                        p.setCurrLocation(place.getName());
                    }
                    place.locationElementFunctionality(p, sum, currentTurn);

                    if (checkBankrupt()){
                        for (BoardView view : views){
                            view.handleAnnounceBankruptedPlayer(p);
                            view.handleRemoveOfPlayerPiece(e);
                            view.handleRemoveOfPlayerView(e);

                            view.handleUpdateSidePanelDisplay(e);
                            view.handleNextTurnDisplay(e, currentTurn);
                            if (status != Status.UNFINISHED){
                                view.handleAnnounceWinner(e);
                            }
                        }
                    }else{
                        if (!e.getDoubles()){
                            nextTurn();
                            for (BoardView view : views){
                                view.handleUpdateSidePanelDisplay(e);
                                view.handleNextTurnDisplay(e, currentTurn);
                            }
                        }
                        else{
                            if (p.getInJail()){
                                nextTurn();
                                for (BoardView view : views){
                                    view.handleUpdateSidePanelDisplay(e);
                                    view.handleNextTurnDisplay(e, currentTurn);
                                }
                            }
                            else{
                                for (BoardView view : views){
                                    view.handleUpdateSidePanelDisplay(e);
                                    view.handleAnnounceRollingAgain(e);
                                }
                            }
                        }
                        for (BoardView view : views){
                            view.buttonEnableCondition(true);
                            view.updateChoicePanel(gamePlayers.get(currentTurn));
                        }
                    }
                    timer2.cancel();
                }
            }
        }, 0, 200);
    }

    private void rollingOutOfJailFunctionality(BoardEvent e){
        if (e.getDoubles()){
            e.getPlayer().setInJail(false);
            for (BoardView view : views){
                view.handleRollingDoubles(e);
            }
            movePlayerFunctionality(e);
        }
        else{
            e.getPlayer().setTurnsInJail(e.getPlayer().getTurnsInJail() - 1);
            if (e.getPlayer().getTurnsInJail() == 0){
                e.getPlayer().setMoneyAmount(e.getPlayer().getMoneyAmount() - 50);
            }
            if (checkBankrupt()){
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
            }else{
                nextTurn();
                for (BoardView view : views){
                    view.handleRollingDoubles(e);
                    view.handleUpdateSidePanelDisplay(e);
                    view.handleNextTurnDisplay(e, currentTurn);
                    view.buttonEnableCondition(true);
                    view.updateChoicePanel(gamePlayers.get(currentTurn));
                }
            }
        }
    }


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
                    if (choice == 1){
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
     * Method for simulating the player's turn depending on numerous scenarios. Rolls the dice and determines whether the player is in jail. Gives choices on whether to move, pass, or quit the game.
     */
    public void playCurrPlayerTurn(int choice){
        boolean doubles = rollDiceOfTwo();
        BoardEvent e = new BoardEvent(this, this.board, doubles, this.roll1, this.roll2, this.gamePlayers.get(this.currentTurn), this.currentTurn, this.gamePlayers);

        if (choice == 1){ // roll
            handleRollingDice(e, choice);
        }
        else if (choice == 2){ // quit
            removePlayer();
            for (BoardView view : this.views){
                view.handlePlayerQuit(e);
                if (status != Status.UNFINISHED){
                    view.handleAnnounceWinner(e);
                }
                view.handleUpdateSidePanelDisplay(e);
                view.handleNextTurnDisplay(e, currentTurn);
                view.updateChoicePanel(gamePlayers.get(currentTurn));
            }
        }
        else if (choice == 3){ // pass
            nextTurn();
            for (BoardView view : this.views){
                view.announcePlayerPass(e);
                view.handleUpdateSidePanelDisplay(e);
                view.handleNextTurnDisplay(e, currentTurn);
                view.updateChoicePanel(gamePlayers.get(currentTurn));
            }
        }
        else if (choice == 4){ // pay out of jail
            Player p = this.gamePlayers.get(this.currentTurn);
            Location place = this.board.get(p.getPosition());
            boolean payed = false;
            if (p.payJail()){
                p.setCurrLocation(place.getName() + " - Just Visiting"); // fix with enum
                p.setInJail(false);
                payed = true;
            }
            nextTurn();
            for (BoardView view : this.views){
                view.payJail(payed, e);
                view.handleUpdateSidePanelDisplay(e);
                view.handleNextTurnDisplay(e, currentTurn);
                view.updateChoicePanel(gamePlayers.get(currentTurn));
            }
        }
        else if (choice == 5){ // roll double out of jail
            handleRollingDice(e, choice);
        }
        else if (choice == 6){ // purchase house
            for (BoardView view : this.views){
                view.announceDecisionToPurchaseHouses(e);
                view.handlePlayerChoiceToPurchaseHouses(e);
            }
        }
        else if (choice == 7){ // sell house
            // prompt user
            // check if they cancel
            // if sell

            for (BoardView view : this.views){
                view.announceDecisionToSellHouses(e);
                view.handlePlayerChoiceToSellHouses(e);
            }
        }
    }
}

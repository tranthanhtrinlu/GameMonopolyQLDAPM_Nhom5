package Model;
import Events.BoardEvent;
import Listener.BoardView;
import Model.BoardElements.*;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;
import Model.GamePlayer.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * @author Tony Massaad
 * Class  that acts as the model for the Monopoly game. Essentially sets up the game and board as a whole.
 */
public class BoardModel {
    public static final int MAX_PLAYERS = 5;
    public static final int SIZE_OF_BOARD = 39; // 0-39 inclusive
    public static final int GO_MONEY = 200;
    public static final int JAIL_POSITION = 10; // 11 - 1
    public static final int TOTAL_UTILITIES = 2;
    private static final int ROLLING_DICE_DELAY = 10;
    public static final String STARTING_POSITION = "Go";
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
    private String version;

    /**
     * Color constants for the Board
     */
    public enum Color{
        BROWN, LIGHTBLUE, PURPLE, ORANGE, RED, YELLOW, GREEN, DARKBLUE, NONE
    }

    /**
     * Enum for constant strings on loading and saving xml
     */
    public enum StringRepresentationOfXML{
        JAR_SAVED("./savedFile.xml"), JAR_LOAD_UK("./UKBoardModel.xml"), JAR_LOAD_US("./NewBoardModel.xml"),
        LOAD_US("src/LoadXML/NewBoardModel.xml"), LOAD_UK("src/LoadXML/UKBoardModel.xml"), SAVED("src/SaveXML/savedFile.xml");

        private final String path;

        /**
         * Constructor for String Representation
         * @param path String, the path
         */
        StringRepresentationOfXML(String path){
            this.path = path;
        }

        /**
         * Get the path of the Given XML file
         * @return String, the path
         */
        public String getPath(){
            return this.path;
        }
    }

    /**
     * Status Constant of the Game.
     */
    public enum Status{
        FINISHED, UNFINISHED
    }

    /**
     * The type of boards board model can play
     */
    public enum TypeOfBoards{
        UK("UK"), US("US");

        private final String version;
        TypeOfBoards(String version){
            this.version = version;
        }

        /**
         * get the version of the game as string
         * @return String, the version
         */
        public String getVersion() {
            return this.version;
        }
    }

    /**
     * Constants for handling player choice throughout the game
     */
    public enum PlayerChoice{
        ROLL(1), QUIT(2), PASS(3), PAY_OUT(4), ROLL_OUT(5), BUY_HOUSE(6), SELL_HOUSE(7), SAVE(8), SAVE_QUIT(9);

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

    /**
     * Constructor for BoardModel
     * @param currentTurn Integer, the current turn
     * @param roll1 Integer, the first roll the game is on
     * @param roll2 Integer, the second roll the game is on
     */
    public BoardModel(int currentTurn, int roll1, int roll2) {
        this.gamePlayers = new ArrayList<>();
        this.board = new ArrayList<>();
        this.views = new ArrayList<>();
        this.currentTurn = currentTurn;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.status = Status.UNFINISHED;
        this.payed = false;
        this.version = "";
    }


    /**
     * set the version of the game
     * @param version String, the version
     */
    public void setGameVersion(String version){
        this.version = version;
    }

    /**
     * Getter for getting the game version
     * @return String, the game version
     */
    public String getGameVersion(){
        return this.version;
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
     * get the number of players in the game
     * @return Integer, the number of players
     */
    public int getNumberOfPlayers() {
        return this.gamePlayers.size();
    }

    /**
     * gets the size of the board
     * @return Integer, size
     */
    public int getSizeOfBoard(){return this.board.size();}

    /**
     * get the integer value of the first dice roll
     * @return Integer, the first dice roll
     */
    public int getRoll1() {
        return this.roll1;
    }

    /**
     * get the integer value of the second dice roll
     * @return Integer, the second dice roll
     */
    public int getRoll2() {
        return this.roll2;
    }

    /**
     * get the true number of players in the game (player's who out attribute it false)
     * @return Integer, the true number of players
     */
    public int getNumOfPlayers() {
        return this.numberOfPlayers;
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
     * @param path String, the file path
     */
    public boolean initializeBoard(String path){
        try{
            File load = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(load);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Location");
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr).getFirstChild().getNextSibling();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    this.parseAndAddDataLocationToBoard(node);
                }
            }
            return true;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Didn't load path " + path);
            return false;
        }
    }

    /**
     * given the node element, add Location to the board according to the node
     * @param node Node, the node element of Location
     */
    private void parseAndAddDataLocationToBoard(Node node){
        switch (node.getNodeName()) {
            case "FreePass": {
                this.board.add(FreePass.createNewFreePass(node));
                break;
            }
            case "Property": {
                this.board.add(Property.createNewProperty(node));
                break;
            }
            case "Tax": {
                this.board.add(Tax.createNewTax(node));
                break;
            }
            case "RailRoad": {
                this.board.add(RailRoad.createNewRailRoad(node));
                break;
            }
            case "LandOnJail": {
                this.board.add(LandOnJail.createNewLandOnJail(node));
                break;
            }
            case "Utility": {
                this.board.add(Utility.createNewUtility(node));
                break;
            }
            case "FreeParking": {
                this.board.add(FreeParking.createNewFreeParking(node));
                break;
            }
            case "GoToJail": {
                this.board.add(GoToJail.createNewGoToJail(node));
                break;
            }
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
            playCurrPlayerTurn(((AI) p).playAI());
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
    private boolean handleSaveToXML(String filename){
        try{
            File save = new File(filename);
            BufferedWriter out = new BufferedWriter(new FileWriter(save));
            out.write(this.toXML());
            out.close();
            return true;
        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * returns an XML representation of the board model as a singular string
     * @return str, the string containing the XML representation of this board model
     */
    private String toXML(){
        String str = "<boardModel>\n";
        str += "\t<version>" + this.version + "</version>\n";
        str += "\t<centerMoney>" + BoardModel.centerMoney + "</centerMoney>\n";
        str += "\t<numberOfPlayers>" + this.numberOfPlayers + "</numberOfPlayers>\n";
        str += "\t<currentTurn>" + this.currentTurn + "</currentTurn>\n";
        str += "\t<roll1>" + this.roll1 + "</roll1>\n";
        str += "\t<roll2>" + this.roll2 + "</roll2>\n";

        for(Player p : this.gamePlayers){
            if (p instanceof User){
                str += p.toXML("User");
                continue;
            }
            str += p.toXML("AI");
        }
        str += "</boardModel>\n";
        return str;
    }

    /**
     * create the board according to the version of the board model specified
     */
    public void createBoard() {
        if (!initializeBoard(getVersionTypeJar()))
            initializeBoard(getVersionType());
    }

    /**
     * Get the version path in JAR file
     * @return String, the path
     */
    private String getVersionTypeJar(){
        if (this.version.equals(TypeOfBoards.US.getVersion())){
            return StringRepresentationOfXML.JAR_LOAD_US.getPath();
        }
        return StringRepresentationOfXML.JAR_LOAD_UK.getPath();
    }

    /**
     * get the version path in IDE
     * @return String, the path
     */
    private String getVersionType(){
        if (this.version.equals(TypeOfBoards.US.getVersion())){
            return StringRepresentationOfXML.LOAD_US.getPath();
        }
        return StringRepresentationOfXML.LOAD_UK.getPath();
    }

    /**
     * Initialize the board model given the document parsed
     * @param doc Document, the xml document parsed
     * @throws ParserConfigurationException Throw parser exception if parsing causes error
     * @throws SAXException Throw sax exception if saxing causes error
     * @throws IOException Throw IO exception if IO causes error
     */
    private void initializeLoadedBoardModel(Document doc) throws IOException, SAXException, ParserConfigurationException {
        String version = doc.getElementsByTagName("version").item(0).getTextContent();
        int diceRoll1 = Integer.parseInt(doc.getElementsByTagName("roll1").item(0).getTextContent());
        int diceRoll2 = Integer.parseInt(doc.getElementsByTagName("roll2").item(0).getTextContent());
        int currentTurn = Integer.parseInt(doc.getElementsByTagName("currentTurn").item(0).getTextContent());
        int centerMoney = Integer.parseInt(doc.getElementsByTagName("centerMoney").item(0).getTextContent());
        int numOfPlayers = Integer.parseInt(doc.getElementsByTagName("numberOfPlayers").item(0).getTextContent());
        this.roll1 = diceRoll1;
        this.roll2 = diceRoll2;
        this.currentTurn = currentTurn;
        this.numberOfPlayers = numOfPlayers;
        BoardModel.centerMoney = centerMoney;
        this.version = version;
        createBoard();
    }

    /**
     * Load a saved path XML specified by the player in the Game menu
     * @param path String, the file path
     */
    public boolean loadSavedXML(String path) {
        try{
            Player p;
            File file = new File(path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodePlayerList = doc.getElementsByTagName("player");
            initializeLoadedBoardModel(doc);
            for (int i = 0; i < nodePlayerList.getLength(); i++){
                Node player = nodePlayerList.item(i);
                if (player.getNodeType() == Node.ELEMENT_NODE){
                    Element playerElement = (Element) player;
                    p = Player.createPlayer(playerElement);
                    this.addGamePlayers(p);
                    p.parseAddPlayerProperties(playerElement, this.board);
                    p.parseAddPlayerOwnedColors(playerElement);
                }
            }
            return true;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Failed to Load " + path);
            return false;
        }
    }

    private void handleSavedFunctionality(){
        if (!handleSaveToXML(StringRepresentationOfXML.SAVED.getPath()))
            handleSaveToXML(StringRepresentationOfXML.JAR_SAVED.getPath());
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
        }else if (choice == PlayerChoice.SAVE.getChoice()){
            handleSavedFunctionality();
        }else if (choice == PlayerChoice.SAVE_QUIT.getChoice()){
            handleSavedFunctionality();
            System.exit(0);
        }
    }
}

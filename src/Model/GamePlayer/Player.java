package Model.GamePlayer;

import Model.BoardModel;
import Model.BoardElements.Location;
import Model.BoardElements.Property;

import java.util.List;

/**
 * @author Tony Massaad
 * Class MVC.Player that defines the attributes of a player in the Monopoly game.
 */
public abstract class Player{

    private final String playerName;
    private int moneyAmount;
    private boolean inJail;
    private int turnsInJail;
    private int position;
    private String currLocation;
    private int numOfRailroads;
    private int numOfUtilities;
    private boolean out;

    /**
     *  MVC.Player default constructor
     * @param name String player name
     */
    public Player(String name){
        this.playerName = name;
        this.moneyAmount = 1500;
        this.out = false;
        this.position = 0;
        this.inJail = false;
        this.turnsInJail = 0;
        this.currLocation = "GO";
        this.numOfRailroads = 0;
        this.numOfUtilities = 0;
    }


    public void setOut(boolean out) {
        this.out = out;
    }

    public boolean getOut(){
        return this.out;
    }

    /**
     * setter method for setting the player position
     * @param position Integer
     */
    public void setPosition(int position){
        this.position = position;
    }

    /**
     * Setter method for setting the current location
     * @param currLocation A String currLocation
     */
    public void setCurrLocation(String currLocation) {
        this.currLocation = currLocation;
    }

    /**
     * used to move the character around the board
     * @param combinedRolls Integer of dice rolled
     * @return True if player is moved, false otherwise
     */
    public boolean movePlayer(int combinedRolls){
        this.position += combinedRolls;
        if (this.position >= BoardModel.SIZE_OF_BOARD){
            this.moneyAmount += BoardModel.GO_MONEY;
            this.position -= BoardModel.SIZE_OF_BOARD;
            return true;
        }
        return false;
    }

    public int getSumOfMovement(int pos, int sum){
        pos += sum;
        if (pos >= BoardModel.SIZE_OF_BOARD){
            pos -= BoardModel.SIZE_OF_BOARD;
        }
        return pos;
    }

    /**
     * sets the player in jail
     * @param inJail boolean in jail or not
     */
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
        if (inJail)
            this.turnsInJail = 3;
        else
            this.turnsInJail = 0;
    }

    /**
     * Getter method for getting the turns in jail
     * @return An integer amount of turns
     */
    public int getTurnsInJail(){
        return this.turnsInJail;
    }

    /**
     * Setter method for setting the number of turns in jail
     * @param val An integer val
     */
    public void setTurnsInJail(int val){
        this.turnsInJail = val;
    }

    /**
     * Getter method for getting a boolean that describes if a player is in jail
     * @return A boolean inJail
     */
    public boolean getInJail(){
        return this.inJail;
    }

    /**
     * Getter for getting the player name
     * @return A string name
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Getter for getting the money amount of the player
     * @return An integer moneyAmount
     */
    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    /**
     * sets amount of money player has
     * @param moneyAmount Integer
     */
    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    /**
     *adds railroads to player
     */
    public void addNumOfRailroads(){
        this.numOfRailroads++;
    }

    /**
     * gets the number of railroads
     * @return number of railroad player owns
     */
    public int getNumOfRailroads() {
        return this.numOfRailroads;
    }

    /**
     * gets number of utilities player has
     * @return Integer numOfUtilities
     */
    public int getNumOfUtilities(){
        return this.numOfUtilities;
    }

    /**
     * adds num of utilities to the player
     */
    public void addNumOfUtilities(){
        this.numOfUtilities++;
    }

    /**
     * gets position of the player
     * @return position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Overrides java object equals() method
     * @param obj Object, the object to check
     * @return boolean, true iff the comparison and the object is identical, otherwise false
     */
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if(obj == null || obj.getClass()!= this.getClass()){
            return false;
        }
        Player player = (Player) obj;
        return this.playerName.equals(player.playerName) && this.moneyAmount == player.moneyAmount
                && this.position == player.position
                && this.inJail == player.inJail && this.currLocation.equals(player.currLocation) && this.numOfRailroads == player.numOfRailroads
                && this.numOfUtilities == player.numOfUtilities && this.turnsInJail == player.turnsInJail;
    }

    /**
     * Get the current location
     * @return String, current location
     */
    public String getCurrLocation() {
        return this.currLocation;
    }

    /**
     * Overridden Java toString method
     * @return A string statement of the state of a player
     */
    public String toString(){
        return "Player: " + this.playerName + "\n{\n" +
                "Money: $" + this.moneyAmount + "\nLocation: " + this.currLocation + "\n}";
    }

    /**
     * Boolean method for confirming jail payment does not result in bankruptcy
     * @return True if the player's money is greater than 50, false otherwise
     */
    public boolean payJail() {
        return this.moneyAmount - 50 > 0;

    }
    
    public abstract void bankrupted();

    public abstract void addProperty(Location property);

    public abstract void addColorToProperty(BoardModel.Color color, int i);

    public abstract List<Property> getEstatePropertiesOfPlayer();

    public abstract int numberOfEstateProperties();

    public abstract int numberOfEstatePropertiesWithHouses();

    public abstract int getNumOfProperties();

    public abstract Location getPropertyByIndex(int i);

    public abstract boolean numberOfColoredPropertiesOwned(BoardModel.Color color, int numberOfColor);
}

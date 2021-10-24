import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player{

    private final String playerName;
    private int moneyAmount;
    private HashMap<BoardModel.Color, Integer> ownedPropertiesBasedOnColors;
    private List<Location> ownedProperties;
    private boolean inJail;
    private int turnsInJail;
    private int position;
    private String currLocation;
    private int numOfRailroads;
    private int numOfUtilities;

    /**
     *  Player default constructor
     * @param name String player name
     */
    public Player(String name){
        this.playerName = name;
        this.moneyAmount = 1500;
        this.ownedPropertiesBasedOnColors = new HashMap<>();
        this.ownedProperties = new ArrayList<>();
        this.position = 0;
        this.inJail = false;
        this.turnsInJail = 0;
        this.currLocation = "GO";
        this.numOfRailroads = 0;
        this.numOfUtilities = 0;
    }

    /**
     * lets you print properties player owns
     * @return String of owned property by the player
     */
    public String printOwnedProperties(){
        StringBuilder s = new StringBuilder();
        for(Location location : this.ownedProperties){
            s.append(location.getName()).append(", ");
        }
        return s.toString();
    }

    /**
     * gets the properties in a players list
     * @return properties
     */
    public List<Property> getEstatePropertiesOfPlayer(){
        StringBuilder s = new StringBuilder();
        List<Property> properties = new ArrayList<>();
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty instanceof Property) {
                properties.add((Property) ownedProperty);
            }
        }
        return properties;
    }

    /**
     * gets the property using its name
     * @param name String name of property
     * @return if it is owned
     */
    public Property getPropertyByName(String name){
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty.getName().equals(name)){
                return (Property) ownedProperty;
            }
        }
        return null;
    }

    public int numberOfEstateProperties(){
        int k = 0;
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty instanceof Property) {
                k++;
            }
        }
        return k;
    }

    public Location getLocationByIndex(int i){
        return this.ownedProperties.get(i);
    }

    /**
     * how many color properties does the player own
     * @param color Color
     * @param numOfColor Integer
     * @return number of colored properties owned by player
     */
    public boolean numberOfColoredPropertiesOwned(BoardModel.Color color, int numOfColor){
        return this.ownedPropertiesBasedOnColors.get(color) == numOfColor;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public void setCurrLocation(String currLocation) {
        this.currLocation = currLocation;
    }

    /**
     * used to move the character around the board
     * @param combinedRolls Integer of dice rolled
     * @return
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

    /**\
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

    public int getTurnsInJail(){
        return this.turnsInJail;
    }

    public void setTurnsInJail(int val){
        this.turnsInJail = val;
    }

    public boolean getInJail(){
        return this.inJail;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    public void addProperty(Location p){
        this.ownedProperties.add(p);
    }

    /**
     * adds a color code to properties if the player owns all of one color double rent
     * @param color Color
     * @param add Integer color int added to property
     */
    public void addColorToProperty(BoardModel.Color color, int add){
        if (this.ownedPropertiesBasedOnColors.containsKey(color)){
            int oldVal = this.ownedPropertiesBasedOnColors.get(color);
            this.ownedPropertiesBasedOnColors.replace(color, oldVal + add);
            return;
        }
        this.ownedPropertiesBasedOnColors.put(color, add);
    }

    /**
     * if player bankrupt reset all properties own by them
     */
    public void bankrupted(){
        for (Location location : this.ownedProperties){
            location.resetOwner();
        }
    }

    public int getSizeOfOwnedProperties(){
        return this.ownedProperties.size();
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
     * Overrides java oject equals() method
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
                && this.ownedPropertiesBasedOnColors == player.ownedPropertiesBasedOnColors
                && this.position == player.position && this.ownedProperties == player.ownedProperties
                && this.inJail == player.inJail && this.currLocation.equals(player.currLocation) && this.numOfRailroads == player.numOfRailroads
                && this.numOfUtilities == player.numOfUtilities && this.turnsInJail == player.turnsInJail;
    }

    public String toString(){
        return "Player: " + this.playerName + "\n{\n" +
                "Money: $" + this.moneyAmount + "\nLocation: " + this.currLocation + "\nOwned Properties: " + this.printOwnedProperties() + "\n}";
    }

    public boolean payJail() {
        return this.moneyAmount - 50 > 0;

    }
}

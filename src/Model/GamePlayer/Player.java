package Model.GamePlayer;
import Model.BoardElements.RailRoad;
import Model.BoardElements.Utility;
import Model.BoardModel;
import Model.BoardElements.Location;
import Model.BoardElements.Property;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<BoardModel.Color, Integer> ownedPropertiesBasedOnColors;
    private List<Location> ownedProperties;

    /**
     *  MVC.Player default constructor
     * @param name String player name
     */
    public Player(String name, int moneyAmount){
        this(name, moneyAmount, false, 0, false, 0, BoardModel.STARTING_POSITION, 0,0);
    }

    public Player(String playerName, int moneyAmount, boolean out,  int position, boolean inJail, int turnsInJail, String currLocation, int numOfRailroads, int numOfUtilities){
        this.playerName = playerName;
        this.moneyAmount = moneyAmount;
        this.out = out;
        this.position = position;
        this.inJail = inJail;
        this.turnsInJail = turnsInJail;
        this.currLocation = currLocation;
        this.numOfRailroads = numOfRailroads;
        this.numOfUtilities = numOfUtilities;
        this.ownedPropertiesBasedOnColors = new HashMap<>();
        this.ownedProperties = new ArrayList<>();
    }

    /**
     * set the player out if they quit or are bankrupted
     * @param out boolean
     */
    public void setOut(boolean out) {
        this.out = out;
    }

    /**
     * get the player attribute out
     * @return boolean
     */
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
        if (this.position > BoardModel.SIZE_OF_BOARD){
            this.moneyAmount += BoardModel.GO_MONEY;
            this.position -= BoardModel.SIZE_OF_BOARD;
            return true;
        }
        return false;
    }

    /**
     * get the total sum of movements the player would move with dice dum
     * @param pos Integer, hypothetical position
     * @param sum Integer, the sum of dice
     * @return Integer, the new position
     */
    public int getSumOfMovement(int pos, int sum){
        pos += sum;
        if (pos > BoardModel.SIZE_OF_BOARD){
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
     * Add to the the number of railroads the player owns
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
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if(obj == null || obj.getClass()!= this.getClass()){
            return false;
        }
        User player = (User) obj;
        return this.playerName.equals(player.getPlayerName()) && this.moneyAmount == player.getMoneyAmount()
                && this.ownedPropertiesBasedOnColors == player.getOwnedPropertiesBasedOnColors()
                && this.position == player.getPosition() && this.ownedProperties == player.getOwnedProperties()
                && this.inJail == player.getInJail() && this.currLocation.equals(player.getCurrLocation()) && this.numOfRailroads == player.getNumOfRailroads()
                && this.numOfUtilities == player.getNumOfUtilities() && this.turnsInJail == player.getTurnsInJail() && this.out == player.getOut();
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
                "Money: $" + this.moneyAmount + "\nLocation: " + this.currLocation + "\nOwned Properties: " + this.printOwnedProperties() + "\n}";
    }

    /**
     * Boolean method for confirming jail payment does not result in bankruptcy
     * @return True if the player's money is greater than 50, false otherwise
     */
    public boolean payJail() {
        return this.moneyAmount - 50 > 0;

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

    /**
     * checks if the player owns a property with a given color
     * @param color BoardModel.Color the color to check for
     * @return boolean indicating whether the player has the color or not
     */
    public boolean hasColor(BoardModel.Color color){
        return this.ownedPropertiesBasedOnColors.containsKey(color);
    }


    /**
     * Integer method that checks the number of properties that can have houses on them
     * @return An integer k
     */
    public int numberOfEstatePropertiesThatPlayerCanBuyHousesFor(){
        int k = 0;
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty instanceof Property) {
                //if (this.ownedPropertiesBasedOnColors.get(((Property) ownedProperty).getColor()) == ((Property) ownedProperty).getNumberOfColor())
                if (((Property) ownedProperty).getNumOfHouses() != ((Property) ownedProperty).getMaxNumberOfHouses()){
                    if (((Property) ownedProperty).numberOfHousesCanBuy() > 0){
                        k++;
                    }
                }
            }
        }
        return k;
    }


    /**
     * Integer method that checks the number of properties that has houses on them
     * @return An integer k
     */
    public int numberOfEstatePropertiesWithHouses(){
        int k = 0;
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty instanceof Property) {
                if (((Property) ownedProperty).getNumOfHouses() > 0)
                    k++;
            }
        }
        return k;
    }

    /**
     * get the size of teh property owned arrays
     * @return Integer, the size
     */
    public int getNumOfProperties(){
        return this.ownedProperties.size();
    }

    /**
     * Get property in list by index
     * @param i, Integer index
     * @return Location, the property
     */
    public Location getPropertyByIndex(int i){
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

    /**
     * Getter for the color hashmap.
     * @return A Color, Integer hashmap ownedPropertiesBasedOnColors.
     */
    public HashMap<BoardModel.Color, Integer> getOwnedPropertiesBasedOnColors() {
        return ownedPropertiesBasedOnColors;
    }

    /**
     * Void method to add a property to the player's list of owned properties
     * @param p A MVC.Location p
     */
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
        this.ownedProperties.clear();
        this.ownedPropertiesBasedOnColors.clear();
    }

    public List<Location> getOwnedProperties() {
        return this.ownedProperties;
    }

    /**
     * returns a xml represntation of player as a single string
     * @return
     */
    public String toXML(String kindOfPlayer){
        String currLocation = this.currLocation;
        if (currLocation.equals(RailRoad.INVALID_PARSE))
            currLocation = RailRoad.VALID_PARSE;
        String str = "";
        str += "\t<player>\n";
        str += "\t\t<typeOfPlayer>" + kindOfPlayer + "</typeOfPlayer>\n";
        str += "\t\t<moneyAmount>" + this.moneyAmount + "</moneyAmount>\n";
        str += "\t\t<playerName>" + this.playerName + "</playerName>\n";
        str += "\t\t<inJail>" + this.inJail + "</inJail>\n";
        str += "\t\t<turnsInJail>" + this.turnsInJail + "</turnsInJail>\n";
        str += "\t\t<position>" + this.position + "</position>\n";
        str += "\t\t<currLocation>" + currLocation + "</currLocation>\n";
        str += "\t\t<numOfRailroads>" + this.numOfRailroads + "</numOfRailroads>\n";
        str += "\t\t<numOfUtilities>" + this.numOfUtilities + "</numOfUtilities>\n";
        str += "\t\t<out>" + this.out + "</out>\n";
        // Properties
        str += "\t\t<ownedProperties>\n";
        for(Location l : this.ownedProperties){
            str += "\t\t\t<Location>\n";
            if (l instanceof Property){
                str += ((Property) l).toXML();
            }else if (l instanceof Utility){
                str += ((Utility) l).toXML();
            }else if (l instanceof RailRoad){
                str += ((RailRoad) l).toXML();
            }
            str += "\t\t\t</Location>\n";
        }
        str += "\t\t</ownedProperties>\n";
        // Colored Owned Properties
        str += "\t\t<coloredOwnedProperties>\n";
        for(BoardModel.Color color : this.ownedPropertiesBasedOnColors.keySet()){
            str += "\t\t\t<index>\n";
            str += "\t\t\t\t<color>" + color + "</color>\n";
            str += "\t\t\t\t<number>" + this.ownedPropertiesBasedOnColors.get(color) + "</number>\n";
            str += "\t\t\t</index>\n";
        }
        str += "\t\t</coloredOwnedProperties>\n";
        str += "\t</player>\n";
        return str;
    }

    /**
     * Create a player from load
     * @param playerElement Element, the element contain ing the player information
     * @return Player, the loaded player
     */
    public static Player createPlayer(Element playerElement){
        String kindOfPlayer = playerElement.getElementsByTagName("typeOfPlayer").item(0).getTextContent();
        int moneyAmount = Integer.parseInt(playerElement.getElementsByTagName("moneyAmount").item(0).getTextContent());
        String name = playerElement.getElementsByTagName("playerName").item(0).getTextContent();
        boolean inJail = Boolean.parseBoolean(playerElement.getElementsByTagName("inJail").item(0).getTextContent());
        int turnsInJail = Integer.parseInt(playerElement.getElementsByTagName("turnsInJail").item(0).getTextContent());
        int position = Integer.parseInt(playerElement.getElementsByTagName("position").item(0).getTextContent());
        String currLocation = playerElement.getElementsByTagName("currLocation").item(0).getTextContent();
        int numOfRailroads = Integer.parseInt(playerElement.getElementsByTagName("numOfRailroads").item(0).getTextContent());
        int numOfUtilities = Integer.parseInt(playerElement.getElementsByTagName("numOfUtilities").item(0).getTextContent());
        boolean out = Boolean.parseBoolean(playerElement.getElementsByTagName("out").item(0).getTextContent());
        if (kindOfPlayer.equals("User"))
            return new User(name, moneyAmount, out, position, inJail, turnsInJail, currLocation, numOfRailroads, numOfUtilities);
        return new AI(name, moneyAmount, out, position, inJail, turnsInJail, currLocation, numOfRailroads, numOfUtilities);
    }

    /**
     * Load properties to player
     * @param nodeProperty, the node containing the properties
     * @param board the list of locations on the board
     */
    private void loadPropertyToPlayer(Node nodeProperty, List<Location> board){
        Element property = (Element) nodeProperty;
        String propertyName = property.getElementsByTagName("name").item(0).getTextContent();
        int numberOfHouses = Integer.parseInt(property.getElementsByTagName("numberOfHouses").item(0).getTextContent());
        int oldNumOfHouses = Integer.parseInt(property.getElementsByTagName("oldNumOfHouses").item(0).getTextContent());
        for (Location l : board){
            if (l instanceof Property){
                if (l.getName().equals(propertyName)){
                    ((Property) l).setNumOfHouses(numberOfHouses);
                    ((Property) l).setOldNumOfHouses(oldNumOfHouses);
                    ((Property) l).setOwner(this);
                    this.addProperty(l);
                    break;
                }
            }
        }
    }

    /**
     * Load non properties to player
     * @param location The node containing the locations
     * @param board the list of locations on the board
     */
    private void loadNonPropertyOwnerShipToPlayer(Node location, List<Location> board){
        String locationName = ((Element) location).getElementsByTagName("name").item(0).getTextContent();
        for (Location l : board) {
            if (l instanceof RailRoad) {
                if (l.getName().equals(locationName)) {
                    ((RailRoad) l).setOwner(this);
                    this.addProperty(l);
                    break;
                }
            }else if (l instanceof Utility) {
                if (l.getName().equals(locationName)) {
                    ((Utility) l).setOwner(this);
                    this.addProperty(l);
                    break;
                }
            }
        }
    }

    /**
     * Parses the player properties
     * @param playerElement the element containing the players information
     * @param board the list of locations on the board
     */
    public void parseAddPlayerProperties(Element playerElement, List<Location> board) {
        Element ownedLocations = (Element) playerElement.getElementsByTagName("ownedProperties").item(0);
        NodeList locations = ownedLocations.getElementsByTagName("Location");
        for (int itr = 0; itr < locations.getLength(); itr++) {
            Node node = locations.item(itr).getFirstChild().getNextSibling();
            if (node.getNodeType() == Node.ELEMENT_NODE){
                if (node.getNodeName().equals("Property")){
                    this.loadPropertyToPlayer(node, board);
                }else{
                    this.loadNonPropertyOwnerShipToPlayer(node, board);
                }
            }
        }
    }

    /**
     * parses and adds the property colors owned
     * @param playerElement the element containing the players information
     */
    public void parseAddPlayerOwnedColors(Element playerElement) {
        Element ownedColors = (Element) playerElement.getElementsByTagName("coloredOwnedProperties").item(0);
        NodeList colorsOwned = ownedColors.getElementsByTagName("index");
        for (int itr = 0; itr<colorsOwned.getLength(); itr++) {
            Node colorIndex = colorsOwned.item(itr);
            if (colorIndex.getNodeType() == Node.ELEMENT_NODE){
                Element value = (Element) colorIndex;
                BoardModel.Color color = BoardModel.Color.valueOf(value.getElementsByTagName("color").item(0).getTextContent());
                int num = Integer.parseInt(value.getElementsByTagName("number").item(0).getTextContent());
                this.addColorToProperty(color, num);
            }
        }
    }
}

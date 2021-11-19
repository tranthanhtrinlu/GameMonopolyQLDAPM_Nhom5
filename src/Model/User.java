package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User extends Player{

    private HashMap<BoardModel.Color, Integer> ownedPropertiesBasedOnColors;
    private List<Location> ownedProperties;/**
     * MVC.Player default constructor
     *
     * @param name String player name
     */
    public User(String name) {
        super(name);
        this.ownedPropertiesBasedOnColors = new HashMap<>();
        this.ownedProperties = new ArrayList<>();
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
     * Integer method that checks the number of properties that can have houses on them
     * @return An integer k
     */
    public int numberOfEstateProperties(){
        int k = 0;
        for (Location ownedProperty : this.ownedProperties) {
            if (ownedProperty instanceof Property) {
                //if (this.ownedPropertiesBasedOnColors.get(((Property) ownedProperty).getColor()) == ((Property) ownedProperty).getNumberOfColor())
                if (((Property) ownedProperty).getNumOfHouses() != ((Property) ownedProperty).getMaxNumberOfHouses())
                    k++;
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
        return super.getPlayerName().equals(player.getPlayerName()) && super.getMoneyAmount() == player.getMoneyAmount()
                && this.ownedPropertiesBasedOnColors == player.ownedPropertiesBasedOnColors
                && super.getPosition() == player.getPosition() && this.ownedProperties == player.ownedProperties
                && super.getInJail() == player.getInJail() && super.getCurrLocation().equals(player.getCurrLocation()) && super.getNumOfRailroads() == player.getNumOfRailroads()
                && super.getNumOfUtilities() == player.getNumOfUtilities() && super.getTurnsInJail() == player.getTurnsInJail() && super.getOut() == player.getOut();
    }


    /**
     * Overridden Java toString method
     * @return A string statement of the state of a player
     */
    public String toString(){
        return "Player: " + super.getPlayerName() + "\n{\n" +
                "Money: $" + super.getMoneyAmount() + "\nLocation: " + super.getCurrLocation() + "\nOwned Properties: " + this.printOwnedProperties() + "\n}";
    }



}

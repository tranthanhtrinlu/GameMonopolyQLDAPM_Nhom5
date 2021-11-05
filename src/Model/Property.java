package Model;

import Events.BoardEvent;
import Events.PropertyEvent;
import Listener.BoardView;
import Listener.PropertyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cory Helm
 * Class MVC.Property that defines a property. Extends MVC.Location
 */
public class Property extends Location{
    private final List<Integer> rentCosts;
    private int numOfHouses;
    private final int maxNumberOfHouses;
    private final BoardModel.Color color;
    private Player owner;
    private final int numberOfColor;
    private final int costPerHouse;
    private List<PropertyListener> propertyListeners;
    private int oldNumOfHouses;

    /**
     * property constructor
     * @param name String name of property
     * @param cost Integer cost of the property to buy
     * @param costPerHouse Integer cost to buy one house
     * @param initialRent Integer starting rent
     * @param house1Rent Integer rent with one house
     * @param house2Rent Integer rent with two houses
     * @param house3Rent Integer rent with three houses
     * @param house4Rent Integer rent with four houses
     * @param hotelRent Integer rent with a hotel
     * @param color Color of property
     * @param numOfColor Integer number of the color
     */
    public Property(String name, int cost, int costPerHouse, int initialRent, int house1Rent, int house2Rent, int house3Rent, int house4Rent, int hotelRent, BoardModel.Color color, int numOfColor){
        super(cost, name);
        this.rentCosts = new ArrayList<>(){{
           add(initialRent);
           add(house1Rent);
           add(house2Rent);
           add(house3Rent);
           add(house4Rent);
           add(hotelRent);
        }};
        this.propertyListeners = new ArrayList<>();
        this.numOfHouses = 0;
        this.oldNumOfHouses = 0;
        this.maxNumberOfHouses = 5;
        this.color = color;
        this.owner = null;
        this.numberOfColor = numOfColor;
        this.costPerHouse = costPerHouse;
    }

    /**
     * allows players to buy property and adds property to owner
     * @param p MVC.Player
     * @return false or true
     */
    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() < this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() - this.getCost());
        this.owner.addColorToProperty(this.color, 1);
        return false;
    }

    /**
     * resets the owner on a property
     */
    @Override
    public void resetOwner() {
        this.numOfHouses = 0;
        this.owner = null;
    }

    /**
     * adds a listener to the property
     * @param view Listener.BoardView
     */
    @Override
    public void addListener(BoardView view) {
        this.propertyListeners.add(view);
    }

    /**
     * adds house to a property based on how many the player wants
     * @param add Integer number of houses added
     * @param p MVC.Player
     * @return
     */
    public boolean addHouse(int add, Player p){
        if (this.numOfHouses+add <= this.maxNumberOfHouses && p.getMoneyAmount() >= add*this.costPerHouse) {
            this.oldNumOfHouses = this.numOfHouses;
            this.numOfHouses += add;
            p.setMoneyAmount(p.getMoneyAmount() - (add*this.costPerHouse));
            return true;
        }
        return false;
    }

    /**
     * find the rent of the current property based off houses and hotels
     * @return the rent of a property
     */
    public int getRent(){
        if (this.owner.numberOfColoredPropertiesOwned(this.color, this.numberOfColor))
            return this.rentCosts.get(this.numOfHouses)*2;
        return this.rentCosts.get(this.numOfHouses);
    }

    @Override
    /**
     * Method for property functionality
     * @return A boolean
     */
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.owner == null){
            for (PropertyListener listener : this.propertyListeners){
                listener.propertyNoOwner(new PropertyEvent(this, p));
            }
            return true;
        }
        else {
            if (this.owner.equals(p)) {
                for (PropertyListener listener : this.propertyListeners){
                    listener.propertyOwned(new PropertyEvent(this, p));
                }
                return true;
            }
            for (PropertyListener listener : this.propertyListeners){
                listener.propertyRent(new PropertyEvent(this, p));
            }
        }
        return false;
    }

    /**
     * Getter for getting cost per house
     * @return An integer cost per house
     */
    public int getCostPerHouse() {
        return this.costPerHouse;
    }

    /**
     * Getter for max number of houses
     * @return An integer max number of houses
     */
    public int getMaxNumberOfHouses() {
        return this.maxNumberOfHouses;
    }

    /**
     * Getter for number of houses
     * @return An integer number of houses
     */
    public int getNumOfHouses() {
        return this.numOfHouses;
    }

    /**
     * Getter for a color
     * @return A MVC.BoardModel color
     */
    public BoardModel.Color getColor() {
        return this.color;
    }

    /**
     * Getter for getting the number of colours on the board
     * @return An integer numberOfColor
     */
    public int getNumberOfColor() {
        return this.numberOfColor;
    }

    /**
     * Getter for getting the property owner
     * @return A MVC.Player owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Method for getting the rent cost from a list of rent costs
     * @param index An integer index
     * @return An integer rent cost
     */
    public int getRentCost(int index){
        return this.rentCosts.get(index);
    }

    /**
     * Getter for getting the old number of houses prior to buying a house
     * @return An integer oldNumOfHouses
     */
    public int getOldNumOfHouses() {
        return this.oldNumOfHouses;
    }

    /**
     * send info to a string
     * @param p MVC.Player
     * @return property name, the cost or how who owns it and how much the player owes them
     */
    public String toString(Player p){
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Property name: " + this.getName() + " Who owns this property";
        }
        return "Property name: " + this.getName() + " {Owned: + " + this.owner.getPlayerName() + ", Rent: "
                + this.rentCosts.get(numOfHouses) + "} \n" + p.getPlayerName() + " will lose money now";
    }
}

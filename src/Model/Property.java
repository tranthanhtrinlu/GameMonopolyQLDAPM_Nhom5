package Model;

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
     *
     */
    public Property(String name, int cost, int costPerHouse, int initialRent, int house1Rent, int house2Rent, int house3Rent, int house4Rent, int hotelRent, BoardModel.Color color, int numOfColors){
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
        this.numberOfColor = numOfColors;
        this.costPerHouse = costPerHouse;
    }

    /**
     * set the owner attribute of a property
     * @param p Player, the player
     */
    public void setOwner(Player p){
        this.owner = p;
    }

    /**
     * allows players to buy property and adds property to owner
     * @param p MVC.Player
     * @return false or true
     */
    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() <= this.getCost()){
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

    @Override
    public Location newInstanceOfCurrentLocation(Location place) {
        return null;
    }

    /**
     * Setter for houses.
     * @param numOfHouses An integer number of houses
     */
    public void setNumOfHouses(int numOfHouses) {
        this.numOfHouses = numOfHouses;
    }

    /**
     * adds house to a property based on how many the player wants
     * @param add Integer number of houses added
     * @return boolean, true if house added otherwise false
     */
    public boolean addHouse(int add){
        if (this.numOfHouses+add <= this.maxNumberOfHouses && this.owner.getMoneyAmount() >= add*this.costPerHouse) {
            this.oldNumOfHouses = this.numOfHouses;
            this.numOfHouses += add;
            this.owner.setMoneyAmount(this.owner.getMoneyAmount() - (add*this.costPerHouse));
            return true;
        }
        return false;
    }

    public void sellHouse(int val){
        this.numOfHouses -= val;
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() + val*this.costPerHouse);
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

    /**
     * Method for property functionality
     * @return A boolean
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (p instanceof AI) {
            if (this.owner != null) {
                int doubleAmount = 1;
                if (this.owner.numberOfColoredPropertiesOwned(this.color, this.numberOfColor))
                    doubleAmount = 2;
                int landedPlayerMoney = p.getMoneyAmount();
                int rentCost = this.getRentCost(this.numOfHouses) * doubleAmount;
                if (landedPlayerMoney <= rentCost) {
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
                } else {
                    p.setMoneyAmount(p.getMoneyAmount() - rentCost);
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + rentCost);
                }

                for (PropertyListener listener : this.propertyListeners) {
                    listener.propertyRent(new PropertyEvent(this, p, rentCost));
                }
            }
        } else {
            if (this.owner == null) {
                for (PropertyListener listener : this.propertyListeners) {
                    if (listener.propertyNoOwner(new PropertyEvent(this, p, 0))) {
                        if (this.buy(p)) {
                            listener.announceCannotBuy(new PropertyEvent(this, p, 0));
                        } else {
                            listener.announcePurchaseProperty(new PropertyEvent(this, p, 0));
                        }
                    }
                }
                return true;
            } else {
                if (this.owner.equals(p)) {
                    for (PropertyListener listener : this.propertyListeners) {
                        listener.propertyOwned(new PropertyEvent(this, p, 0));
                    }
                    return true;
                }

                int doubleAmount = 1;
                if (this.owner.numberOfColoredPropertiesOwned(this.color, this.numberOfColor))
                    doubleAmount = 2;
                int landedPlayerMoney = p.getMoneyAmount();
                int rentCost = this.getRentCost(this.numOfHouses) * doubleAmount;
                if (landedPlayerMoney <= rentCost) {
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
                } else {
                    p.setMoneyAmount(p.getMoneyAmount() - rentCost);
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + rentCost);
                }

                for (PropertyListener listener : this.propertyListeners) {
                    listener.propertyRent(new PropertyEvent(this, p, rentCost));
                }
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
        if (index < 0 || index >= this.rentCosts.size()) return -1;
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

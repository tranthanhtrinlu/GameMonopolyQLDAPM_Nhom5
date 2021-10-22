import java.util.ArrayList;
import java.util.List;

public class Property extends Location {
    private final List<Integer> rentCosts;
    private int numOfHouses;
    private final int maxNumberOfHouses;
    private final BoardModel.Color color;
    private Player owner;
    private final int numberOfColor;
    private final int costPerHouse;
    private List<PropertyListener> propertyListeners;

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
        this.maxNumberOfHouses = 5;
        this.color = color;
        this.owner = null;
        this.numberOfColor = numOfColor;
        this.costPerHouse = costPerHouse;
    }

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

    public boolean addHouse(int add, Player p){
        if (this.numOfHouses+add <= this.maxNumberOfHouses && p.getMoneyAmount() <= add*this.costPerHouse) {
            this.numOfHouses += add;
            return true;
        }
        return false;
    }

    // Raise an event for each case "no owner" "curr owner" "not curr owner"
    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.owner == null){
            for (PropertyListener listener : this.propertyListeners){
                listener.propertyNoOwner(new PropertyEvent(this, p));
            }
        }
        else {
            if (this.owner.equals(p)) {
                for (PropertyListener listener : this.propertyListeners){
                    listener.propertyOwned(new PropertyEvent(this, p));
                }
                return;
            }
            for (PropertyListener listener : this.propertyListeners){
                listener.propertyRent(new PropertyEvent(this, p));
            }
        }
    }

    public int getCostPerHouse() {
        return this.costPerHouse;
    }

    public int getMaxNumberOfHouses() {
        return this.maxNumberOfHouses;
    }

    public int getNumOfHouses() {
        return this.numOfHouses;
    }

    public BoardModel.Color getColor() {
        return this.color;
    }

    public int getNumberOfColor() {
        return this.numberOfColor;
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getRentCost(int index){
        return this.rentCosts.get(index);
    }

    public String toString(){
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        return "Property name: " + this.getName() + " {Rent: " + this.rentCosts.get(numOfHouses) + "}";

    }
}

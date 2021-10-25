import java.util.ArrayList;
import java.util.List;

/**
 * @author Kareem El-Hajjar
 * Class Railroad that defines a railroad element. Extends Location
 */
public class RailRoad extends Location{
    private List<Integer> payments;
    private Player owner;
    private List<RailRoadListener> railRoadListener;

    /**
     * Constructor for railroad
     * @param name String railroad property name
     * @param cost Integer cost of property
     */
    public RailRoad(String name, int cost){
        super(cost, name);
        this.payments = new ArrayList<>(){{
           add(25);
           add(50);
           add(100);
           add(200);
        }};
        this.railRoadListener = new ArrayList<>();
        this.owner = null;
    }

    /**
     * element functionality
     * @param p Player
     * @param totalDiceRoll Integer sum of dice roll
     * @return true or false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.owner == null){
            for (RailRoadListener listener : this.railRoadListener){
                listener.railRoadNoOwner(new RailRoadEvent(this, p));
            }
            return true;
        }
        else {
            if (!this.owner.equals(p)) {
                for (RailRoadListener listener : this.railRoadListener){
                    listener.railRoadRent(new RailRoadEvent(this, p));
                }
                return false;
            }
            for (RailRoadListener listener : this.railRoadListener){
                listener.railRoadOwned(new RailRoadEvent(this, p));
            }
            return false;
        }
    }

    /**
     * allows the player to buy a railroad
     * @param p Player
     * @return false or ture depending on if they have the money needed
     */
    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() < this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.addNumOfRailroads();
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() - this.getCost());
        return false;
    }

    /**
     * resets the owner of a railroad property
     */
    @Override
    public void resetOwner() {
        this.owner = null;
    }

    /**
     * gets the results of a board event
     * @param p Player
     * @param event BoardEvent
     */
    @Override
    public void getResult(Player p, BoardEvent event) {
        for (RailRoadListener listener : this.railRoadListener){
            listener.displayLandedRailroadResult(new RailRoadEvent(this, p), event);
        }
    }

    /**
     * Getter for getting the payment to an owner of a property
     * @return An integer payment
     */
    public int getPayment(){
        return this.payments.get(this.owner.getNumOfRailroads());
    }

    /**
     * Method for adding a view to the list of railroadListeners
     * @param view A BoardView view.
     */
    @Override
    public void addListener(BoardView view) {
        this.railRoadListener.add(view);
    }

    /**
     * Same payment method but using an index for the list
     * @param index An integer index
     * @return An integer payment
     */
    public int getPayment(int index){
        return this.payments.get(index);
    }

    /**
     * gets the owner of the railroad
     * @return owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * prints info to a string
     * @param p Player
     * @return property name the cost or who owns it and how much the player owes
     */
    @Override
    public String toString(Player p) {
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Property name: " + this.getName() + " Who owns this property";
        }
        return "Property name: " + this.getName() + " {Owned: + " + this.owner.getPlayerName() + ", Rent: "
                + this.payments.get(this.owner.getNumOfRailroads()) + "} \n" + p.getPlayerName() + " will lose money now";
    }


}

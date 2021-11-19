package Model;

import Events.RailRoadEvent;
import Listener.RailRoadListener;
import Listener.BoardView;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Kareem El-Hajjar
 * Class Railroad that defines a railroad element. Extends MVC.Location
 */
public class RailRoad extends Location{
    private List<Integer> payments;
    private Player owner;
    private List<RailRoadListener> railRoadListener;

    /**
     * Constructor for railroad boardModel
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
     * @param p MVC.Player
     * @param totalDiceRoll Integer sum of dice roll
     * @param currentTurn
     * @return true or false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if(p instanceof AI){
            if (this.owner != null){
                int landedPlayerMoney = p.getMoneyAmount();
                int payment = this.payments.get(this.owner.getNumOfRailroads());
                if (landedPlayerMoney <= payment){
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
                }
                else{
                    p.setMoneyAmount(p.getMoneyAmount() - payment);
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + payment);
                }

                for (RailRoadListener listener : this.railRoadListener){
                    listener.railRoadRent(new RailRoadEvent(this, p, payment));
                }
                return false;
            }

        }else{
            if (this.owner == null){
                for (RailRoadListener listener : this.railRoadListener){
                    if (listener.railRoadNoOwner(new RailRoadEvent(this, p, 0))){
                        if (this.buy(p)) {
                            listener.announceCannotBuyRailRoad(new RailRoadEvent(this, p, 0));
                        }
                        else{
                            listener.announcePurchaseRailRoad(new RailRoadEvent(this, p, 0));
                        }
                    }
                }
                return true;
            }
            else {
                if (!this.owner.equals(p)) {

                    int landedPlayerMoney = p.getMoneyAmount();
                    int payment = this.payments.get(this.owner.getNumOfRailroads());
                    if (landedPlayerMoney <= payment){
                        this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
                    }
                    else{
                        p.setMoneyAmount(p.getMoneyAmount() - payment);
                        this.owner.setMoneyAmount(this.owner.getMoneyAmount() + payment);
                    }

                    for (RailRoadListener listener : this.railRoadListener){
                        listener.railRoadRent(new RailRoadEvent(this, p, payment));
                    }
                    return false;
                }

                for (RailRoadListener listener : this.railRoadListener){
                    listener.railRoadOwned(new RailRoadEvent(this, p, 0));
                }

            }
        }
        return false;

    }

    /**
     * allows the player to buy a railroad
     * @param p MVC.Player
     * @return false or ture depending on if they have the money needed
     */
    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() <= this.getCost()){
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
     * Getter for getting the payment to an owner of a property
     * @return An integer payment
     */
    public int getPayment(){
        return this.payments.get(this.owner.getNumOfRailroads());
    }

    /**
     * Method for adding a view to the list of railroadListeners
     * @param view A Listener.BoardView view.
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
     * @param p MVC.Player
     * @return property name the cost or who owns it and how much the player owes
     */
    @Override
    public String toString(Player p) {
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Property name: " + this.getName() + " Which you own this property";
        }
        return "Property name: " + this.getName() + " {Owned: + " + this.owner.getPlayerName() + ", Rent: "
                + this.payments.get(this.owner.getNumOfRailroads()) + "} \n" + p.getPlayerName() + " will lose money now";
    }


}

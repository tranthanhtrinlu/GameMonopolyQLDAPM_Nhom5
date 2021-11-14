package Model;

import Events.UtilityEvent;
import Listener.UtilityListener;
import Listener.BoardView;


import java.util.ArrayList;
import java.util.List;

/**
* @author Kareem El-Hajjar, Max Curkovic
* Class MVC.Utility for a utility element
*/
public class Utility extends Location{

    private Player owner;
    private List<UtilityListener> utilityListenerList;

    /**
     * the constructor for MVC.Utility
     * @param name, String name
     * @param cost, cost of the utility
     */
    public Utility(String name, int cost) {
        super(cost, name);
        this.owner = null;
        this.utilityListenerList = new ArrayList<>();
    }

    /**
     * Allows the player to buy a utility property
     * @param p MVC.Player
     * @return true tor false based of amount of money
     */
    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() < this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.addNumOfUtilities();
        return false;
    }

    /**
     *resets the owner of a utility property
     */
    @Override
    public void resetOwner() {
        this.owner = null;
    }

    /**
     * listener for board
     * @param view  view of the board
     */
    @Override
    public void addListener(BoardView view) {
        this.utilityListenerList.add(view);
    }


    /**
     * gets the owner of the utility property
     * @return MVC.Player, the owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * location for player on the board and element functionality
     * @param p MVC.Player
     * @param totalDiceRoll integer of amount rolled on the dice
     * @param currentTurn
     * @return Boolean, true if no owner, otherwise false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (this.owner == null){
            for (UtilityListener listener : this.utilityListenerList){
                if (listener.UtilityNoOwner(new UtilityEvent(this, p, totalDiceRoll, 0))){
                    if (this.buy(p)) {
                        listener.announceCannotBuyUtility(new UtilityEvent(this, p, totalDiceRoll, 0));
                    }
                    else{
                        listener.announcePurchaseOfUtility(new UtilityEvent(this, p, totalDiceRoll, 0));
                    }
                }
            }
            return true;
        }
        else {
            if (!this.owner.equals(p)) { // if owned
                int landedPlayerMoney = p.getMoneyAmount();
                int payment = this.payment(totalDiceRoll);
                if (landedPlayerMoney <= payment){
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + landedPlayerMoney);
                }
                else{
                    p.setMoneyAmount(p.getMoneyAmount() - payment);
                    this.owner.setMoneyAmount(this.owner.getMoneyAmount() + payment);
                }

                for (UtilityListener listener : this.utilityListenerList){
                    listener.UtilityPay(new UtilityEvent(this, p, totalDiceRoll, payment));
                }
                return false;
            }
            for (UtilityListener listener : this.utilityListenerList){
                listener.UtilityOwned(new UtilityEvent(this, p, totalDiceRoll, 0));
            }
            return false;
        }
    }

    /**
     * how much someone has to pay if a player lands on utility
     * @param totalDiceRoll Integer of dice sum
     * @return Integer, payment amount accordingly
     */
    public int payment(int totalDiceRoll){
        int amount = 4;
        if (this.owner.getNumOfUtilities() == BoardModel.TOTAL_UTILITIES)
            amount = 10;
        return totalDiceRoll*amount;
    }

    /**
     * puts info to a string
     * @param p MVC.Player
     * @return property name, who owns it and how much is owed if landing on it
     */
    @Override
    public String toString(Player p) {
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Purchase property: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Property name: " + this.getName() + " Who owns this property";
        }
        int amount = 4;
        if (this.owner.getNumOfUtilities() == BoardModel.TOTAL_UTILITIES)
            amount = 10;
        return "Property name: " + this.getName() + " {Owned: + " + this.owner.getPlayerName() + ", Payment: dice rolls * "
                + amount + "} \n" + p.getPlayerName() + " will lose money now";
    }
}

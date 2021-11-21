package Model.BoardElements;

import Events.UtilityEvent;
import Listener.UtilityListener;
import Listener.BoardView;
import Model.BoardModel;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;


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
     * Constructor for Utility
     * @param name String, the name
     * @param cost Integer, the cost
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
     * Handles player functionality when landing on utility(common to AI and User)
     * @param p current player
     * @param totalDiceRoll total of the dice roll
     */
    private void handlePlayerLocationOwnedFunctionality(Player p,int totalDiceRoll){
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
    }

    /**
     * handles functionality for when an AI player lands on a utility
     * @param p Player, the player
     * @param totalDiceRoll Integer, the sum of die
     * @param currentTurn Integer, the current player turn
     */
    private boolean handleAIFunctionality(Player p, int totalDiceRoll, int currentTurn){
        handlePlayerLocationOwnedFunctionality(p,totalDiceRoll);
        return false;
    }

    /**
     * handles functionality for when an human player(User) lands on a rail road
     * @param p Player, the player
     * @param totalDiceRoll Integer, the sum of die
     * @param currentTurn Integer, the current player turn
     */
    private boolean handleUserFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (this.owner == null) {
            for (UtilityListener listener : this.utilityListenerList) {
                if (listener.UtilityNoOwner(new UtilityEvent(this, p, totalDiceRoll, 0))) {
                    if (this.buy(p)) {
                        listener.announceCannotBuyUtility(new UtilityEvent(this, p, totalDiceRoll, 0));
                    } else {
                        listener.announcePurchaseOfUtility(new UtilityEvent(this, p, totalDiceRoll, 0));
                    }
                }
            }
            return true;
        } else {
            if (!this.owner.equals(p)) { // if owned
                handlePlayerLocationOwnedFunctionality(p,totalDiceRoll);
                return false;
            }
            for (UtilityListener listener : this.utilityListenerList) {
                listener.UtilityOwned(new UtilityEvent(this, p, totalDiceRoll, 0));
            }
            return false;
        }
    }
    /**
     * location for player on the board and element functionality
     * @param p Player, the player
     * @param totalDiceRoll Integer, the sum of die
     * @param currentTurn Integer, the current player turn
     * @return Boolean, true if no owner, otherwise false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if(p instanceof AI){
            return (handleAIFunctionality(p,totalDiceRoll,currentTurn));
        }else{
            return (handleUserFunctionality(p,totalDiceRoll,currentTurn));
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

package Model.BoardElements;

import Events.UtilityEvent;
import Listener.BuyableLocation;
import Listener.UtilityListener;
import Listener.BoardView;
import Model.BoardModel;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* @author Kareem El-Hajjar, Max Curkovic
* Class MVC.Utility for a utility element
*/
public class Utility extends Location implements BuyableLocation {
    private final static int AI_RANDOM_CHOICE_BUY = 0;
    private Player owner;
    private final List<UtilityListener> utilityListenerList;

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
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() - this.getCost());
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
     * handle location functionality if the location is owned
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationOwnedFunctionality(Player p, int totalDiceRoll, int currentTurn){
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
     * handle location functionality if the location is not owned for the user
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationNotOwnedFunctionalityUser(Player p, int totalDiceRoll, int currentTurn) {
        if (p.getMoneyAmount() > this.getCost()){
            for (UtilityListener listener : this.utilityListenerList) {
                if (listener.UtilityNoOwner(new UtilityEvent(this, p, totalDiceRoll, 0))) {
                    if (this.buy(p)) {
                        listener.announcePurchaseOfUtility(new UtilityEvent(this, p, totalDiceRoll, 0));
                    }
                }
            }
        }
    }

    /**
     * handle location functionality if the location is owned by another player
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationOwnedByPlayerFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        for (UtilityListener listener : this.utilityListenerList) {
            listener.UtilityOwned(new UtilityEvent(this, p, totalDiceRoll, 0));
        }
    }

    /**
     * handles functionality for when an AI player lands on a utility
     * @param p Player, the player
     * @param totalDiceRoll Integer, the sum of die
     * @param currentTurn Integer, the current player turn
     */
    @Override
    public void handleLocationNotOwnedFunctionalityAI(Player p, int totalDiceRoll, int currentTurn){
        if (p.getMoneyAmount() > this.getCost()){
            Random r = new Random();
            int choice = r.nextInt(2);
            if (choice == AI_RANDOM_CHOICE_BUY){
                this.buy(p);
                for (UtilityListener listener : this.utilityListenerList) {
                    listener.announcePurchaseOfUtility(new UtilityEvent(this, p, totalDiceRoll, 0));
                }
            }
        }
    }

    /**
     * handles functionality for when a player lands on a rail road
     * @param p Player, the player
     * @param totalDiceRoll Integer, the sum of die
     * @param currentTurn Integer, the current player turn
     */
    @Override
    public void handleLocationNotOwnedFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if(p instanceof AI){
            handleLocationNotOwnedFunctionalityAI(p,totalDiceRoll,currentTurn);
        }else{
            handleLocationNotOwnedFunctionalityUser(p,totalDiceRoll,currentTurn);
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
        if (this.owner == null) {
            handleLocationNotOwnedFunctionality(p, totalDiceRoll, currentTurn);
            return true;
        } else {
            if (!this.owner.equals(p)) { // if owned
                handleLocationOwnedFunctionality(p,totalDiceRoll, currentTurn);
                return false;
            }
            handleLocationOwnedByPlayerFunctionality(p,totalDiceRoll, currentTurn);
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

    @Override
    /**
     * returns an XML representation of Utility as a String
     */
    public String toXML(){
        String str = "\t\t\t\t<Utility>\n";
        str += "\t\t\t\t\t<name>" + this.getName() + "</name>\n";
        str += "\t\t\t\t</Utility>\n";
        return str;
    }

    /**
     * Set the owner of this utility
     * @param owner Player, the player to be set as the new owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * create new utility from node data
     * @param node, the node containing the data
     * @return the newly created location
     */
    public static Location createNewUtility(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new Utility(name, cost);
    }
}

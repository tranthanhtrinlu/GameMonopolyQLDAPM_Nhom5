package Model.BoardElements;

import Events.RailRoadEvent;
import Listener.BuyableLocation;
import Listener.RailRoadListener;
import Listener.BoardView;
import Model.GamePlayer.AI;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Kareem El-Hajjar
 * Class Railroad that defines a railroad element. Extends MVC.Location
 */
public class RailRoad extends Location implements BuyableLocation {
    private final static int AI_RANDOM_CHOICE_BUY = 0;
    private List<Integer> payments;
    private Player owner;
    private final List<RailRoadListener> railRoadListener;

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
     * handle location functionality if the location is owned
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationOwnedFunctionality(Player p, int totalDiceRoll, int currentTurn){
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
    }

    /**
     * handle location functionality if location is not owned for the User
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationNotOwnedFunctionalityUser(Player p, int totalDiceRoll, int currentTurn) {
        if (p.getMoneyAmount() > this.getCost()){
            for (RailRoadListener listener : this.railRoadListener){
                if (listener.railRoadNoOwner(new RailRoadEvent(this, p, 0))){
                    if (!this.buy(p)) {
                        listener.announcePurchaseRailRoad(new RailRoadEvent(this, p, 0));
                    }
                }
            }
        }

    }

    /**
     * handle location owned by the current player landing on it
     * @param p Player, the player
     * @param totalDiceRoll Integer, the total dice roll
     * @param currentTurn Integer, the current turn
     */
    @Override
    public void handleLocationOwnedByPlayerFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        for (RailRoadListener listener : this.railRoadListener){
            listener.railRoadOwned(new RailRoadEvent(this, p, 0));
        }
    }

    /**
     * handles functionality for when an AI player lands on a railroad
     * @param p Player, the player
     * @param totalDiceRoll Integer, the dice sum
     * @param currentTurn Integer, the current player turn
     */
    @Override
    public void handleLocationNotOwnedFunctionalityAI(Player p, int totalDiceRoll, int currentTurn){
        if (p.getMoneyAmount() > this.getCost()){
            Random r = new Random();
            int choice = r.nextInt(2);
            if (choice == AI_RANDOM_CHOICE_BUY){
                this.buy(p);
                for (RailRoadListener listener : this.railRoadListener){
                    listener.announcePurchaseRailRoad(new RailRoadEvent(this, p, 0));
                }
            }
        }
    }

    /**
     * handles functionality for when an human player(User) lands on a rail road
     * @param p Player, the player
     * @param totalDiceRoll Integer, the dice sum
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
     * element functionality
     * @param p MVC.Player
     * @param totalDiceRoll Integer sum of dice roll
     * @param currentTurn Integer, the currentTurn
     * @return true or false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (this.owner == null){
            handleLocationNotOwnedFunctionality(p, totalDiceRoll, currentTurn);
            return true;
        }
        else {
            if (!this.owner.equals(p)) {
                handleLocationOwnedFunctionality(p, totalDiceRoll, currentTurn);
                return false;
            }
            handleLocationOwnedByPlayerFunctionality(p, totalDiceRoll, currentTurn);

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
     * gets the owner of the railroad
     * @return owner
     */
    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
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

    @Override
    public String toXML(){
        String str = "\t\t\t\t<RailRoad>\n";
        str += "\t\t\t\t\t<name>" + this.getName() + "</name>\n";
        str += "\t\t\t\t</RailRoad>\n";
        return str;
    }

    public static Location createNewRailRoad(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new RailRoad(name, cost);
    }

}

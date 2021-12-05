package Model.BoardElements;

import Events.TaxEvent;
import Listener.BoardView;
import Listener.TaxListener;
import Model.BoardModel;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Class for Tax Board Element
 * @author Tony Massaad
 */
public class Tax extends Location{


    private final ArrayList<TaxListener> listeners;

    /**
     * Constructor for Tax
     * @param name String, the name
     * @param cost Integer, the cost
     */
    public Tax(String name, int cost){
        super(cost, name);
        this.listeners = new ArrayList<>();
    }

    /**
     * Functionality for when the player lands on Tax
     * @param p A MVC.Player p.
     * @param totalDiceRoll An Integer sum of the dice.
     * @param currentTurn Integer, the current turn
     * @return boolean, always false
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (p.getMoneyAmount() <= this.getCost()){
            BoardModel.addToCenterMoney(p.getMoneyAmount());
        }
        else{
            BoardModel.addToCenterMoney(this.getCost());
            p.setMoneyAmount(p.getMoneyAmount() - this.getCost());
        }
        for (TaxListener listener : this.listeners){
            listener.payTax(new TaxEvent(this, p));
        }
        return false;
    }

    /**
     * tostring method for displaying tax
     * @param p A MVC.Player object p.
     * @return String, the string
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " landed on " + this.getName() + ". Loss of money is $" + this.getCost();
    }

    /**
     * method for buying Tax (not possible so always false)
     * @param p A MVC.Player object p.
     * @return false
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Do nothing
     */
    @Override
    public void resetOwner() {
        // Nothing
    }

    /**
     * add boardView to listeners
     * @param view A Listener.BoardView view.
     */
    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);
    }

    /**
     * creates a new Tax from node data
     * @param node the node containing the data
     * @return Location, the newly created location
     */
    public static Location createNewTax(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();//Parse accordingly
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());//Parse accordingly
        return new Tax(name, cost);
    }

}

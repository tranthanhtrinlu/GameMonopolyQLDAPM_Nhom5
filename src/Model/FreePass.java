package Model;

import Events.FreePassEvent;
import Listener.BoardView;
import Listener.FreePassListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Max Curkovic
 * Class MVC.FreePass for describing a Free Pass element.
 */
public class FreePass extends Location {

    private List<FreePassListener> listeners;

    /**
     * constructor for freepass board elements

     */
    public FreePass(String name, int cost) {
        super(cost, name);
        this.listeners = new ArrayList<>();
    }

    /**
     * Boolean method for listening to the Free Pass element.
     * @param p A MVC.Player object p.
     * @param totalDiceRoll An integer totalDiceRoll.
     * @param currentTurn
     * @return Will always return false.
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        for (FreePassListener listener : this.listeners) {
            listener.FreePass(new FreePassEvent(this, p));
        }
        return false;
    }

    /**
     * Overriden Java method toString for Free Pass.
     * @param p A MVC.Player object p.
     * @return A String.
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " Landed on a Free Pass";
    }

    /**
     * Overriden boolean method for handling the purchase of a Free Pass.
     * @param p A MVC.Player object p.
     * @return Always false since you cannot buy a Free Pass.
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Does nothing for this class.
     */
    @Override
    public void resetOwner() {

    }

    /**
     * Adds the view to the list of listeners.
     * @param view A Listener.BoardView view.
     */
    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);
    }

    @Override
    public Location newInstanceOfCurrentLocation(Location place) {
        return new FreePass(place.getName(), place.getCost());
    }


}

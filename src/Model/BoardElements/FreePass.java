package Model.BoardElements;
import Events.FreePassEvent;
import Listener.BoardView;
import Listener.FreePassListener;
import Model.GamePlayer.Player;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Max Curkovic
 * Class MVC.FreePass for describing a Free Pass element.
 */
public class FreePass extends Location {

    private final List<FreePassListener> listeners;

    /**
     * Constructor for FreePass
     * @param name String, the name
     * @param cost Integer, the cost
     */
    public FreePass(String name, int cost) {
        super(cost, name);
        this.listeners = new ArrayList<>();
    }



    /**
     * Boolean method for listening to the Free Pass element.
     * @param p A MVC.Player object p.
     * @param totalDiceRoll An integer totalDiceRoll.
     * @param currentTurn Integer, the current turn
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
     * toString for Free Pass.
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


    /**
     * creates a new free pass given the node data
     * @param node Node, the data
     * @return FreePass, new FreePass
     */
    public static Location createNewFreePass(Node node) {
        Element e = (Element) node;
        String name = e.getElementsByTagName("name").item(0).getTextContent();
        int cost = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());
        return new FreePass(name, cost);
    }


}

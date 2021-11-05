package Model;

import Events.BoardEvent;
import Events.LandOnJailEvent;
import Listener.BoardView;
import Listener.LandOnJailListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kareem El-Hajjar
 * Class MVC.LandOnJail that defines a MVC.LandOnJail element
 */
public class LandOnJail extends Location {

    private List<LandOnJailListener> landOnJailListenerList;

    /**
     * constructor for MVC.LandOnJail
     * @param cost Integer cost to get out of jail
     * @param name String
     */
    public LandOnJail(int cost, String name) {
        super(cost, name);
        this.landOnJailListenerList = new ArrayList<>();
    }

    /**
     *  location functionality
     * @param p MVC.Player
     * @param totalDiceRoll Integer sum of dice roll
     * @return land on jail listener
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        for (LandOnJailListener listener : this.landOnJailListenerList){
            listener.visiting(new LandOnJailEvent(this));
        }
        return false;
    }

    /**
     * send info to a string
     * @param p MVC.Player
     * @return player name and landed on just visiting
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " LANDED ON JAIL. Just Visiting";
    }

    /**
     * the play can not buy jail
     * @param p MVC.Player
     * @return false
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * reset the owner
     */
    @Override
    public void resetOwner() {

    }

    /**
     * adds the listener for jail
     * @param view
     */
    @Override
    public void addListener(BoardView view) {
        this.landOnJailListenerList.add(view);
    }

}

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * Class FreePass for describing a Free Pass element.
 */
public class FreePass extends Location {

    private List<FreePassListener> listeners;

    /**
     * Default constructor for FreePass.
     * @param cost An integer cost.
     * @param name A String name.
     */
    public FreePass(int cost, String name) {
        super(cost, name);
        this.listeners = new ArrayList<>();
    }

    /**
     * Boolean method for listening to the Free Pass element.
     * @param p A Player object p.
     * @param totalDiceRoll An integer totalDiceRoll.
     * @return Will always return false.
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        for (FreePassListener listener : this.listeners) {
            listener.FreePass(new FreePassEvent(this));
        }
        return false;
    }

    /**
     * Overriden Java method toString for Free Pass.
     * @param p A Player object p.
     * @return A String.
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " Landed on a Free Pass";
    }

    /**
     * Overriden boolean method for handling the purchase of a Free Pass.
     * @param p A Player object p.
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
     * Does nothing for this class.
     */
    @Override
    public void getResult(Player p,  BoardEvent event) {

    }

    /**
     * Adds the view to the list of listeners.
     * @param view A BoardView view.
     */
    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);
    }

}

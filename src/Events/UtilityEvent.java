package Events;
import Model.*;

import java.util.EventObject;

/**
* @author Kareem El-Hajjar, Max Curkovic
* Class Events.UtilityEvent for a utility event, that extends EventObject
*/
public class UtilityEvent extends EventObject {

    private final Player player;
    private final int totalDiceRoll;
    /**
     * Constructs a prototypical Event.
     *
     * @param utility the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public UtilityEvent(Utility utility, Player p, int totalDiceRoll) {
        super(utility);
        this.player = p;
        this.totalDiceRoll = totalDiceRoll;
    }

    /**
    * Method for getting the total dice roll
    * @return An integer totalDiceRoll
    */
    public int getTotalDiceRoll() {
        return this.totalDiceRoll;
    }

    /**
    * Method for getting the player
    * @return A MVC.Player player
    */
    public Player getPlayer() {
        return this.player;
    }

    /**
    * Method for getting a utility element
    * @return A MVC.Utility
    */
    public Utility getUtility(){
        return (Utility) this.getSource();
    }

    /**
    * Java method for getting a source
    */
    @Override
    public Object getSource() {
        return super.getSource();
    }
}

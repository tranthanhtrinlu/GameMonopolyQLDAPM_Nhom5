package Events;
import Model.BoardElements.RailRoad;
import Model.GamePlayer.Player;

import java.util.EventObject;

/**
 * @author Kareem El-Hajjar
 * Class Events.RailRoadEvent for defining a railroad event. Extends EventObject
 */
public class RailRoadEvent extends EventObject {

    private final Player player;
    private final int rentCost;

    /**
     * constructor for railroad
     * @param rail MVC.RailRoad property
     * @param p MVC.Player
     */
    public RailRoadEvent(RailRoad rail, Player p, int rentCost) {
        super(rail);
        this.player = p;
        this.rentCost = rentCost;
    }

    /**
     * Getter method for returning the player
     * @return A MVC.Player player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter method for the Railroad element.
     * @return A Railroad event.
     */
    public RailRoad getRailRoad(){
        return (RailRoad) this.getSource();
    }

    /**
     * Override Java method for getting a source
     * @return A Java source
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Get the rent cost
     * @return Integer, the rent cost
     */
    public int getRentCost() {
        return this.rentCost;
    }
}

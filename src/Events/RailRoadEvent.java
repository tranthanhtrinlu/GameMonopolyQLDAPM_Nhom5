package Events;
import Model.*;

import java.util.EventObject;

/**
 * @author Kareem El-Hajjar
 * Class Events.RailRoadEvent for defining a railroad event. Extends EventObject
 */
public class RailRoadEvent extends EventObject {

    private final Player player;

    /**
     * constructor for railroad
     * @param rail MVC.RailRoad property
     * @param p MVC.Player
     */
    public RailRoadEvent(RailRoad rail, Player p) {
        super(rail);
        this.player = p;
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
}

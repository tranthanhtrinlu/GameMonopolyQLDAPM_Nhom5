import java.util.EventObject;

/**
 * @author Kareem El-Hajjar
 * Class RailRoadEvent for defining a railroad event. Extends EventObject
 */
public class RailRoadEvent extends EventObject {

    private Player player;

    /**
     * constructor for railroad
     * @param rail RailRoad property
     * @param p Player
     */
    public RailRoadEvent(RailRoad rail, Player p) {
        super(rail);
        this.player = p;
    }

    /**
     * Getter method for returning the player
     * @return A Player player
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

package Events;
import Model.BoardElements.Property;
import Model.GamePlayer.Player;

import java.util.EventObject;

/**
 * @author Cory Helm
 * Class Events.PropertyEvent that describes a property event. Extends EventObject
 */
public class PropertyEvent extends EventObject {

    private final Player player;
    private final int cost;

    /**
     * constructor for property event
     * @param property MVC.Property
     * @param p MVC.Player
     * @param rentCost Integer, the rent
     */
    public PropertyEvent(Property property, Player p, int rentCost) {
        super(property);
        this.player = p;
        this.cost = rentCost;
    }

    /**
     * Getter method for returning the player
     * @return A MVC.Player player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter method for a property event.
     * @return A MVC.Property event.
     */
    public Property getProperty(){
        return (Property) this.getSource();
    }

    /**
     * Overridden Java method for getting a source
     * @return A Java source
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Get the cost
     * @return Integer, the cost
     */
    public int getCost() {
        return this.cost;
    }
}

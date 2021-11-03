package Events;
import Model.*;

import java.util.EventObject;

/**
 * @author Cory Helm
 * Class Events.PropertyEvent that describes a property event. Extends EventObject
 */
public class PropertyEvent extends EventObject {

    private Player player;

    /**
     * constructor for property event
     * @param property MVC.Property
     * @param p MVC.Player
     */
    public PropertyEvent(Property property, Player p) {
        super(property);
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
}

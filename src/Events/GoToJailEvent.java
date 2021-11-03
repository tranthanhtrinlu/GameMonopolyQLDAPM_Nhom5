package Events;
import Model.*;

import java.util.EventObject;

/**
 * @author Tony Massaad
 * Class Events.GoToJailEvent that describes a go to jail event. Extends EventObject.
 */
public class GoToJailEvent extends EventObject {

    private Player player;

    /**
     * Constructs a prototypical Event.
     *
     * @param goToJail the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GoToJailEvent(GoToJail goToJail, Player p) {
        super(goToJail);
        this.player = p;
    }

    /**
     * Getter method for returning the player.
     * @return A MVC.Player object player.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter method for the Go To Jail.
     * @return A MVC.GoToJail event.
     */
    public GoToJail getGoToJail(){
        return (GoToJail) this.getSource();
    }

    /**
     * Overridden Java method for getting an object source.
     * @return An object source.
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }
}

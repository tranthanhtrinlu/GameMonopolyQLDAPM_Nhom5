package Events;
import Model.*;

import java.util.EventObject;

/**
 * @author Kareem El-Hajjar
 * Class Events.LandOnJailEvent for the event when a player lands on jail. Extends EventObject
 */
public class LandOnJailEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param landOnJail the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public LandOnJailEvent(LandOnJail landOnJail) {
        super(landOnJail);
    }

    /**
     * Method for getting a MVC.LandOnJail element
     * @return A MVC.LandOnJail element
     */
    public LandOnJail getLandOnJail(){
        return (LandOnJail) this.getSource();
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

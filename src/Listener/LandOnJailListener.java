package Listener;
import Events.LandOnJailEvent;


/**
 * @author Kareem El-Hajjar
 * Interface Listener.LandOnJailListener that describes the listener for a Land on Jail element.
 */
public interface LandOnJailListener {

    /**
     * Listener for when player Land On jail, which is the just visiting jail
     * @param e The Land on jail event
     */
    void visiting(LandOnJailEvent e);
}


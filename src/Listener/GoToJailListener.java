package Listener;
import Events.GoToJailEvent;


/**
 * @author Tony Massaad
 * Interface Listener.GoToJailListener that describes the listener for a MVC.GoToJail element.
 */
public interface GoToJailListener {

    /**
     * Listener for when the player lands on "go to jail", which will send the player to jail
     * @param e Event for go to jail
     */
    void SendPlayerToJail(GoToJailEvent e);
}

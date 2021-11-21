package Listener;
import Events.FreePassEvent;


/**
 * @author Max Curkovic
 * Interface Listener.FreePassListener that describes the listener for a Free Pass element.
 */
public interface FreePassListener {

    /**
     * Listener method for when free pass is landed on.
     * @param e The free pass  event
     */
    void FreePass(FreePassEvent e);
}

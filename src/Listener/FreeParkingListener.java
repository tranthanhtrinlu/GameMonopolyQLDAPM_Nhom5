package Listener;
import Events.FreeParkingEvent;

/**
 * Listener for when Player lands on Free Parking
 * @author Tony Massaad
 */
public interface FreeParkingListener {
    /**
     * Listener for when the player lands on Free Parking
     * @param e Event for Tax and free parking, taking the class and player
     */
    void FreeParking(FreeParkingEvent e);

}

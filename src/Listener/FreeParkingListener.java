package Listener;

import Events.*;

public interface FreeParkingListener {
    /**
     * Listener for when the player lands on Free Parking
     * @param e Event for Tax and free parking, taking the class and player
     */
    void FreeParking(FreeParkingEvent e);

}

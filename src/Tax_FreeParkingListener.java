/**
 * @author Tony Massaad
 * Interface Tax_FreeParkingListener that describes the listener for a Free Parking or Tax element.
 */
public interface Tax_FreeParkingListener {

    /**
     * Listener for when the player lands on Free Parking
     * @param e Event for Tax and free parking, taking the class and player
     */
    void FreeParking(Tax_FreeParkingEvent e);

    /**
     * Listener for when the player lands on a tax board element
     * @param e Event for Tax and free parking, taking the class and player
     */
    void payTax(Tax_FreeParkingEvent e);
}

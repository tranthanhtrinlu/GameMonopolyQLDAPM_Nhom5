package Listener;
import Events.TaxEvent;

/**
 * Listener for when player lands on tax
 * @author Kareem
 */
public interface TaxListener {

    /**
     * Listener for when the player lands on a tax board element
     * @param e Event for Tax and free parking, taking the class and player
     */
    void payTax(TaxEvent e);
}

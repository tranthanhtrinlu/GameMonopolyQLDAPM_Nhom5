package Listener;

import Events.*;

public interface TaxListener {
    /**
     * Listener for when the player lands on a tax board element
     * @param e Event for Tax and free parking, taking the class and player
     */
    void payTax(TaxEvent e);
}

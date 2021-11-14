package Listener;
import Events.*;

/**
 * @author Tony Massaad
 * Interface Listener.UtilityListener that describes the listener for a MVC.Utility element.
 */
public interface UtilityListener {

    /**
     * Listener for when the player lands on a MVC.Utility that has no owner
     * gives player choice to purchase the utitlity
     * @param e Events.UtilityEvent
     */
    boolean UtilityNoOwner(UtilityEvent e);

    /**
     * Listener for when the player lands on a MVC.Utility that they own themselves
     * @param e Events.UtilityEvent
     */
    void UtilityOwned(UtilityEvent e);

    /**
     * Listener for when the player lands on a MVC.Utility that is owned by another player
     * MVC.Player landed must pay
     * @param e Events.UtilityEvent
     */
    void UtilityPay(UtilityEvent e);

    void announceCannotBuyUtility(UtilityEvent utilityEvent);

    void announcePurchaseOfUtility(UtilityEvent utilityEvent);
}

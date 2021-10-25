/**
 * @author Tony Massaad
 * Interface UtilityListener that describes the listener for a Utility element.
 */
public interface UtilityListener {

    /**
     * Listener for when the player lands on a Utility that has no owner
     * gives player choice to purchase the utitlity
     * @param e UtilityEvent
     */
    void UtilityNoOwner(UtilityEvent e);

    /**
     * Listener for when the player lands on a Utility that they own themselves
     * @param e UtilityEvent
     */
    void UtilityOwned(UtilityEvent e);

    /**
     * Listener for when the player lands on a Utility that is owned by another player
     * Player landed must pay
     * @param e UtilityEvent
     */
    void UtilityPay(UtilityEvent e);

    /**
     * Listener to display the result of the player landing on the Utility
     * @param e UtilityEvent
     * @param boardEvent BoardEvent
     */
    void displayLandedUtilityResult(UtilityEvent e, BoardEvent boardEvent);
}

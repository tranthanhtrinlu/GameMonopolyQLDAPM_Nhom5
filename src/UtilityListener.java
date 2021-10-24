/**
 * Interface UtilityListener that describes the listener for a Utility element.
 */
public interface UtilityListener {
    void UtilityNoOwner(UtilityEvent e);
    void UtilityOwned(UtilityEvent e);
    void UtilityPay(UtilityEvent e);
    void displayLandedUtilityResult(UtilityEvent e, BoardEvent boardEvent);
}

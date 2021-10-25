/**
 * @author Cory Helm
 * Interface PropertyListener that describes the listener for a Property element.
 */
public interface PropertyListener {
    /**
     * Listener for when the player lands on a property that is not owned
     * Provides the option to purchase the property
     * @param e PropertyEvent that takes the property and player
     */
    void propertyNoOwner(PropertyEvent e);

    /**
     * Listener for when the player lands on a property they own themselves
     * Provides option to purchase houses for that property (might fix for milestone 3)
     * @param e PropertyEvent that takes the property and player
     */
    void propertyOwned(PropertyEvent e);

    /**
     * Listener for when the player lands on a property owned by another player
     * Player who lands on the property will pay the other player to rent money
     * @param e PropertyEvent that takes the property and player
     */
    void propertyRent(PropertyEvent e);

    /**
     * Listener to announce the landed property result by the player
     * @param e PropertyEvent that takes the property and player
     * @param boardEvent The BoardEvent
     */
    void displayLandedPropertyResult(PropertyEvent e, BoardEvent boardEvent);
}

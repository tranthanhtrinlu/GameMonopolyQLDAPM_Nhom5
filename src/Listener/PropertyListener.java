package Listener;
import Events.PropertyEvent;


/**
 * @author Cory Helm
 * Interface Listener.PropertyListener that describes the listener for a MVC.Property element.
 */
public interface PropertyListener {
    /**
     * Listener for when the player lands on a property that is not owned
     * Provides the option to purchase the property
     * @param e Events.PropertyEvent that takes the property and player
     * @return boolean, true if wanting to purchase property, otherwise false
     */
    boolean propertyNoOwner(PropertyEvent e);

    /**
     * Listener for when the player lands on a property they own themselves
     * Provides option to purchase houses for that property (might fix for milestone 3)
     * @param e Events.PropertyEvent that takes the property and player
     */
    void propertyOwned(PropertyEvent e);

    /**
     * Listener for when the player lands on a property owned by another player
     * MVC.Player who lands on the property will pay the other player to rent money
     * @param e Events.PropertyEvent that takes the property and player
     */
    void propertyRent(PropertyEvent e);

    /**
     * for every listener, announce that the player could not buy property when they tried
     * @param e PropertyEvent, the events occurring in Property
     */
    void announceCannotBuy(PropertyEvent e);

    /**
     * for every listener, announce the purchase of property when player buys
     * @param e Property Event, the events occurring in property
     */
    void announcePurchaseProperty(PropertyEvent e);
}

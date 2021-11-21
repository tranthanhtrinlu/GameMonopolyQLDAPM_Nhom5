package Listener;
import Events.RailRoadEvent;

/**
 * @author Tony Massaad
 * Interface Listener.RailRoadListener that describes the listener for a MVC.RailRoad element.
 */
public interface RailRoadListener {

    /**
     * Listener for when the player lands on a MVC.RailRoad that is not owned
     * Provides the option to purchase the property
     * @param e Events.RailRoadEvent that takes the MVC.RailRoad and player
     */
    boolean railRoadNoOwner(RailRoadEvent e);

    /**
     * Listener for when the player lands on a Railroad they own themselves
     * @param e Events.RailRoadEvent that takes the MVC.RailRoad and player
     */
    void railRoadOwned(RailRoadEvent e);

    /**
     * Listener for when the player lands on a property owned by another player
     * MVC.Player who lands on the property will pay the other player to rent money
     * @param e Events.RailRoadEvent that takes the RailRoad and player
     */
    void railRoadRent(RailRoadEvent e);

    /**
     * for every listener, handle announcement that the player attempted to purchase Railroad
     * but could not
     * @param e RailRoadEvent, the event occurring in the RailRoad
     */
    void announceCannotBuyRailRoad(RailRoadEvent e);

    /**
     * for every listener, handle the announcement of player purchasing Railroad
     * @param e RailRoadEvent, the event occurring in the railroad
     */
    void announcePurchaseRailRoad(RailRoadEvent e);

}

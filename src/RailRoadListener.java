/**
 * @author Tony Massaad
 * Interface RailRoadListener that describes the listener for a RailRoad element.
 */
public interface RailRoadListener {

    /**
     * Listener for when the player lands on a RailRoad that is not owned
     * Provides the option to purchase the property
     * @param e RailRoadEvent that takes the RailRoad and player
     */
    void railRoadNoOwner(RailRoadEvent e);

    /**
     * Listener for when the player lands on a Railroad they own themselves
     * @param e RailRoadEvent that takes the RailRoad and player
     */
    void railRoadOwned(RailRoadEvent e);

    /**
     * Listener for when the player lands on a property owned by another player
     * Player who lands on the property will pay the other player to rent money
     * @param e RailRoadEvent that takes the RailRoadand player
     */
    void railRoadRent(RailRoadEvent e);

    /**
     * Listener to announce the landed property result by the player
     * @param e RailRoadEvent that takes the RailRoad and player
     * @param boardEvent The BoardEvent
     */
    void displayLandedRailroadResult(RailRoadEvent e, BoardEvent boardEvent);
}

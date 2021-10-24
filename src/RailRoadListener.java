/**
 * Interface RailRoadListener that describes the listener for a RailRoad element.
 */
public interface RailRoadListener {
    void railRoadNoOwner(RailRoadEvent e);
    void railRoadOwned(RailRoadEvent e);
    void railRoadRent(RailRoadEvent e);
    void displayLandedRailroadResult(RailRoadEvent e, BoardEvent boardEvent);
}

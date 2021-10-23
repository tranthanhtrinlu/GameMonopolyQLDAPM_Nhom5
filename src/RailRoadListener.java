public interface RailRoadListener {
    void railRoadNoOwner(RailRoadEvent e);
    void railRoadOwned(RailRoadEvent e);
    void railRoadRent(RailRoadEvent e);
    void displayLandedRailroadResult(RailRoadEvent e, BoardEvent boardEvent);
}

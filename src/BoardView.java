public interface BoardView extends PropertyListener,RailRoadListener,
        UtilityListener, Tax_FreeParkingListener, LandOnJailListener, GoToJailListener, FreePassListener{


    int handleCurrentPlayerChoice(BoardEvent e);

    boolean checkIfPlayerHasProperties(BoardEvent e);

    void handlePrintStateOfEachPlayer(BoardEvent e);

    void handlePlayerMovement(BoardEvent e);

    void announceReachingGo();

    void handleGameStatus(BoardEvent e, boolean result);

    void handleAnnounceLanded(BoardEvent e);

    void handlePurchaseAnnouncment(BoardEvent e);

    void handleNextTurn(BoardEvent e);

    void announcePlayerPass(BoardEvent e);

    void handlePlayerQuit(BoardEvent e);

    void handlePlayerChoiceToPurchaseHouses(BoardEvent e);

    void announceDecisionToPurchaseHouses(BoardEvent e);
}

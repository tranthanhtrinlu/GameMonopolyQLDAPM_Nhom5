package Listener;
import Events.*;

/**
 * @author Tony Massaad
 * Interface Listener.BoardView that acts as the view for the Monopoly game. Extends all of the listeners for different elements of the board.
 */

public interface BoardView extends PropertyListener, RailRoadListener,
        UtilityListener, Tax_FreeParkingListener, LandOnJailListener, GoToJailListener, FreePassListener {


    /**
     * Abstract method that handles a current player choice based on a board event.
     * @param e A Events.BoardEvent object e.
     * @return An integer player choice.
     */
    int handleCurrentPlayerChoice(BoardEvent e);

    /**
     * Abstract method that checks if the player has properties based on a board event.
     * @param e A Events.BoardEvent e.
     * @return A boolean true if a player has properties, false otherwise.
     */
    boolean checkIfPlayerHasProperties(BoardEvent e);

    /**
     * Abstract method for handling the print state of each player.
     * @param e A Events.BoardEvent e.
     */
    void handlePrintStateOfEachPlayer(BoardEvent e);

    /**
     * Abstract method for handling player movement.
     * @param e A Events.BoardEvent e.
     */
    void handlePlayerMovement(BoardEvent e);

    /**
     * Abstract method for handling the announcement of reaching GO!.
     */
    void announceReachingGo();

    /**
     * Abstract method for handling the game status.
     * @param e A Events.BoardEvent e.
     * @param result A boolean result.
     */
    void handleGameStatus(BoardEvent e, boolean result);

    /**
     * Abstract method for handling the announcement of a board element landed.
     * @param e A Events.BoardEvent e.
     */
    void handleAnnounceLanded(BoardEvent e);

    /**
     * Abstract method for handling the purchase announcement.
     * @param e A Events.BoardEvent e.
     */
    void handlePurchaseAnnouncment(BoardEvent e);

    /**
     * Abstract method for handling the player's next turn.
     * @param e A Events.BoardEvent e.
     */
    void handleNextTurn(BoardEvent e);

    /**
     * Abstract method for handling the announcement of a player passing their turn.
     * @param e A Events.BoardEvent e.
     */
    void announcePlayerPass(BoardEvent e);

    /**
     * Abstract method for handling the player quitting the game.
     * @param e A Events.BoardEvent e.
     */
    void handlePlayerQuit(BoardEvent e);

    /**
     * Abstract method for handling the player's choice to purchase houses.
     * @param e A Events.BoardEvent e.
     */
    void handlePlayerChoiceToPurchaseHouses(BoardEvent e);

    /**
     * Abstract method for handling the announcement of the decision to purchase houses.
     * @param e A Events.BoardEvent e.
     */
    void announceDecisionToPurchaseHouses(BoardEvent e);

    /**
     * Abstract method for announcing the current player.
     * @param e A Events.BoardEvent e.
     */
    void announceCurrentPlayer(BoardEvent e);

    /**
     * Abstract boolean method for checking if the player is in jail.
     * @param e A Events.BoardEvent e.
     * @return True if the player is in jail, false otherwise.
     */
    boolean checkIfPlayerInJail(BoardEvent e);

    /**
     * Abstract boolean method for handling jail payment.
     * @param e A Events.BoardEvent e.
     * @return True if the player posted bail, false otherwise.
     */
    boolean payJail(BoardEvent e);

    /**
     * Abstract method for handling the announcement of rolling out of jail.
     */
    void handleAnnounceRolledOutOfJail();

    /**
     * Abstract method for handling the announcement of not rolling out of jail.
     */
    void handleAnnounceDidNotRollOutOfJail();

    /**
     * Abstract method for handling the announcement of paying out of jail.
     * @param e A Events.BoardEvent e.
     * @param b A boolean b.
     */
    void handleAnnouncePayedOutOfJail(BoardEvent e, boolean b);

    /**
     * Abstract boolean method for updating the game players.
     * @param e A Events.BoardEvent e.
     * @return True if the game players were updated, false otherwise.
     */
    boolean updateGamePlayers(BoardEvent e);

}

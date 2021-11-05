package Listener;
import Events.*;
import Model.Location;
import Model.Player;

/**
 * @author Tony Massaad
 * Interface Listener.BoardView that acts as the view for the Monopoly game. Extends all of the listeners for different elements of the board.
 */

public interface BoardView extends PropertyListener, RailRoadListener,
        UtilityListener, Tax_FreeParkingListener, LandOnJailListener, GoToJailListener, FreePassListener {



    /**
     * Abstract method for handling the announcement of reaching GO!.
     */
    void announceReachingGo();

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
     * Abstract boolean method for handling jail payment.
     * @param e A Events.BoardEvent e.
     * @return True if the player posted bail, false otherwise.
     */
    boolean payJail(BoardEvent e);


    /**
     * Abstract boolean method for updating the game players.
     * @param e A Events.BoardEvent e.
     * @return True if the game players were updated, false otherwise.
     */
    boolean updateGamePlayers(BoardEvent e);

    /**
     * Player regular roll
     * @param e BoardEvent, the BoardEvent
     */
    void handleGameplayRoll(BoardEvent e);

    /**
     * player attempts to roll double
     * @param e BoardEvent, the BoardEvent
     */
    void handleRollingDoubles(BoardEvent e);

    void handleMessageAnnouncement(String s);

    void handleAnnounceLocationPurchasing(Location place);

    void handleAnnounceBankruptedPlayer(Player p);

    void updateChoicePanel();

    void handleNextTurnDisplay(BoardEvent e);

    void handleUpdateSidePanelDisplay(BoardEvent e);
}

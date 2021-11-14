package Listener;
import Events.*;
import Model.Player;

/**
 * @author Tony Massaad
 * Interface Listener.BoardView that acts as the view for the Monopoly game. Extends all of the listeners for different elements of the board.
 */

public interface BoardView extends PropertyListener, RailRoadListener,
        UtilityListener, LandOnJailListener, GoToJailListener, FreePassListener, TaxListener, FreeParkingListener {

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
    void payJail(boolean payed, BoardEvent e);

    void handleAnnounceBankruptedPlayer(Player p);

    void updateChoicePanel(Player player);

    void handleNextTurnDisplay(BoardEvent e, int updatedTurn);

    void handleUpdateSidePanelDisplay(BoardEvent e);

    void handlePlayerPieceMovement(int currentTurn, int diceSum, int position);

    void handleAnnounceWinner(BoardEvent e);

    void handleAnnounceRollingAgain(BoardEvent e);

    void handleUpdateRoll(int lastRoll1, int lastRoll2);

    void buttonEnableCondition(boolean b);

    void handleRemoveOfPlayerPiece(BoardEvent e);

    void handleRemoveOfPlayerView(BoardEvent e);

    void announceReachingGo(BoardEvent e);

    void handleRollingDoubles(BoardEvent e);
}

package Listener;
import Events.*;
import Model.GamePlayer.Player;

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
     */
    void payJail(boolean payed, BoardEvent e);

    /**
     * handles the announcements to all views if a player is bankrupted
     * @param p Player, the player
     */
    void handleAnnounceBankruptedPlayer(Player p);

    /**
     * Updates the panel for every play accordingly
     * @param player Player, the player
     */
    void updateChoicePanel(Player player);

    /**
     * Update the display on the side panel for when the next turn occurs
     * @param e BoardEvent, the event occurring in the board
     * @param updatedTurn Integer, the turn when moved to the next player
     */
    void handleNextTurnDisplay(BoardEvent e, int updatedTurn);

    /**
     * update the side panel when a player state has changed
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    void handleUpdateSidePanelDisplay(BoardEvent e);

    /**
     * For every view, move the current player's piece on the board accordingly
     * @param currentTurn Integer, the current turn
     * @param oldPos Integer, the old player position
     * @param newPosition Integer, the new player position
     */
    void handlePlayerPieceMovement(int currentTurn, int oldPos, int newPosition);

    /**
     * for every view, handle the announcement of the winner
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    void handleAnnounceWinner(BoardEvent e);

    /**
     * for every view, handle the announcement of rolling the player able to roll again
     * @param e BoardEvent, the event occurring in the boardModel
     */
    void handleAnnounceRollingAgain(BoardEvent e);

    /**
     * For every view, update the rolling of dice display
     * @param lastRoll1 Integer, roll 1
     * @param lastRoll2 Integer, roll 2
     */
    void handleUpdateRoll(int lastRoll1, int lastRoll2);

    /**
     * enable or disable the button choices for the players of the game
     * @param toggle boolean, the boolean choice
     */
    void buttonEnableCondition(boolean toggle);

    /**
     * for every view, handle the removing of the player piece on the board
     * when a player goes bankrupt
     * @param e BoardEvent, the event occurring in the board
     */
    void handleRemoveOfPlayerPiece(BoardEvent e);

    /**
     * For every view, handle the displaying of "out" of the current player when they are bankrupted
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    void handleRemoveOfPlayerView(BoardEvent e);

    /**
     * For ever view, handle the announcement of a player reaching go
     * @param e BoardEvent, the event occurring in the board
     */
    void announceReachingGo(BoardEvent e);

    /**
     * for every view, handle the event when a player attempts to roll doubles when in jail
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    void handleResultsOfRollingInJail(BoardEvent e);

    /**
     * For every view, handle the choice  of a player purchasing houses
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    void handlePlayerChoiceToPurchaseHouses(BoardEvent e);

    /**
     * for every view, handle the choice of a player selling houses
     * @param e BoardEvent, the event occurring in the BoardModel
     */
    void handlePlayerChoiceToSellHouses(BoardEvent e);
}

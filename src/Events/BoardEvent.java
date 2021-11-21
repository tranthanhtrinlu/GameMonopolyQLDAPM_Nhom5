package Events;
import Model.BoardElements.Location;
import Model.BoardModel;
import Model.GamePlayer.Player;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * @author Max Curkovic
 * Class Events.BoardEvent that extends EventObject. Provides necessary methods for maintaining functionality of the model.
 */
public class BoardEvent extends EventObject {

    private final List<Location> board;
    private final boolean doubles;
    private final int roll1;
    private final int roll2;
    private final Player player;
    private final int turn;
    private final int numOfPlayers;
    private final ArrayList<Player> players;

    /**
     * Default constructor for Events.BoardEvent.
     * @param boardModel A MVC.BoardModel object boardModel.
     * @param board A list of locations board.
     * @param doubles A boolean doubles.
     * @param roll1 Integer, dice 1 roll
     * @param roll2 An integer roll2.
     * @param p Player p
     * @param turn An integer turn
     * @param players ArrayList of players
     */
    public BoardEvent(BoardModel boardModel, List<Location> board, boolean doubles, int roll1, int roll2, Player p, int turn, ArrayList<Player> players) {
        super(boardModel);
        this.board = board;
        this.doubles = doubles;
        this.roll1 = roll1;
        this.roll2 = roll2;
        this.player = p;
        this.turn = turn;
        this.players = players;
        this.numOfPlayers = players.size();
    }

    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getTurn() {
        return this.turn;
    }

    public Player getPlayerByIndex(int i){
        return this.players.get(i);
    }

    /**
     * Getter for roll1.
     * @return An integer roll1.
     */
    public int getRoll1() {
        return this.roll1;
    }

    /**
     * Getter for roll2.
     * @return An integer roll2.
     */
    public int getRoll2() {
        return this.roll2;
    }

    /**
     * Method for determining the sum of the two rolls.
     * @return An integer sum of roll1 and roll2.
     */
    public int diceSum(){
        return this.roll1 + this.roll2;
    }

    /**
     * Method for determining if a roll is doubles.
     * @return A boolean doubles.
     */
    public boolean getDoubles(){
        return this.doubles;
    }

    /**
     * Method for determining the name of an element on the board.
     * @param index An integer index.
     * @return A string name from the board element.
     */
    public String boardElementName(int index){
        if (index < 0 || index > this.board.size()){
            return null;
        }
        return this.board.get(index).getName();
    }

    /**
     * Method for returning the location of an element on the board.
     * @param index An integer index.
     * @return A MVC.Location object of an element on the board.
     */
    public Location boardElementByIndex(int index){
        return this.board.get(index);
    }

    /**
     * Getter method for the model using getSource().
     * @return A MVC.BoardModel object model.
     */
    public BoardModel getModel(){
        return (BoardModel) this.getSource();
    }

    /**
     * Overriden Java method for getting an object source.
     * @return An object source.
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }
}

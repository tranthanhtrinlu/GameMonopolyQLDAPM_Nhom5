package Model.GamePlayer;

/**
 * Class for the user
 * @author Tony
 */
public class User extends Player {

    /**
     * MVC.Player default constructor
     *
     * @param name String player name
     */
    public User(String name, int moneyAmount) {
        super(name, moneyAmount);
    }

    public User(String name, int moneyAmount, boolean out, int position, boolean inJail, int turnsInJail, String currLocation, int numOfRailroads, int numOfUtilities) {
        super(name, moneyAmount, out, position, inJail, turnsInJail, currLocation, numOfRailroads, numOfUtilities);
    }
}

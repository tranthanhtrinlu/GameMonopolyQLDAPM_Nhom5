package Model.GamePlayer;

import Model.BoardModel;

/**
 * Class for AI
 * @author Kareem
 */
public class AI extends Player {
    /**
     * MVC.Player default constructor
     * @param name String player name
     */
    public AI(String name, int moneyAmount) {
        super(name, moneyAmount);
    }

    public AI(String name, int moneyAmount, boolean out, int position, boolean inJail, int turnsInJail, String currLocation, int numOfRailroads, int numOfUtilities) {
        super(name, moneyAmount, out, position, inJail, turnsInJail, currLocation, numOfRailroads, numOfUtilities);
    }

    /**
     * Play the AI's turn dependent on the state of the AI
     * @return Integer, the AI's choice
     */
    public int playAI(){
        if (!super.getInJail())
            return BoardModel.PlayerChoice.ROLL.getChoice();
        return BoardModel.PlayerChoice.ROLL_OUT.getChoice();
    }


}

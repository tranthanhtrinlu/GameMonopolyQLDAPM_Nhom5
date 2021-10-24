import java.util.ArrayList;
import java.util.List;

public class GoToJail extends Location{

    private List<GoToJailListener> goToJailListener;

    /**
     * constructor for go to jail
     * @param cost Integer for cost to get out
     * @param name String name of jail
     */
    public GoToJail(int cost, String name) {
        super(cost, name);
        this.goToJailListener= new ArrayList<>();
    }

    /**
     * location functionality
     * @param p Player
     * @param totalDiceRoll Integer sum of dice roll
     * @return
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        for (GoToJailListener listener : this.goToJailListener){
            listener.SendPlayerToJail(new GoToJailEvent(this, p));
        }
        return false;
    }

    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " landed on " + this.getName() + ". Being Sent to Jail.";
    }

    @Override
    public boolean buy(Player p) {
        return false;
    }

    @Override
    public void resetOwner() {

    }

    @Override
    public void getResult(Player p,  BoardEvent event) {

    }

    @Override
    public void addListener(BoardView view) {
        this.goToJailListener.add(view);
    }

}

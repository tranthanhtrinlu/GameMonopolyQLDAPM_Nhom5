import java.util.ArrayList;
import java.util.List;

public class FreePass extends Location {

    private List<FreePassListener> listeners;

    public FreePass(int cost, String name) {
        super(cost, name);
        this.listeners = new ArrayList<>();
    }

    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        for (FreePassListener listener : this.listeners) {
            listener.FreePass(new FreePassEvent(this));
        }
        return false;
    }

    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " Landed on a Free Pass";
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
        this.listeners.add(view);
    }

}

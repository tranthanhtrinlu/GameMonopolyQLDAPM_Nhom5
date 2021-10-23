import java.util.ArrayList;
import java.util.List;

public class LandOnJail extends Location {

    private List<LandOnJailListener> landOnJailListenerList;
    public LandOnJail(int cost, String name) {
        super(cost, name);
        this.landOnJailListenerList = new ArrayList<>();
    }

    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        for (LandOnJailListener listener : this.landOnJailListenerList){
            listener.visiting(new LandOnJailEvent(this));
        }
        return false;
    }

    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " LANDED ON JAIL. Just Visiting";
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
        this.landOnJailListenerList.add(view);
    }

}

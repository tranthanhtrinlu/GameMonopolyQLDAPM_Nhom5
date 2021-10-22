import java.util.ArrayList;
import java.util.List;

public class LandOnJail extends Location {

    private List<LandOnJailListener> landOnJailListenerList;
    public LandOnJail(int cost, String name) {
        super(cost, name);
        this.landOnJailListenerList = new ArrayList<>();
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        for (LandOnJailListener listener : this.landOnJailListenerList){
            listener.visiting(new LandOnJailEvent(this));
        }

    }

    @Override
    public String toString() {
        return this.getName();
    }
}

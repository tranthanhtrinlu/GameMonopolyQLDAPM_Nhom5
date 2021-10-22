import java.util.ArrayList;
import java.util.List;

public class FreePass extends Location{

    private List<FreePassListener> listeners;
    public FreePass(int cost, String name) {
        super(cost, name);
        this.listeners = new ArrayList<>();
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        for (FreePassListener listener : this.listeners){
            listener.FreePass(new FreePassEvent(this));
        }
    }

    @Override
    public String toString() {
        return "Free Pass";
    }
}

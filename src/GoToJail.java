import java.util.ArrayList;
import java.util.List;

public class GoToJail extends Location{

    private List<GoToJailListener> goToJailListener;
    public GoToJail(int cost, String name) {
        super(cost, name);
        this.goToJailListener= new ArrayList<>();
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        for (GoToJailListener listener : this.goToJailListener){
            listener.SendPlayerToJail(new GoToJailEvent(this, p));
        }
    }

    @Override
    public String toString() {
        return "GO TO JAIL";
    }
}

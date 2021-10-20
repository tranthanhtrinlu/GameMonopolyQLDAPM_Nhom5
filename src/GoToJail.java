import java.util.HashMap;

public class GoToJail extends Location{


    private HashMap<Player, Integer> playersInJailWithRounds;
    public GoToJail(int cost, String name) {
        super(cost, name);
        this.playersInJailWithRounds = new HashMap<>();
    }

    @Override
    public void locationElementFunctionality(Player p) {
        // p.setPosition(Whatever position it is);
        // set player jail bool to true
        // add player to jail list
        this.playersInJailWithRounds.put(p, 3);
    }

    @Override
    public String toString() {
        return null;
    }
}

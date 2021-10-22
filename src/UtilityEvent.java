import java.util.EventObject;

public class UtilityEvent extends EventObject {

    private Player player;
    private int totalDiceRoll;
    /**
     * Constructs a prototypical Event.
     *
     * @param utility the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public UtilityEvent(Utility utility, Player p, int totalDiceRoll) {
        super(utility);
        this.player = p;
        this.totalDiceRoll = totalDiceRoll;
    }

    public int getTotalDiceRoll() {
        return this.totalDiceRoll;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Utility getUtility(){
        return (Utility) this.getSource();
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}

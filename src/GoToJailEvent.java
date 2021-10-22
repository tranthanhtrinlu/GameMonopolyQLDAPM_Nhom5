import java.util.EventObject;

public class GoToJailEvent extends EventObject {

    private Player player;

    /**
     * Constructs a prototypical Event.
     *
     * @param goToJail the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GoToJailEvent(GoToJail goToJail, Player p) {
        super(goToJail);
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }

    public GoToJail getGoToJail(){
        return (GoToJail) this.getSource();
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}

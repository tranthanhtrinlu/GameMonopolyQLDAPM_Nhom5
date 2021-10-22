import java.util.EventObject;

public class RailRoadEvent extends EventObject {

    private Player player;
    public RailRoadEvent(RailRoad rail, Player p) {
        super(rail);
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }

    public RailRoad getRailRoad(){
        return (RailRoad) this.getSource();
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}

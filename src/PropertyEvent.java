import java.util.EventObject;

public class PropertyEvent extends EventObject {

    private Player player;

    public PropertyEvent(Property property, Player p) {
        super(property);
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Property getProperty(){
        return (Property) this.getSource();
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}

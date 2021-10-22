import java.util.EventObject;

public class Tax_FreeParkingEvent extends EventObject {

    private Player player;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public Tax_FreeParkingEvent(Tax_FreeParking source, Player p) {
        super(source);
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Tax_FreeParking getLocation(){
        return (Tax_FreeParking) this.getSource();
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}

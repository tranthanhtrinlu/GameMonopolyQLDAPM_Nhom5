import java.util.EventObject;

/**
 * @author Cory Helm
 * Class Tax_FreeParkingEvent for defining the events for Tax and Free Parking. Extends EventObject
 */
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

    /**
     * Getter method for returning the player
     * @return A Player player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter method for the Tax_FreeParking.
     * @return A Tax_FreeParking event.
     */
    public Tax_FreeParking getLocation(){
        return (Tax_FreeParking) this.getSource();
    }

    /**
     * Overriden Java method for getting a source
     * @return A Java source
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }
}

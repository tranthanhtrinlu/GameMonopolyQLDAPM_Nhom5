package Events;
import Model.GamePlayer.Player;
import Model.BoardElements.Tax;
import java.util.EventObject;

/**
 * Event class for what happens when player lands on Tax
 * @author Tony Massaad
 */
public class TaxEvent extends EventObject {

    private final Player player;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public TaxEvent(Tax source, Player p) {
        super(source);
        this.player = p;
    }

    /**
     * Getter method for returning the player
     * @return A MVC.Player player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter method for the MVC.Tax_FreeParking.
     * @return A MVC.Tax_FreeParking event.
     */
    public Tax getLocation(){
        return (Tax) this.getSource();
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

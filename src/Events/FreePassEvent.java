package Events;
import Model.BoardElements.FreePass;
import Model.GamePlayer.Player;
import java.util.EventObject;

/**
 * @author Max Curkovic
 * Class Events.FreePassEvent for describing a Events.FreePassEvent. Extends EventObject.
 */
public class FreePassEvent extends EventObject {


    private final Player player;

    /**
     * Constructs a prototypical Event.
     *
     * @param pass the object on which the Event initially occurred
     * @param p Player, the player
     * @throws IllegalArgumentException if source is null
     */
    public FreePassEvent(FreePass pass, Player p) {
        super(pass);
        this.player = p;
    }

    /**
     * Getter method for the Free Pass.
     * @return A MVC.FreePass object.
     */
    public FreePass getPass(){
        return (FreePass) this.getSource();
    }

    /**
     * Overridden Java method for getting an object source.
     * @return An object source.
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Getter for the player.
     * @return A Player player.
     */
    public Player getPlayer() {
        return this.player;
    }
}

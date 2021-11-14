package Events;
import Model.*;

import java.util.EventObject;

/**
 * @author Tony Massaad
 * Class Events.GoToJailEvent that describes a go to jail event. Extends EventObject.
 */
public class GoToJailEvent extends EventObject {

    private final Player player;
    private final int currentTurn;
    private final int oldPos;
    private final int newPos;

    /**
     * Constructs a prototypical Event.
     *
     * @param goToJail the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GoToJailEvent(GoToJail goToJail, Player p, int currentTurn, int oldPos, int newPos) {
        super(goToJail);
        this.player = p;
        this.currentTurn = currentTurn;
        this.oldPos = oldPos;
        this.newPos = newPos;
    }

    /**
     * Getter method for returning the player.
     * @return A MVC.Player object player.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter method for the Go To Jail.
     * @return A MVC.GoToJail event.
     */
    public GoToJail getGoToJail(){
        return (GoToJail) this.getSource();
    }

    /**
     * Overridden Java method for getting an object source.
     * @return An object source.
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    public int getCurrentTurn(){
        return this.currentTurn;
    }

    public int getOldPos(){
        return this.oldPos;
    }

    public int getNewPos(){
        return this.newPos;
    }


}

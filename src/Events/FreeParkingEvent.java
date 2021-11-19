package Events;

import Model.BoardElements.FreeParking;
import Model.GamePlayer.Player;

import java.util.EventObject;

public class FreeParkingEvent extends EventObject {
    private final Player player;
    private final int centerMoney;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @param centerMoney Integer, centermoney
     * @throws IllegalArgumentException if source is null
     */
    public FreeParkingEvent(FreeParking source, Player p, int centerMoney) {
        super(source);
        this.player = p;
        this.centerMoney = centerMoney;
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
    public FreeParking getLocation(){
        return (FreeParking) this.getSource();
    }

    /**
     * Overriden Java method for getting a source
     * @return A Java source
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    public int getCenterMoney() {
        return this.centerMoney;
    }
}

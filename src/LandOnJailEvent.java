import java.util.EventObject;

public class LandOnJailEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param landOnJail the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public LandOnJailEvent(LandOnJail landOnJail) {
        super(landOnJail);
    }

    public LandOnJail getLandOnJail(){
        return (LandOnJail) this.getSource();
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}

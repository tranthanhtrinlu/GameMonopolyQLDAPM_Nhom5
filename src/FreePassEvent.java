import java.util.EventObject;

/**
 * @author Max Curkovic
 * Class FreePassEvent for describing a FreePassEvent. Extends EventObject.
 */
public class FreePassEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param pass the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FreePassEvent(FreePass pass) {
        super(pass);
    }

    /**
     * Getter method for the Free Pass.
     * @return A FreePass object.
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
}

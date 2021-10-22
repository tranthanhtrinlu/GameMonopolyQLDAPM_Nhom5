import java.util.EventObject;

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

    public FreePass getPass(){
        return (FreePass) this.getSource();
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}

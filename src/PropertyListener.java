/**
 * Interface PropertyListener that describes the listener for a Property element.
 */
public interface PropertyListener {
    void propertyNoOwner(PropertyEvent e);
    void propertyOwned(PropertyEvent e);
    void propertyRent(PropertyEvent e);
    void displayLandedPropertyResult(PropertyEvent e, BoardEvent boardEvent);
}

public interface PropertyListener {
    void propertyNoOwner(PropertyEvent e);
    void propertyOwned(PropertyEvent e);
    void propertyRent(PropertyEvent e);
}

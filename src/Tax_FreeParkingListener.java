/**
 * Interface Tax_FreeParkingListener that describes the listener for a Free Parking or Tax element.
 */
public interface Tax_FreeParkingListener {
    void FreeParking(Tax_FreeParkingEvent e);
    void payTax(Tax_FreeParkingEvent e);
}

package JUnitTesting;

import Model.Tax_FreeParking;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the Tax_FreeParking class.
 */
public class Tax_FreeParkingTest {

    /**
     * Test to test the cost of the Tax or Free Parking element.
     */
    @Test
    public void testCost(){
        Tax_FreeParking free = new Tax_FreeParking(0, "Free Pass");
        assertEquals(0, free.getCost());
    }
}
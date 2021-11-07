package JUnitTesting;

import Model.FreePass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the FreePass class.
 */
public class FreePassTest {

    /**
     * Test to test the cost of the FreePass element.
     */
    @Test
    public void testCost(){
        FreePass free = new FreePass(0, "Free Pass");
        assertEquals(0, free.getCost());
    }
}
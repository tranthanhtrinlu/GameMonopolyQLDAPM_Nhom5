package Testing;

import Model.BoardElements.FreePass;
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
        FreePass free = new FreePass("Free Pass", 0);
        assertEquals(0, free.getCost());
    }
}
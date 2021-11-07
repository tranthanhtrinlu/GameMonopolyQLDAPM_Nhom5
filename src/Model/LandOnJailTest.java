package Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the LandOnJail class.
 */
public class LandOnJailTest {

    /**
     * Test to test the cost of the LandOnJail element.
     */
    @Test
    public void testCost(){
        LandOnJail landOnJail= new LandOnJail(0, "Land on Jail");
        assertEquals(0, landOnJail.getCost());
    }
}
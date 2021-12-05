package Testing;

import Model.BoardElements.GoToJail;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the GoToJail class.
 */
public class GoToJailTest {

    /**
     * Test to test the cost of the GoToJail element.
     */
    @Test
    public void testCost(){
        GoToJail goToJail = new GoToJail("Go To Jail!", 0);
        assertEquals(0, goToJail.getCost());
    }
}
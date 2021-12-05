package Testing;

import Model.BoardElements.Tax;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the Tax class.
 */
public class TaxTest {

    /**
     * Test to test the cost of the Tax element.
     */
    @Test
    public void testCost(){
        Tax tax = new Tax("Testing Tax", 100);
        assertEquals(100, tax.getCost());
    }
}

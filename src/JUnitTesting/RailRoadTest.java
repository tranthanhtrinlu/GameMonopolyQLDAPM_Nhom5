package JUnitTesting;

import Model.Player;
import Model.RailRoad;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the RailRoad class.
 */
public class RailRoadTest {


    /**
     * Tests the buy() method.
     */
    @Test
    public void testBuy() {

        RailRoad railRoad = new RailRoad("Short Line", 100);
        Player player = new Player("A");
        player.setMoneyAmount(100);
        railRoad.buy(player);
        assertEquals(1, player.getNumOfProperties());

    }

    /**
     * Tests the resetOwner() method.
     */
    @Test
    public void testResetOwner() {
        RailRoad railRoad = new RailRoad("Short Line", 100);
        Player player = new Player("A");
        player.setMoneyAmount(100);
        railRoad.buy(player);
        railRoad.resetOwner();
        assertNull(railRoad.getOwner());
    }


}
package Testing;

import Model.BoardElements.RailRoad;
import Model.GamePlayer.User;
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
        User player = new User("A", 500);
        player.setMoneyAmount(100);
        railRoad.buy(player);
        assertEquals(0, player.getNumOfRailroads());

    }

    /**
     * Tests the resetOwner() method.
     */
    @Test
    public void testResetOwner() {
        RailRoad railRoad = new RailRoad("Short Line", 100);
        User player = new User("A", 500);
        player.setMoneyAmount(100);
        railRoad.buy(player);
        railRoad.resetOwner();
        assertNull(railRoad.getOwner());
    }


}
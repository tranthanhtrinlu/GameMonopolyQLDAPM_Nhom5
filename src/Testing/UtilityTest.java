package Testing;

import Model.BoardElements.Utility;
import Model.GamePlayer.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the Utility class.
 */
public class UtilityTest {

    /**
     * Tests the buy() method.
     */
    @Test
    public void testBuy() {

        Utility utility = new Utility("Electric Company", 100);
        User player = new User("A", 500);
        player.setMoneyAmount(100);
        utility.buy(player);
        assertEquals(1, player.getNumOfProperties());

    }

    /**
     * Tests the resetOwner() method.
     */
    @Test
    public void testResetOwner() {
        Utility utility = new Utility("Electric Company", 100);
        User player = new User("A", 500);
        player.setMoneyAmount(100);
        utility.buy(player);
        utility.resetOwner();
        assertNull(utility.getOwner());
    }

    /**
     * Tests the payment() method only for a utility.
     */
    @Test
    public void testPayment() {
        Utility utility = new Utility("Electric Company", 100);
        User player1 = new User("A", 500);
        player1.setMoneyAmount(100);
        utility.buy(player1);
        assertEquals(28, utility.payment(7));

    }
}
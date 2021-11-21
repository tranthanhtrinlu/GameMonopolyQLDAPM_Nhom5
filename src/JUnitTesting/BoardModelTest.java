package JUnitTesting;

import Model.BoardModel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the BoardModel class.
 */
public class BoardModelTest {

    /**
     * Test to ensure that money is properly added to the center of the board.
     */
    @Test
    public void addToCenterMoney() {
        BoardModel.addToCenterMoney(100);
        assertEquals(100, BoardModel.getCenterMoney());
    }

    /**
     * Test for the status update.
     */
    @Test
    public void testStatusUpdate() {
        BoardModel bm = new BoardModel();
        bm.setNumberOfPlayers(1);
        bm.updateStatus();
        assertEquals(bm.getStatus(), BoardModel.Status.FINISHED);
    }
}
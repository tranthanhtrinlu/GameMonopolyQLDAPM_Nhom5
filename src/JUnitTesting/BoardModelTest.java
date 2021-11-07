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
        BoardModel boardModel = new BoardModel();
        boardModel.addToCenterMoney(100);
        assertEquals(100, boardModel.getCenterMoney());
    }

    /**
     * Test for the incrementation of the current turn.
     */
    @Test
    public void incrementCurrentTurn() {
        BoardModel boardModel = new BoardModel();
        boardModel.incrementCurrentTurn();
        assertEquals(1, boardModel.getCurrentTurn());
    }
}
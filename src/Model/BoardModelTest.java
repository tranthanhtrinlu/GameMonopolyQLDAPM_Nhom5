package Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardModelTest {

    @Test
    public void addToCenterMoney() {
        BoardModel boardModel = new BoardModel();
        boardModel.addToCenterMoney(100);
        assertEquals(100, boardModel.getCenterMoney());
    }

    @Test
    public void incrementCurrentTurn() {
    }

    @Test
    public void addView() {
    }

    @Test
    public void addViewToListener() {
    }

    @Test
    public void announcePurchasingProperty() {
    }

    @Test
    public void movePlayerPieces() {
    }

    @Test
    public void announceBankruptedPlayer() {
    }

    @Test
    public void announceReachingGo() {
    }

    @Test
    public void announcePlayerMessage() {
    }

    @Test
    public void playCurrPlayerTurn() {
    }
}
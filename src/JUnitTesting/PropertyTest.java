package JUnitTesting;

import Model.BoardElements.Property;
import Model.GamePlayer.Player;
import Model.GamePlayer.User;
import org.junit.Test;
import Model.*;
import static org.junit.Assert.*;

/**
 * JUnit Testing of the Property Class
 * General Testing used by using BoardWalk as an Example from the board
 * @author Tony Massaad, Max Curkovic
 */
public class PropertyTest {

    /**
     * Testing that all initial rents is set according the constructor
     */
    @Test
    public void testInsuranceAllRentsAreSetAccordinglyForAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(50, testPropertyBoardWalk.getRentCost(0));
        assertEquals(200, testPropertyBoardWalk.getRentCost(1));
        assertEquals(600, testPropertyBoardWalk.getRentCost(2));
        assertEquals(1400, testPropertyBoardWalk.getRentCost(3));
        assertEquals(1700, testPropertyBoardWalk.getRentCost(4));
        assertEquals(2000, testPropertyBoardWalk.getRentCost(5));
    }

    /**
     * Testing the get cost of the property ensuring that it is set according to the constructor
     */
    @Test
    public void testGetCostOfProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(400, testPropertyBoardWalk.getCost());
    }

    /**
     * Testing the get number of color method of a property that it is set according to the constructor
     */
    @Test
    public void testNumberOfColorForAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(2, testPropertyBoardWalk.getNumberOfColor());
    }

    /**
     * Testing that the default of a property is set to null
     */
    @Test
    public void testDefaultOwnerOfAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        assertNull(testPropertyBoardWalk.getOwner());
    }

    /**
     * Testing that the cost of each house is set according to the constructor
     */
    @Test
    public void testGetCostPerHouseOfProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(200, testPropertyBoardWalk.getCostPerHouse());
    }

    /**
     * Testing that the color of a property is set according to the constructor
     */
    @Test
    public void testGetColorOfProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(BoardModel.Color.DARKBLUE, testPropertyBoardWalk.getColor());
    }

    /**
     * Testing that the max number of houses is defaulted to 5 when a property is initalized
     */
    @Test
    public void testDefaultMaxNumberOfHousesOfAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        assertEquals(5, testPropertyBoardWalk.getMaxNumberOfHouses());
    }

    /**
     * Ensuring that a player can purchase a property if they have the valid money for it.
     * returns false if a player can
     */
    @Test
    public void testValidBuyOfAPropertyByAPlayer(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        User testPlayer = new User("Testing");
        assertFalse(testPropertyBoardWalk.buy(testPlayer));
    }

    /**
     * Ensuring that a player can not purchase a property if they have the invalid money for it.
     * returns true if a player cannot
     */
    @Test
    public void testInvalidBuyOfAPropertyByAPlayer(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        User testPlayer = new User("Testing");
        testPlayer.setMoneyAmount(200);
        assertTrue(testPropertyBoardWalk.buy(testPlayer));
    }

    /**
     * Test the get owner of a property by using the set owner
     */
    @Test
    public void testSetOfOwnerWithGetOwnerOfAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        User testPlayer = new User("Testing");
        testPropertyBoardWalk.setOwner(testPlayer);
        assertEquals(testPlayer, testPropertyBoardWalk.getOwner());
    }

    /**
     * test that the add houses of a property returns true if the player has the valid money for it
     */
    @Test
    public void testingAddHouseWithPlayerValidAmount() {
        User testPlayer = new User("Testing");
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.getOwner().setMoneyAmount(5000);
        assertTrue(testPropertyBoardWalk.addHouse(5));
    }

    /**
     * Test if the add houses of a property returns false if the player has invalid money
     */
    @Test
    public void testingAddHouseWithPlayerInvalidAmount() {
        User testPlayer = new User("Testing");
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.getOwner().setMoneyAmount(200);
        assertFalse(testPropertyBoardWalk.addHouse(5));
    }

    /**
     * test the initial rent of a property is valid with an owner
     */
    @Test
    public void testRentWithNoHouses(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        User testPlayer = new User("Testing");
        testPropertyBoardWalk.buy(testPlayer);
        assertEquals(50, testPropertyBoardWalk.getRent());
    }

    /**
     * test if the rent with one house on a property with an owner
     */
    @Test
    public void testRentWithAddedHouses1() {
        User testPlayer = new User("Testing");
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.addHouse(1);
        assertEquals(200, testPropertyBoardWalk.getRent());
    }

    /**
     * test the rent of the property with two houses and with an owner
     */
    @Test
    public void testRentWithAddedHouses2() {
        User testPlayer = new User("Testing");
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.addHouse(2);
        assertEquals(600, testPropertyBoardWalk.getRent());
    }

    /**
     * test the rent of the property with three houses and with an owner
     */
    @Test
    public void testRentWithAddedHouses3() {
        User testPlayer = new User("Testing");
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.addHouse(3);
        assertEquals(1400, testPropertyBoardWalk.getRent());
    }

    /**
     * test the rent of the property with four houses and with an owner
     */
    @Test
    public void testRentWithAddedHouses4() {
        User testPlayer = new User("Testing");
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.addHouse(4);
        assertEquals(1700, testPropertyBoardWalk.getRent());
    }

    /**
     * test the rent of the property with a hotel and with an owner
     */
    @Test
    public void testRentWithAddedHouses5() {
        User testPlayer = new User("Testing");
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.addHouse(5);
        assertEquals(2000, testPropertyBoardWalk.getRent());
    }

    /**
     * test that the get method of the number of houses is true when using add houses on a property
     */
    @Test
    public void testGetNumberOfHousesWithAddHousesOfAProperty(){
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        User testPlayer = new User("Testing");
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.getOwner().setMoneyAmount(5000);
        testPropertyBoardWalk.addHouse(1);
        assertEquals(1, testPropertyBoardWalk.getNumOfHouses());
        testPropertyBoardWalk.addHouse(1);
        assertEquals(2, testPropertyBoardWalk.getNumOfHouses());
        testPropertyBoardWalk.addHouse(1);
        assertEquals(3, testPropertyBoardWalk.getNumOfHouses());
        testPropertyBoardWalk.addHouse(1);
        assertEquals(4, testPropertyBoardWalk.getNumOfHouses());
    }

    /**
     * test that the get method of old number of houses is true when using add houses on a property
     */
    @Test
    public void testSeveralGetOldNumberOfHousesWithAddHousesOfAProperty(){
        User testPlayer = new User("Testing");
        Property testPropertyBoardWalk = new Property("BOARDWALK", 400,200,50,200,600,1400,1700,2000, BoardModel.Color.DARKBLUE, 2);
        testPropertyBoardWalk.buy(testPlayer);
        testPropertyBoardWalk.getOwner().setMoneyAmount(5000);
        testPropertyBoardWalk.addHouse(1);
        assertEquals(0, testPropertyBoardWalk.getOldNumOfHouses());
        testPropertyBoardWalk.addHouse(1);
        assertEquals(1, testPropertyBoardWalk.getOldNumOfHouses());
        testPropertyBoardWalk.addHouse(1);
        assertEquals(2, testPropertyBoardWalk.getOldNumOfHouses());
        testPropertyBoardWalk.addHouse(1);
        assertEquals(3, testPropertyBoardWalk.getOldNumOfHouses());
        testPropertyBoardWalk.addHouse(1);
        assertEquals(4, testPropertyBoardWalk.getOldNumOfHouses());
    }
}
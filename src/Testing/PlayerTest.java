package Testing;

import Model.BoardElements.Property;
import Model.BoardElements.RailRoad;
import Model.BoardModel;
import Model.GamePlayer.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All required tests for the Player abstract class.
 * Note that User and AI are extended from this class.
 */
public class PlayerTest {


    /**
     * Tests getting the property by name.
     */
    @Test
    public void testGetPropertyByName() {
        Property property = new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250, BoardModel.Color.BROWN, 2);
        User player = new User("Max",500);
        property.buy(player);
        assertNotNull(player.getPropertyByName("Mediterranean Avenue"));
    }

    /**
     * Tests getting the number of properties of a player.
     */
    @Test
    public void testGetNumOfProperties() {
        RailRoad railRoad = new RailRoad("Max Line", 200);
        User player = new User("Max", 500);
        railRoad.buy(player);
        assertEquals(1, player.getNumOfProperties());
    }

    /**
     * Tests getting a certain property using the index in the list.
     */
    @Test
    public void testGetPropertyByIndex() {
        RailRoad railRoad = new RailRoad("Max Line", 200);
        User player = new User("Max", 500);
        railRoad.buy(player);
        assertEquals(railRoad, player.getPropertyByIndex(0));
    }

    /**
     * Tests getting the number of colored properties owned by the player.
     */
    @Test
    public void testNumberOfColoredPropertiesOwned() {
        Property property1 = new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250, BoardModel.Color.BROWN, 2);
        Property property2 = new Property("BALTIC AVENUE", 60,50,4,20,60,180,320,450, BoardModel.Color.BROWN, 2);
        User player = new User("Max", 500);
        property1.buy(player);
        property2.buy(player);
        assertTrue(player.numberOfColoredPropertiesOwned(BoardModel.Color.BROWN, 2));
    }

    /**
     * Tests setting the player in jail.
     */
    @Test
    public void testSetInJail() {
        User player = new User("Max", 500);
        player.setInJail(true);
        assertTrue(player.getInJail());
    }

    /**
     * Tests adding the property to the list of player properties.
     */
    @Test
    public void testAddProperty() {
        Property property1 = new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250, BoardModel.Color.BROWN, 2);
        User player = new User("Max", 500);
        player.addProperty(property1);
        assertEquals(1, player.getNumOfProperties());
    }

    /**
     * Tests adding a color to a property.
     */
    @Test
    public void testAddColorToProperty() {
        User player = new User("Max", 500);
        player.addColorToProperty(BoardModel.Color.GREEN, 3);
        assertNotNull(player.getOwnedPropertiesBasedOnColors());
    }

    /**
     * Tests whether a player is bankrupt.
     */
    @Test
    public void testBankrupted() {
        Property property1 = new Property("Mediterranean Avenue", 60, 50,2,10,30,90,160,250, BoardModel.Color.BROWN, 2);
        User player = new User("Max", 500);
        property1.buy(player);
        player.bankrupted();
        assertEquals(1, player.getNumOfProperties());
    }

    /**
     * Tests the addition of railroads to the player's list of owned properties.
     */
    @Test
    public void testAddNumOfRailroads() {
        User player = new User("Max", 500);
        player.addNumOfRailroads();
        assertEquals(1, player.getNumOfRailroads());
    }

    /**
     * Tests the addition of utilities to the player's list of owned properties.
     */
    @Test
    public void testAddNumOfUtilities() {
        User player = new User("Max", 500);
        player.addNumOfUtilities();
        assertEquals(1, player.getNumOfUtilities());
    }

    /**
     * Tests the player's jail payment.
     */
    @Test
    public void testPayJail() {
        User player = new User("Max", 500);
        player.setMoneyAmount(70);
        assertTrue(player.payJail());
    }
}
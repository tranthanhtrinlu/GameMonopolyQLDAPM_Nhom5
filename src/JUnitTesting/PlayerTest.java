package JUnitTesting;

import Model.Player;
import Model.Property;
import Model.RailRoad;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {


    @Test
    public void getPropertyByName() {
        RailRoad railRoad = new RailRoad("Max Line", 200);
        Player player = new Player("Max");
        railRoad.buy(player);
        assertNotNull(player.getPropertyByName("Max Line"));
    }

    /**
     * I have commented out these tests as a reminder to complete them for Milestone 3.
     */
    /*
    @Test
    public void numberOfEstateProperties() {
    }

    @Test
    public void numberOfEstatePropertiesWithHouses() {
    }
     */

    @Test
    public void getNumOfProperties() {
    }

    @Test
    public void getPropertyByIndex() {
    }

    @Test
    public void numberOfColoredPropertiesOwned() {
    }

    @Test
    public void movePlayer() {
    }

    @Test
    public void setInJail() {
    }

    @Test
    public void addProperty() {
    }

    @Test
    public void addColorToProperty() {
    }

    @Test
    public void bankrupted() {
    }

    @Test
    public void addNumOfRailroads() {
    }

    @Test
    public void addNumOfUtilities() {
    }

    @Test
    public void payJail() {
    }
}
package Model;

import java.util.List;

public class AI extends Player{
    /**
     * MVC.Player default constructor
     *
     * @param name String player name
     */
    public AI(String name) {
        super(name);
    }

    @Override
    public void bankrupted() {
        // Nothing
    }

    @Override
    public void addProperty(Location property) {
        // Nothing
    }

    @Override
    public void addColorToProperty(BoardModel.Color color, int i) {
        // Nothing
    }

    @Override
    public List<Property> getEstatePropertiesOfPlayer() {
        return null;
    }

    @Override
    public int numberOfEstateProperties() {
        return 0;
    }

    @Override
    public int numberOfEstatePropertiesWithHouses() {
        return 0;
    }

    @Override
    public int getNumOfProperties() {
        return 0;
    }

    @Override
    public Location getPropertyByIndex(int i) {
        return null;
    }

    @Override
    public boolean numberOfColoredPropertiesOwned(BoardModel.Color color, int numberOfColor) {
        return false;
    }

}

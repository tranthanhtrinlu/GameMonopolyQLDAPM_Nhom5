package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User extends Player{

    private HashMap<BoardModel.Color, Integer> ownedPropertiesBasedOnColors;
    private List<Location> ownedProperties;/**
     * MVC.Player default constructor
     *
     * @param name String player name
     */
    public User(String name) {
        super(name);
        this.ownedPropertiesBasedOnColors = new HashMap<>();
        this.ownedProperties = new ArrayList<>();
    }
}

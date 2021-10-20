import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * TO BE IMPLEMENTED BY MAX
 *
 * NEEDS:
 * A way to keep track of money, gaining and losing money
 * Name...
 * function to display the properties the player owns (Needs a list of properties AKA BoardElement)
 * A way of keeping  track of the position he/she is on the board. (remember to check the position for being at go)
 * Getter setters...
 *
 *
 * Anything else you feel would be nice to implement in player, go ahead!
 */
public class Player {

    private final String playerName;
    private int moneyAmount;
    private HashMap<String, Integer> ownedPropertiesBasedOnColors;
    private List<Location> ownedProperties;
    private boolean inJail;
    private int position;

    public Player(String name, int money){
        this.playerName = name;
        this.moneyAmount = money;
        this.ownedPropertiesBasedOnColors = new HashMap<>();
        this.ownedProperties = new ArrayList<>();
        this.position = 0;
        this.inJail = false;
    }

    public void displayProperties(){
        System.out.println(playerName + " owns the following properties: \n");
        for(Location location : ownedProperties){
            System.out.println(location.toString());
        }
    }

    public void setPosition(int position){
        this.position = position;
    }

    public void checkPosition(){

    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public boolean getInJail(){
        return this.inJail;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    public HashMap<String, Integer> getOwnedPropertiesBasedOnColors() {
        return this.ownedPropertiesBasedOnColors;
    }

    public List<Location> getOwnedProperties() {
        return this.ownedProperties;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }




    /**
     * Overrides java oject equals() method
     * @param obj Object, the object to check
     * @return boolean, true iff the comparison and the object is identical, otherwise false
     */
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if(obj == null || obj.getClass()!= this.getClass()){
            return false;
        }
        Player player = (Player) obj;
        return this.playerName.equals(player.playerName) && this.moneyAmount == player.moneyAmount
                && this.ownedPropertiesBasedOnColors == player.ownedPropertiesBasedOnColors
                && this.position == player.position && this.ownedProperties == player.ownedProperties
                && this.inJail == player.inJail;
    }
}

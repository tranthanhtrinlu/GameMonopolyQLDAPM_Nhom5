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

    private String playerName;
    private int moneyAmount;
    private HashMap<String, Integer> ownedProperties;
    private List<BoardElement> boardElementList;
    private boolean inJail;

    public Player(String name, int money){
        this.playerName = name;
        this.moneyAmount = money;
        this.ownedProperties = new HashMap();
        this.boardElementList = new ArrayList();
    }

    public void displayProperties(){
        System.out.println(playerName + " owns the following properties: \n");
        for(String string: ownedProperties.keySet()){
            System.out.println(string);
        }
    }

    public void checkPosition(){

    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public HashMap<String, Integer> getOwnedProperties() {
        return ownedProperties;
    }

    public List<BoardElement> getBoardElementList() {
        return boardElementList;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setOwnedProperties(HashMap<String, Integer> ownedProperties) {
        this.ownedProperties = ownedProperties;
    }

    public void setBoardElementList(List<BoardElement> boardElementList) {
        this.boardElementList = boardElementList;
    }
}

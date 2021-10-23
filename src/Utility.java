import java.util.ArrayList;
import java.util.List;

public class Utility extends Location{

    private Player owner;
    private List<UtilityListener> utilityListenerList;

    public Utility(String name, int cost) {
        super(cost, name);
        this.owner = null;
        this.utilityListenerList = new ArrayList<>();
    }

    @Override
    public boolean buy(Player p){
        if (p.getMoneyAmount() < this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.addNumOfUtilities();
        return false;
    }

    @Override
    public void resetOwner() {
        this.owner = null;
    }

    @Override
    public void getResult(Player p, BoardEvent event) {
        for (UtilityListener listener : this.utilityListenerList){
            listener.displayLandedUtilityResult(new UtilityEvent(this, p, event.getRoll()), event);
        }
    }


    public Player getOwner() {
        return this.owner;
    }

    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.owner == null){
            for (UtilityListener listener : this.utilityListenerList){
                listener.UtilityNoOwner(new UtilityEvent(this, p, totalDiceRoll));
            }
            return true;
        }
        else {
            if (!this.owner.equals(p)) { // if owned
                for (UtilityListener listener : this.utilityListenerList){
                    listener.UtilityPay(new UtilityEvent(this, p, totalDiceRoll));
                }
                return false;
            }
            for (UtilityListener listener : this.utilityListenerList){
                listener.UtilityOwned(new UtilityEvent(this, p, totalDiceRoll));
            }
            return false;
        }
    }

    @Override
    public String toString(Player p) {
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Purchase property: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Property name: " + this.getName() + " Who owns this property";
        }
        int amount = 4;
        if (this.owner.getNumOfUtilities() == BoardModel.TOTAL_UTILITIES)
            amount = 10;
        return "Property name: " + this.getName() + " {Owned: + " + this.owner.getPlayerName() + ", Payment: dice rolls * "
                + amount + "} \n" + p.getPlayerName() + " will lose money now";
    }
}

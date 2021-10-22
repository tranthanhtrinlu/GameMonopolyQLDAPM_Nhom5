import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Utility extends Location{

    private Player owner;
    private List<UtilityListener> utilityListenerList;

    public Utility(String name, int cost) {
        super(cost, name);
        this.owner = null;
        this.utilityListenerList = new ArrayList<>();
    }

    public boolean buy (Player p){
        if (p.getMoneyAmount() < this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.addNumOfUtilities();
        return false;
    }

    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.owner == null){
            for (UtilityListener listener : this.utilityListenerList){
                listener.UtilityNoOwner(new UtilityEvent(this, p, totalDiceRoll));
            }
        }
        else {
            if (!this.owner.equals(p)) { // if owned
                for (UtilityListener listener : this.utilityListenerList){
                    listener.UtilityPay(new UtilityEvent(this, p, totalDiceRoll));
                }
                return;
            }
            for (UtilityListener listener : this.utilityListenerList){
                listener.UtilityOwned(new UtilityEvent(this, p, totalDiceRoll));
            }
        }
    }

    @Override
    public String toString() {
        if (this.owner == null)
            return this.getName() + " {Cost is: " + this.getCost() + "}";

        int amount = 4;
        if (this.owner.getNumOfUtilities() == BoardModel.TOTAL_UTILITIES)
            amount = 10;
        return this.getName() + " {Payment is: dice rolls * " + amount + "}";
    }
}

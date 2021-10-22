import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RailRoad extends Location{
    private List<Integer> payments;
    private Player owner;
    private List<RailRoadListener> railRoadListener;

    public RailRoad(String name, int cost){
        super(cost, name);
        this.payments = new ArrayList<>(){{
           add(25);
           add(50);
           add(100);
           add(200);
        }};
        this.railRoadListener = new ArrayList<>();
        this.owner = null;
    }

    @Override
    public void locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.owner == null){
            for (RailRoadListener listener : this.railRoadListener){
                listener.railRoadNoOwner(new RailRoadEvent(this, p));
            }
        }
        else {
            if (!this.owner.equals(p)) {
                for (RailRoadListener listener : this.railRoadListener){
                    listener.railRoadRent(new RailRoadEvent(this, p));
                }
                return;
            }
            for (RailRoadListener listener : this.railRoadListener){
                listener.railRoadOwned(new RailRoadEvent(this, p));
            }
        }
    }

    public boolean buy(Player p){
        if (p.getMoneyAmount() < this.getCost()){
            return true;
        }
        this.owner = p;
        this.owner.addProperty(this);
        this.owner.addNumOfRailroads();
        this.owner.setMoneyAmount(this.owner.getMoneyAmount() - this.getCost());
        return false;
    }

    public int getPayment(int index){
        return this.payments.get(index);
    }

    public Player getOwner() {
        return this.owner;
    }

    @Override
    public String toString() {
        if (this.owner == null)
            return this.getName() + " {Cost is: " + this.getCost() + "}";
        return this.getName() + " {Current Price: " + this.payments.get(this.owner.getNumOfRailroads()) + "}";
    }


}

import java.util.ArrayList;
import java.util.List;

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
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.owner == null){
            for (RailRoadListener listener : this.railRoadListener){
                listener.railRoadNoOwner(new RailRoadEvent(this, p));
            }
            return true;
        }
        else {
            if (!this.owner.equals(p)) {
                for (RailRoadListener listener : this.railRoadListener){
                    listener.railRoadRent(new RailRoadEvent(this, p));
                }
                return false;
            }
            for (RailRoadListener listener : this.railRoadListener){
                listener.railRoadOwned(new RailRoadEvent(this, p));
            }
            return false;
        }
    }

    @Override
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

    @Override
    public void resetOwner() {
        this.owner = null;
    }

    @Override
    public void getResult(Player p, BoardEvent event) {
        for (RailRoadListener listener : this.railRoadListener){
            listener.displayLandedRailroadResult(new RailRoadEvent(this, p), event);
        }
    }

    @Override
    public void addListener(BoardView view) {
        this.railRoadListener.add(view);
    }

    public int getPayment(int index){
        return this.payments.get(index);
    }

    public Player getOwner() {
        return this.owner;
    }

    @Override
    public String toString(Player p) {
        if (this.owner == null)
            return "Property name: " + this.getName() + " {Cost: " + this.getCost() + "}";
        else if (this.owner.equals(p)){
            return "Property name: " + this.getName() + " Who owns this property";
        }
        return "Property name: " + this.getName() + " {Owned: + " + this.owner.getPlayerName() + ", Rent: "
                + this.payments.get(this.owner.getNumOfRailroads()) + "} \n" + p.getPlayerName() + " will lose money now";
    }


}

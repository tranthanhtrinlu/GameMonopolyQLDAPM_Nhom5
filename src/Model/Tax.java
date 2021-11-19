package Model;

import Events.TaxEvent;
import Listener.BoardView;
import Listener.TaxListener;

import java.util.ArrayList;

public class Tax extends Location{


    ArrayList<TaxListener> listeners;

    /**
     * Constructor for Tax on boardModel

     */
    public Tax(String name, int cost){
        super(cost, name);
        this.listeners = new ArrayList<>();
    }


    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (p.getMoneyAmount() <= this.getCost()){
            BoardModel.addToCenterMoney(p.getMoneyAmount());
        }
        else{
            BoardModel.addToCenterMoney(this.getCost());
            p.setMoneyAmount(p.getMoneyAmount() - this.getCost());
        }
        for (TaxListener listener : this.listeners){
            listener.payTax(new TaxEvent(this, p));
        }
        return false;
    }

    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " landed on " + this.getName() + ". Loss of money is $" + this.getCost();
    }

    @Override
    public boolean buy(Player p) {
        return false;
    }

    @Override
    public void resetOwner() {
        // Nothing
    }

    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);

    }
}

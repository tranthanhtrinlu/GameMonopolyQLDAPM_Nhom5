package Model;

import Events.FreeParkingEvent;
import Listener.BoardView;
import Listener.FreeParkingListener;

import java.util.ArrayList;

public class FreeParking extends Location{


    ArrayList<FreeParkingListener> listeners;

    public FreeParking(int cost, String name){
        super(cost, name);
        this.listeners = new ArrayList<>();

    }

    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll, int currentTurn) {
        if (BoardModel.centerMoney == 0)
            BoardModel.setCenterMoney(100);
        p.setMoneyAmount(p.getMoneyAmount() + BoardModel.getCenterMoney());
        for (FreeParkingListener listener : this.listeners){
            listener.FreeParking(new FreeParkingEvent(this, p, BoardModel.getCenterMoney()));
        }
        BoardModel.setCenterMoney(0);
        return false;
    }

    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " Landed on free parking. Earned money is $";
    }

    @Override
    public boolean buy(Player p) {
        return false;
    }

    @Override
    public void resetOwner() {

    }

    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);

    }


}

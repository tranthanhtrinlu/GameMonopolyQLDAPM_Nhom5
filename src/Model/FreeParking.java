package Model;

import Events.FreeParkingEvent;
import Listener.BoardView;
import Listener.FreeParkingListener;

import java.util.ArrayList;

/**
 * @author Max Curkovic
 * Class MVC.FreeParking for describing a Free Parking element.
 */
public class FreeParking extends Location{


    ArrayList<FreeParkingListener> listeners;


    public FreeParking(int cost, String name){
        super(cost, name);
        this.listeners = new ArrayList<>();

    }

    /**
     * Boolean method for listening to the Free Pass element.
     * @param p A MVC.Player object p.
     * @param totalDiceRoll An integer totalDiceRoll.
     * @param currentTurn An integer currentTurn.
     * @return Will always return false.
     */
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

    /**
     * Overriden Java method toString for Free Parking.
     * @param p A MVC.Player object p.
     * @return A String.
     */
    @Override
    public String toString(Player p) {
        return p.getPlayerName() + " Landed on free parking. Earned money is $";
    }

    /**
     * Does nothing for this class.
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * Does nothing for this class.
     */
    @Override
    public void resetOwner() {

    }

    /**
     * Adds the view to the ArrayList of Free Parking listeners
     * @param view A Listener.BoardView view
     */
    @Override
    public void addListener(BoardView view) {
        this.listeners.add(view);

    }


}

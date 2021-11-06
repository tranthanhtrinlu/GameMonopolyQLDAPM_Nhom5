package Model;
import  Listener.Tax_FreeParkingListener;

import Events.BoardEvent;
import Events.Tax_FreeParkingEvent;
import Listener.BoardView;

import java.util.ArrayList;
import java.util.List;

/**
* @author Tony Massaad
* class for Tax and Free Parking
*/ 
public class Tax_FreeParking extends Location{

    private List<Tax_FreeParkingListener> tax_freeParkingListenerList;

    /**
     * constructor for tax free parking
     * @param cost Integer for cost
     * @param name String name of parking lot
     */
    public Tax_FreeParking(int cost, String name) {
        super(cost, name);
        this.tax_freeParkingListenerList = new ArrayList<>();
    }

    /**
     * used for actual functionality of free parking and tax
     * @param p MVC.Player
     * @param totalDiceRoll Integer amount of dice sum
     * @return
     */
    @Override
    public boolean locationElementFunctionality(Player p, int totalDiceRoll) {
        if (this.getName().equals("FREE PARKING")){
            for (Tax_FreeParkingListener listener : this.tax_freeParkingListenerList){
                listener.FreeParking(new Tax_FreeParkingEvent(this, p));
            }
            return false;
        }
        for (Tax_FreeParkingListener listener : this.tax_freeParkingListenerList){
            listener.payTax(new Tax_FreeParkingEvent(this, p));
        }
        return false;
    }

    /**
     * Sends player name and the amount of money earned to a string
     * @param p MVC.Player
     * @return player name and how much they earned
     */
    @Override
    public String toString(Player p) {
        if (this.getName().equals("FREE PARKING")){
            return p.getPlayerName() + " Landed on free parking. Earned money is $";
        }
        return p.getPlayerName() + " landed on " + this.getName() + ". Loss of money is $" + this.getCost();
    }

    /**
     *  buying property
     * @param p MVC.Player
     * @return false
     */
    @Override
    public boolean buy(Player p) {
        return false;
    }

    /**
     * resets the owner
     */
    @Override
    public void resetOwner() {

    }

    /**
     * adds a listener
     * @param view
     */
    @Override
    public void addListener(BoardView view) {
        this.tax_freeParkingListenerList.add(view);
    }

}

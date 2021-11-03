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

    private int centerMoney;
    private List<Tax_FreeParkingListener> tax_freeParkingListenerList;

    /**
     * constructor for tax free parking
     * @param cost Integer for cost
     * @param name String name of parking lot
     */
    public Tax_FreeParking(int cost, String name) {
        super(cost, name);
        this.centerMoney = 0;
        this.tax_freeParkingListenerList = new ArrayList<>();
    }

    /**
     * gets how much money is in the center
     * @return Integer centerMoney
     */
    public int getCenterMoney() {
        return this.centerMoney;
    }

    /**
     * will set centerMoney
     * @param centerMoney Integer money in center
     */
    public void setCenterMoney(int centerMoney) {
        this.centerMoney = centerMoney;
    }

    /**
     * used to add money to the center
     * @param add Integer added
     */
    public void addToCenterMoney(int add){
        this.centerMoney += add;
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
            return p.getPlayerName() + " Landed on free parking. Earned money is $" + this.centerMoney;
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
     * get result of a board event
     * @param p
     * @param event
     */
    @Override
    public void getResult(Player p, BoardEvent event) {

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
